package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.ProdutoBO;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("produto")
public class ProdutoController {

  public static final List<ProdutoDTO> PRODUTOS = new ArrayList<>();

  @Inject
  ProdutoBO produtoBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance produto();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance produto() {
    return Templates.produto();
  }

  @GET
  @Path("lista")
  @Produces(MediaType.APPLICATION_JSON)
  public Response lista() {
    return produtoBO.listar();
  }
}
