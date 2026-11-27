package br.edu.ifg.luziania.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// Sem @RolesAllowed: a página de "acesso negado" precisa ser pública
@Path("acessonegado")
public class AcessoNegadoController {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance acessonegado();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance acessonegado() {
    return Templates.acessonegado();
  }
}
