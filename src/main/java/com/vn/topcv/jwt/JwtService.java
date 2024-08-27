package com.vn.topcv.jwt;

import com.vn.topcv.entity.User;
import com.vn.topcv.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Data
public class JwtService {

  private static final String SECRET_KEY = "462884FCD356AE84BE6F1125725BF462884FCD356AE84BE6F1125725BF";
  private static final Integer EXPIRATION_TIME = 24;

  public String generateToken(User user) {
	return generateToken(new HashMap<>(), user);
  }

  public String generateToken(Map<String, Object> claims, User user) {
	claims.put("UserId", user.getUserId());
	ZonedDateTime expirationTime = ZonedDateTime.now().plusHours(EXPIRATION_TIME);
	Date expire = Date.from(expirationTime.toInstant());
	return Jwts.builder()
		.setClaims(claims)
		.setSubject(user.getEmail())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(expire)
		.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
		.compact();
  }

  public String generateRefreshToken(User user) {
	Map<String, Object> claims = new HashMap<>();
	claims.put("UserId", user.getUserId());
	ZonedDateTime expirationTime = ZonedDateTime.now().plusDays(7);
	Date expire = Date.from(expirationTime.toInstant());

	return Jwts.builder()
		.setClaims(claims)
		.setSubject(user.getEmail())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(expire)
		.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
		.compact();
  }

  public Claims extractAllClaims(String token) {
	try {
	  return Jwts.parser()
		  .setSigningKey(SECRET_KEY)
		  .parseClaimsJws(token)
		  .getBody();
	} catch (JwtException e) {
	  throw e;
	}
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = extractAllClaims(token);
	return claimsResolver.apply(claims);
  }

  public Long getUserId(String token) {
	return this.extractClaim(token, claims -> claims.get("UserId", Long.class));
  }

  public Date getExpirationTimeFromToken(String token) {
	Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

	return claims.getExpiration();
  }

  public String extractEmailFromToken(String token) {
	try {
	  return extractClaim(token, Claims::getSubject);
	} catch (JwtException e) {
	  throw new CustomException("Invalid JWT token: " + e.getMessage());
	}
  }

  public Date extractExpirationFromToken(String token) {
	return extractClaim(token, Claims::getExpiration);
  }

  public Boolean isTokenExpired(String token) {
	return extractExpirationFromToken(token).before(new Date());
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
	final String email = extractEmailFromToken(token);

	return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
}
