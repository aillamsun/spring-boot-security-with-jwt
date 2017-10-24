package com.chinawiserv.admin.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * handle 403 page
 * <p>
 * Created by sungang on 2017/10/24.
 */
@Component
@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.info("用户: '" + auth.getName()
                    + " 无权限访问URL : "
                    + httpServletRequest.getRequestURI());
        }
        httpServletResponse.setStatus(403);
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/api/login/403?url=" + httpServletRequest.getMethod() + "路径>>" + httpServletRequest.getRequestURI());

    }
}
