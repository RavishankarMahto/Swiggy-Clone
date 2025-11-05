package com.fooddelivery.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * CharacterEncodingFilter
 * Ensures all requests and responses use UTF-8 encoding.
 */
public class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encodingParam = filterConfig.getInitParameter("encoding");
        if (encodingParam != null && !encodingParam.trim().isEmpty()) {
            this.encoding = encodingParam;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Set encoding for incoming requests
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }

        // Set encoding for outgoing responses
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html; charset=" + encoding);

        // Continue filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No cleanup necessary
    }
}
