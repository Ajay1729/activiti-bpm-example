package com.example.service;

import com.example.interceptor.JWTFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * Created by aloha on 22-Jan-17.
 */
@Service
public class AuthService {

    private static final String ROLE_CLAIM = "role";

    @Autowired
    IdentityServiceWrapper userService;



    public String buildToken(User user){
        return  Jwts.builder()
                .setSubject(user.getId())
                .claim(ROLE_CLAIM, "CLAIM 01 VALUE")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JWTFilter.SECRET_KEY)
                .compact();
    }


    public Optional<User> getUserFromRequest(HttpServletRequest request) throws ServletException {

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.equals("Bearer null") || authHeader.endsWith("null")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        String token = authHeader.substring(7); // The part after "Bearer "
        Claims claims = Jwts.parser().setSigningKey(JWTFilter.SECRET_KEY).parseClaimsJws(token).getBody();
        String id = (String) claims.get("sub"); //subject as setSubject(...)
        return userService.getUserById(id);

    }

}
