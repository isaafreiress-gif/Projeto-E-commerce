package br.edu.ifg.luziania.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("cadastroproduto") // A rota certa fica aqui, na classe principal
public class CadastroProdutoController {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance cadastroproduto();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance cadastroproduto() {
    return CadastroProdutoController.Templates.cadastroproduto();
  }
}
