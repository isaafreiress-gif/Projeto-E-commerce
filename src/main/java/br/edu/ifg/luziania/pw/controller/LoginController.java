package br.edu.ifg.luziania.pw.controller;


import br.edu.ifg.luziania.pw.model.AutenticacaoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("login")
public class LoginController {

  @CheckedTemplate
  public static class Templates{
    public static native TemplateInstance login();

  }

    @GET()
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance login() {
      return Templates.login();
    }

    @POST
    @Path("autenticar")
    @Consumes (MediaType.APPLICATION_JSON)
    public Response autenticacao(AutenticacaoDTO dto) {
      return Response.ok().build();

    }
}
