package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.ProdutoBO;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@RolesAllowed("ADMIN")
@Path("cadastroproduto")
public class CadastroProdutoController {

  @Inject
  ProdutoBO produtoBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance cadastroproduto();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance cadastroproduto() {
    return Templates.cadastroproduto();
  }

  @POST
  @Path("registrar")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registrar(ProdutoDTO dto, @Context SecurityContext security) {
    return produtoBO.registrar(dto, security.getUserPrincipal().getName());
  }
}
