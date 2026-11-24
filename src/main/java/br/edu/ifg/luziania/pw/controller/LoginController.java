package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.AutenticacaoBO;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("login")
public class LoginController {

  @Inject
  AutenticacaoBO autenticacaoBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance login();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance login() {
    return Templates.login();
  }

  @POST
  @Path("autenticacao")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response autenticar(AutenticacaoDTO dto) {
    return autenticacaoBO.autenticar(dto);
  }

  // O cookie é HttpOnly (o JavaScript não consegue apagar), então o servidor o expira
  @POST
  @Path("logout")
  public Response logout() {
    NewCookie expirado = new NewCookie.Builder("jwt-token")
      .value("")
      .path("/")
      .maxAge(0)
      .httpOnly(true)
      .build();
    return Response.ok().cookie(expirado).build();
  }
}
