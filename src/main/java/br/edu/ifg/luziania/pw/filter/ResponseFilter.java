package br.edu.ifg.luziania.pw.filter;

import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.NewCookie;
import org.jboss.resteasy.reactive.server.ServerResponseFilter;

public class ResponseFilter {

  private static final String HEADER_JWT = "jwt-token";
  // Deve coincidir com mp.jwt.token.cookie do application.properties
  private static final String COOKIE_JWT = "jwt-token";

  @ServerResponseFilter
  public void responseFilter(ContainerResponseContext responseContext) {
    String token = responseContext.getHeaderString(HEADER_JWT);

    if (token == null) {
      return;
    }

    // O token não pode ficar visível ao JavaScript
    responseContext.getHeaders().remove(HEADER_JWT);

    NewCookie cookie = new NewCookie.Builder(COOKIE_JWT)
      .value(token)
      .path("/")
      .maxAge(3600)
      .secure(false) // em produção (HTTPS) use true
      .httpOnly(true)
      .sameSite(NewCookie.SameSite.LAX)
      .build();

    responseContext.getHeaders().add("Set-Cookie", cookie);
  }
}
