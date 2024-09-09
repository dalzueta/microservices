package org.daniel.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.MediaType;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

  @Value("${auth.api.url}")
  private String authApiUrl;

  private final RestTemplate restTemplate = new RestTemplate();  // RestTemplate reutilizable

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    String tokenAuthorization = request.getHeader("Authorization");

    // si no manda ningun token  chau
    if (tokenAuthorization == null || (!tokenAuthorization.startsWith("Bearer ") && !tokenAuthorization.startsWith("Basic "))) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    String tokenType = tokenAuthorization.startsWith("Bearer ") ? "Bearer" : "Basic";

    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("token", tokenAuthorization);
    requestBody.put("token_type", tokenType);
    HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

    try {
      // hacemos el post
      ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
          authApiUrl + "/validate-token",
          HttpMethod.POST,
          requestEntity,
          Boolean.class
      );


      if (responseEntity.getStatusCode() == HttpStatus.OK ) {

        CustomUserDetails userDetails = new CustomUserDetails("user", "", true, null);

        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      e.printStackTrace();
      System.out.println("ERRORRRR: " + e.getMessage());
      return;
    }
    filterChain.doFilter(request, response);
  }
}
