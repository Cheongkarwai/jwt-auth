package com.example.springsecurity.util;

import com.example.springsecurity.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "6B58703272357538782F413F4428472B4B6250655368566D5971337436763979";
    private static final String ISSUER = "Cheong";

    private static <T> T extractClaims(String token,Function<Claims,T> resolver){
        final Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    private static Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    public static boolean validateToken(String token,String username){
        Date date = extractClaims(token,Claims::getExpiration);
        String subject = extractClaims(token,Claims::getSubject);

        log.info("Username {} ,Date {}",subject,date.after(new Date(System.currentTimeMillis())));

        return username.equals(subject) &&  date.after(new Date(System.currentTimeMillis()));
    }

    public static String generateToken(User user){

        Map<String,Object> claims = new HashMap<>();

        claims.put("roles",user.getAuthorities());

        log.info(user.getUsername());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 60000 * 60))
                .setSubject(user.getUsername())
                .setIssuer(ISSUER)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(User user){

        return Jwts.builder()
                .setSubject(user.getUsername())
                .compact();
    }

}
