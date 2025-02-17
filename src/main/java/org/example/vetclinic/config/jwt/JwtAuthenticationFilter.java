package org.example.vetclinic.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.UserType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = null;


        final var authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null) {
                jwt = (String) session.getAttribute("token");
            }
        }

        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String userEmail = jwtTokenUtil.getEmailFromToken(jwt);
            final UserType userType = jwtTokenUtil.getUserTypeFromToken(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                if (jwtTokenUtil.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("User {} with type {} authenticated successfully", userEmail, userType);

                }
            }
        } catch (ExpiredJwtException ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error("JWT token is expired: {}", ex.getMessage());
            return;
        } catch (MalformedJwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            log.error("Invalid JWT token: {}", ex.getMessage());
            return;
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Internal server error: {}", ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

}
