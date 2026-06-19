package br.edu.ifg.luziania.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("carrinho")
public class CarrinhoController {
  @CheckedTemplate
  public static class Templates{
    public static native TemplateInstance carrinho();
  }

  @GET()
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance carrinho() {
    return CarrinhoController.Templates.carrinho();
  }
}
