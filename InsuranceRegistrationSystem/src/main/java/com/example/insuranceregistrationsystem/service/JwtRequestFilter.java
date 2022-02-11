package com.example.insuranceregistrationsystem.service;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.insuranceregistrationsystem.exception.InsuranceException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /***************************************************************************************************************************
     * Ability to verify the jwtToken each time of every User request for accessing the resources.
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     **************************************************************************************************************************/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        System.out.println("Hi");

        final String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;
            jwtToken = requestTokenHeader;
        System.out.println(jwtToken);
            // JWT Token is in the form "Bearer token". Remove Bearer word and get
            // only the Token
            if (requestTokenHeader != null && jwtToken.startsWith("Bearer ")) {
                jwtToken = jwtToken.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    throw new InsuranceException("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    throw new InsuranceException("JWT Token has expired");
                } catch (MalformedJwtException e){
                    throw new InsuranceException("JWT Token Invalid");
                }catch (SignatureException e){
                    throw new InsuranceException(e.getMessage());
                }
            } else {
                logger.warn("JWT Token does not begin with Bearer String");
            }

            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                // if token is valid configure Spring Security to manually set
                // authentication
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            }
            chain.doFilter(request, response);
    }
}
