package br.edu.ifg.luziania.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/loginadm") // 1. O @Path DEVE ficar aqui, antes da classe principal
public class LoginadmController {

  @CheckedTemplate
  public static class Templates {
    // Isso vai procurar um arquivo em src/main/resources/templates/LoginadmController/loginadm.html
    public static native TemplateInstance loginadm();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance loginadm() {
    // 2. Simplificado o retorno chamando direto a classe interna do escopo
    return Templates.loginadm();
  }
}
