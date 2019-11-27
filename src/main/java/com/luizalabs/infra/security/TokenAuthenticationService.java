package com.luizalabs.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenAuthenticationService {

  // EXPIRATION_TIME = 10 dias
  static final long EXPIRATION_TIME = 860_000_000;
  static final String SECRET = "luizlab-teste";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "X-Authorization";

  static void addAuthentication(
      HttpServletResponse response,
      String username,
      Collection<? extends GrantedAuthority> authorities) {
    String JWT =
        Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .claim(
                "rol",
                authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()))
            .compact();

    response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);

    if (token != null) {
      Claims body =
          Jwts.parser()
              .setSigningKey(SECRET)
              .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
              .getBody();

      String user = body.getSubject();
      var authorities =
          ((List<?>) body.get("rol"))
              .stream()
                  .map(authority -> new SimpleGrantedAuthority((String) authority))
                  .collect(Collectors.toList());

      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
      }
    }
    return null;
  }
}
