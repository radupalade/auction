package com.sda.auction.mapper;

import com.sda.auction.dto.HeaderDto;
import io.jsonwebtoken.Claims;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HeaderMapper {

    public HeaderDto convert(Claims claims) {
        HeaderDto headerDto = new HeaderDto();
        headerDto.setFirstName(claims.get("firstName", String.class));
        List<String> roles = claims.get("roles", ArrayList.class);
        for (String role : roles) {
            if (role.compareTo("admin") == 0) {
                headerDto.setAdmin(true);
            }
        }
        return headerDto;
    }
}
