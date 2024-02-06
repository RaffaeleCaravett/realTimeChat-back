package com.example.realTimeChat.security;



import com.example.realTimeChat.exception.UnauthorizedException;
import com.example.realTimeChat.payloads.entities.Token;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;
    public Token createToken(User user){

        String accessToken= Jwts.builder().setSubject(String.valueOf(user.getId()))// Subject <-- A chi appartiene il token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 45) ) // Data di scadenza (Expiration Date)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        String refreshToken=Jwts.builder().setSubject(String.valueOf(user.getId()))// Subject <-- A chi appartiene il token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data di emissione (IAT - Issued At)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 ) ) // Data di scadenza (Expiration Date)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

       Token token = new Token(accessToken,refreshToken);

        return token;

    } // Si utilizza al login

    public User verifyToken(String token){
        try {
          Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            return userRepository.findById(Long.valueOf(userId)).get();
        } catch (Exception ex){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }

    } // Si utilizza in tutte le request autenticate


    public Token verifyRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();

            return this.createToken(userRepository.findById(Long.valueOf(userId)).get());
        } catch (Exception ex){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }

    }


    public String extractIdFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
        // Nel body (payload) del token ci sono il subject( che è l'ID dell'utente), la data di emissione, e la data di scadenza.
        // A noi interessa l'id dell'utente
    }
}
