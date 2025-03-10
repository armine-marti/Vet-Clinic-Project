package org.example.vetclinic.config.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.vetclinic.entity.UserType;
import org.example.vetclinic.security.CurrentUser;
import org.example.vetclinic.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for working with JWT tokens.
 * This class provides methods for generating, validating, and extracting claims from JWT tokens.
 */
@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationInMillis;

    /**
     * Extracts the email (subject) from the given JWT token.
     *
     * @param token the JWT token.
     * @return the email (subject) from the token.
     */
    public String getEmailFromToken(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates a new JWT token for the given user details.
     * The token includes the user email and user type as claims, along with an expiration time.
     *
     * @param userDetails the details of the user.
     * @return the generated JWT token as a string.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("usertype", ((CurrentUser) userDetails).getUserType());

        Instant expirationTime = Instant.now().plusMillis(jwtExpirationInMillis);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(((CurrentUser) userDetails).getUser().getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Checks if the provided JWT token is valid based on the user details.
     * A token is valid if the email in the token matches the user's email and the token is not expired.
     *
     * @param token       the JWT token.
     * @param userDetails the user details for comparison.
     * @return true if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String tokenEmail = getEmailFromToken(token);
        return (tokenEmail.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the provided JWT token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(DateUtil.getCurrentDate());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token.
     * @return the expiration date of the token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claim resolver.
     *
     * @param token         the JWT token.
     * @param claimResolver the function that extracts the specific claim.
     * @param <T>           the type of the claim.
     * @return the value of the specified claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token.
     * @return the claims contained within the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extracts the user type from the JWT token.
     *
     * @param token the JWT token.
     * @return the user type extracted from the token.
     */
    public UserType getUserTypeFromToken(String token) {
        String userTypeString = extractClaim(token, claims -> claims.get("usertype", String.class));
        return UserType.valueOf(userTypeString);
    }

    /**
     * Generates a signing key from the secret key used to sign the JWT token.
     * The secret key is base64 decoded and used to create a signing key.
     *
     * @return the signing key used for JWT token signing.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
