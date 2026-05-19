package net.edigest.journalApp.utilies;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class Jwtutil {

	private String SECRET_KEY = "myVeryStrongSecretKeyForJwtTokenGeneration2026";

	// Generate Secret Key
	private SecretKey getSignkey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	// Extract Username
	public String extractUsername(String token) {
		return extractAllClaims(token).getSubject();
	}

	// Extract Expiration Time
	public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	}

	// Extract All Claims
	private Claims extractAllClaims(String token) {

		return Jwts.parser()
				.verifyWith(getSignkey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	// Check Token Expired or Not
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Generate Token
	public String generateToken(String username) {

		Map<String, Object> claims = new HashMap<>();

		return createToken(claims, username);
	}

	// Create JWT Token
	private String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.header()
				.empty()
				.add("typ", "JWT")
				.and()
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(
						new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignkey())
				.compact();
	}

	// Validate Token
	public Boolean validateToken(String token) {

		return !isTokenExpired(token);
	}
}