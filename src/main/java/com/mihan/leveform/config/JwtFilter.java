package com.mihan.leveform.config;

import com.mihan.leveform.service.MyUserDetailsService;
import com.mihan.leveform.utility.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                userName = jwtUtil.extractUsername(token);
            }

            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(MyUserDetailsService.class)
                        .loadUserByUsername(userName);

                if (jwtUtil.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            sendErrorResponse(response, "JWT token has expired", HttpServletResponse.SC_UNAUTHORIZED);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            sendErrorResponse(response, "Invalid JWT token", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", status);
        error.put("error", "Unauthorized");
        error.put("message", message);

        response.getWriter().write(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(error));
    }
}
