package com.gmail.salahub.nikolay.springbootmodule.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    public final static String HOME_PAGE = "home";
    public final static String USER_PAGE = "item";
    public final static String ITEM_PAGE = "user";

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        handle(httpServletRequest, httpServletResponse, authentication);
        clearAuthenticationAttribute(httpServletRequest);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = chooseTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug("response has ben commited");
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private String chooseTargetUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equalsIgnoreCase("CUSTOMER")) {
                logger.info("granted authority, return", grantedAuthority.toString(), ITEM_PAGE);
                return ITEM_PAGE;
            } else if (grantedAuthority.getAuthority().equalsIgnoreCase("ADMINISTRATOR")) {
                logger.info("granted authority return", grantedAuthority.toString(), USER_PAGE);
                return USER_PAGE;
            }
        }
        logger.debug("LoginSuccessHandler Exception");
        throw new IllegalStateException();
    }

    private void clearAuthenticationAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
