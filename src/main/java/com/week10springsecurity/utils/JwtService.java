package com.week10springsecurity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String  SECRET_KEY = "5A7234753778214125442A472D4B6150645367566B58703273357638792F423F";


            //extract username from the token
    //use claim::getSubject
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //extract a single claim, use a generic key like <T>
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = exctractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //generate token from userDetails
     public  String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(), userDetails);
     }
    //generate Token which will take  parameter a map of String that will contain the claims
    public  String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
            ){
        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //method that validate a token
    public  boolean isTokenValid(String token, UserDetails userDetails){
        final  String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return exctratcExpiration(token).before(new Date());
    }

    private Date exctratcExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims  exctractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        //generate a secret key
    }
    private Key getSignInKey(){
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
