package br.edu.ifg.luziania.pw.filter;

import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import java.net.URI;

public class AcessoNegado {

  @ServerExceptionMapper
  public Response naoLogado(UnauthorizedException e, HttpHeaders headers) {
    return responder(401, headers);
  }

  @ServerExceptionMapper
  public Response semPermissao(ForbiddenException e, HttpHeaders headers) {
    return responder(403, headers);
  }

  // Navegador (página) -> /acessonegado. fetch/API -> mantém 401/403 (o carrinho.js usa o 401).
  private Response responder(int status, HttpHeaders headers) {
    String accept = headers.getHeaderString(HttpHeaders.ACCEPT);
    if (accept != null && accept.contains("text/html")) {
      return Response.seeOther(URI.create("/acessonegado")).build();
    }
    return Response.status(status).build();
  }
}
