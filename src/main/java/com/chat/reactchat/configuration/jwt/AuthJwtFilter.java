package com.chat.reactchat.configuration.jwt;

import com.chat.reactchat.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@AllArgsConstructor
public class AuthJwtFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Upgrade") != null ?
                parseJwtFromParams(request) : parseJwtFromHeader(request);
        // передавать токен в параметре плохая идея, но браузерный апи не поддерживает установку касномных заголовков.
        if (jwt != null) {
            String userId = jwtTokenUtils.getIdAndValidate(jwt);

            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwtFromHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        return jwtTokenUtils.getToken(headerAuth);
    }

    private String parseJwtFromParams(HttpServletRequest request) {
        Map<String, String[]> paramAuth = request.getParameterMap();
        String[] token = paramAuth.getOrDefault("token", null);
        return token == null ? null : token[0];
    }
}
