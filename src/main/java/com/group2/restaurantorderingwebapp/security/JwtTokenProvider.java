package com.group2.restaurantorderingwebapp.security;

import com.group2.restaurantorderingwebapp.exception.AppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


     //Tao JWT token
    public  String generateToken(Authentication authentication){
        String userEmail = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder()
                .subject(userEmail)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }

    private SecretKey key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

     public String getUsername(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

     }

     public boolean validateToken(String token){
        try {

        Jwts.parser().verifyWith(key())
                .build()
                .parse(token);
        return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new AppException(HttpStatus.BAD_REQUEST,"Invalid Jwt Token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new AppException(HttpStatus.BAD_REQUEST,"Expired Jwt Token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new AppException(HttpStatus.BAD_REQUEST,"Unsupported Jwt Token");
        }catch(IllegalArgumentException illegalArgumentException){
            throw new AppException(HttpStatus.BAD_REQUEST,"Jwt claims string is null or empty");
        }
    }

}
