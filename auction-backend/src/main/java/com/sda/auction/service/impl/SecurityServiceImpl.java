package com.sda.auction.service.impl;

import com.sda.auction.dto.HeaderDto;
import com.sda.auction.dto.LoginDto;
import com.sda.auction.jwt.TokenProvider;
import com.sda.auction.model.Role;
import com.sda.auction.model.User;
import com.sda.auction.repository.RoleRepository;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.SecurityService;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SecurityServiceImpl implements SecurityService {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RoleRepository roleRepository;

    @Value("${jwt.role.public}")
    private String publicPaths;
    @Value("${jwt.admin.suffixes}")
    private String adminSuffixes;

    @Override
    public boolean passwordMatch(LoginDto userDto, User user) {
        String plaintextPassword = userDto.getPassword();
        String hashedPassword = user.getPassword();

        return passwordEncoder.matches(plaintextPassword, hashedPassword);
    }

    @Override
    public LoginDto createDtoWithJwt(User user) {

        LoginDto result = new LoginDto();
        result.setEmail(user.getEmail());
        result.setPassword(user.getPassword());

        String jwt = tokenProvider.createJwt(user);
        result.setJwt(jwt);

        return result;
    }

    @Override
    public boolean isValid(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String requestURL = httpServletRequest.getRequestURI();
        if (isPublicPath(requestURL)) {
            return true;
        }
        String jwt = resolveToken(httpServletRequest);
        return tokenProvider.validate(jwt, requestURL);
    }


    private boolean isPublicPath(String requestURL) {
        String[] publicPathsArray = publicPaths.split(",");
        for (String path : publicPathsArray) {
            if (requestURL.compareTo(path) == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setEmailOn(ServletRequest servletRequest) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);

        String userEmail = tokenProvider.getEmailFrom(jwt);
        httpServletRequest.setAttribute("userEmail", userEmail);
    }

    @Override
    public HeaderDto getHeaderDtoFrom(HttpServletRequest request) {
        String jwt = resolveToken(request);
        return tokenProvider.getHeaderDtoFrom(jwt);
    }


    @Override
    public void addUserRoles(User user) {
        List<String> adminSuffixesArray = Arrays.asList(adminSuffixes.split(","));
        String userEmailAddress = user.getEmail();

        for (String suffix : adminSuffixesArray) {
            if (userEmailAddress.endsWith(suffix)) {
                Role admin = roleRepository.findByRoleName("admin");
                user.addRole(admin);
                return;
            }
        }
        // nu s-a iesit din metoda cu return, inseamna ca nu s-a atribuit admin
        //default: ii dam user
        Role role = roleRepository.findByRoleName("user");
        user.addRole(role);
    }


    //	"Bearer adsadsafisafsakjskjdsa.sadjsaksaksajk.sakjddsakdsakdsa"
    private String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;

    }
}
