package com.sda.auction.config;

import com.sda.auction.service.SecurityService;
import com.sda.auction.service.impl.AccesDeniedHandler;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SecurityFilter implements Filter {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private AccesDeniedHandler accessDeniedHandler;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        if (securityService.isValid(servletRequest)) {

            securityService.setEmailOn(servletRequest);

            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            accessDeniedHandler.reply(servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

