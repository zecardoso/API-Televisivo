package com.apitelevisivo.security.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.apitelevisivo.model.Role;
import com.apitelevisivo.service.exceptions.InvalidJwtAuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {

    private String secretKey = "secret";
    private long validadeEmMilleSegundos = 3600000;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date hoje = new Date();
        Date dataValida = new Date(hoje.getTime() + validadeEmMilleSegundos);
        return Jwts.builder().setClaims(claims).setIssuedAt(dataValida).setExpiration(dataValida).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

	public Authentication getAuthentication(String token) {
        UserDetails usuario = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(usuario, "", usuario.getAuthorities());
	}

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

	public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
		return null;
	}

	public boolean validateToken(String token) {
		try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Token expirado ou invalido");
        }
        return true;
	}
}