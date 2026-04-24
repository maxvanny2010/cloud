package com.cloud.license.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.cloud.license.utils.UserContext.AUTH_TOKEN;
import static com.cloud.license.utils.UserContext.CORRELATION_ID;
import static com.cloud.license.utils.UserContext.ORGANIZATION_ID;
import static com.cloud.license.utils.UserContext.USER_ID;

/**
 * UserContextFilter.
 *
 * @author legion
 * @version 7.0
 * @since 22.04.2026
 */
@Component
public class UserContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;
        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(AUTH_TOKEN));
        UserContextHolder.getContext().setOrganizationId(httpServletRequest.getHeader(ORGANIZATION_ID));

        logger.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}