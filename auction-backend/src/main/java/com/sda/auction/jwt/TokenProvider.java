package com.sda.auction.jwt;

import com.sda.auction.dto.HeaderDto;
import com.sda.auction.mapper.HeaderMapper;
import com.sda.auction.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements InitializingBean {

    private SignatureAlgorithm signatureAlgorithm
            = SignatureAlgorithm.HS256;

    @Value("${jwt.server.secret}")
    private String serverSecret;

    private Key signingKey;


    @Value("${jwt.role.admin.protected}")
    private String adminProtectedPaths;

    @Value("${jwt.role.user.protected}")
    private String userProtectedPaths;

    @Autowired
    private HeaderMapper headerMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(serverSecret);
        signingKey = new SecretKeySpec(keyBytes, signatureAlgorithm.getJcaName());
    }


    public String createJwt(User user) {
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("roles", user.getRolesAsStrings())
                .claim("firstName", user.getFirstName())
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    public boolean validate(String jwt, String requestURL) {
        Optional<Claims> optionalClaims = decodeJwt(jwt);
        if (!optionalClaims.isPresent()) {
            return false;
        }

        Claims claims = optionalClaims.get();
        return isAuthoriezed(claims, requestURL);
    }

    private boolean isAuthoriezed(Claims claims, String requestURL) {
        boolean result = true;
        if (adminProtectedResource(requestURL)) {
            result = hasRole("admin", claims);
        } else if (userProtectedResource(requestURL)) {
            result = hasRole("user", claims);
        }
        return result;
    }

    private boolean hasRole(String role, Claims claims) {
        List<String> roles = claims.get("roles", ArrayList.class);
        for (String each : roles) {
            if (role.compareTo(each) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean userProtectedResource(String requestURL) {
        return roleProtectedResource(requestURL, userProtectedPaths);
    }

    private boolean adminProtectedResource(String requestURL) {
        return roleProtectedResource(requestURL, adminProtectedPaths);
    }

    private boolean roleProtectedResource(String requestURL, String roleProtectedPaths) {
        String[] roleProtected = roleProtectedPaths.split(",");
        for (String path : roleProtected) {
            if (requestURL.contains(path)) {
                return true;
            }
        }
        return false;
    }


    private Optional<Claims> decodeJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(jwt)
                    .getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String getEmailFrom(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(jwt)
                    .getBody().get("email", String.class);
        } catch (Exception e) {
            return "";
        }
    }

    public HeaderDto getHeaderDtoFrom(String jwt) {
        Claims claim = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println(claim);

        return headerMapper.convert(claim);
    }
}
