package uz.pdp.money_transfer_demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtProvider {
    long expireTime=36000000;
    String yashirinSoz="Hechkimbilmasligikerak";
    
    public String generateToken(String username){
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, yashirinSoz)
                .compact();
        return token;
    }

    public boolean validateToken(String token){
        try {
            Jwts
                    .parser()
                    .setSigningKey(yashirinSoz)
                    .parseClaimsJws(token);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
        }
    return false;
    }

    public  String getUsernameFromToken(String token){
        String username = Jwts
                .parser()
                .setSigningKey(yashirinSoz)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return username;
    }
}
