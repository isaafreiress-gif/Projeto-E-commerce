package br.edu.ifg.luziania.pw.controller;


import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("produto")
public class ProdutoController {

  @CheckedTemplate
  public static class Templates{
    public static native TemplateInstance produto();
  }

  @GET()
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance produto() {
    return ProdutoController.Templates.produto();
  }

}
