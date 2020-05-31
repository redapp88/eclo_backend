package com.redapp.eclo.security;																																																																																																																																																																																																																																																																																	
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redapp.eclo.entities.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthntificationFilter extends UsernamePasswordAuthenticationFilter {
private AuthenticationManager authenticationManager;
	public JWTAuthntificationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

	AppUser appUser=null;
	
	try {
		appUser=new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		throw new RuntimeException(e);
	} 
	System.out.println(appUser);
	return this.authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
	
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		ExtendedUser springUser=(ExtendedUser) authResult.getPrincipal();
		
		String jwtToken=Jwts.builder()
				.setSubject(springUser.getUsername())
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.claim("roles", springUser.getAuthorities())
				.claim("experition", SecurityConstants.EXPIRATION_TIME)
				.claim("name", springUser.getName())
				.claim("categorie", springUser.getCategorie())
				.claim("sex", springUser.getSex())
				.claim("area", springUser.getArea())
				.claim("phone", springUser.getPhone())
				.claim("status", springUser.getStatus())
				.compact();
		response.addHeader(SecurityConstants.HEADER_STRING,
				SecurityConstants.TOKEN_PREFIX+jwtToken);
				
				
		
	}

}
