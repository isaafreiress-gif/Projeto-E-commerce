package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.EstoqueBO;
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
import java.util.List;

@RolesAllowed("ADMIN")
@Path("estoque")
public class EstoqueController {

  @Inject
  EstoqueBO estoqueBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance estoque();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance estoque() { return Templates.estoque(); }

  @GET
  @Path("lista")
  @Produces(MediaType.APPLICATION_JSON)
  public List<ProdutoDTO> lista() { return estoqueBO.listar(); }

  @DELETE
  @Path("{id}")
  public Response deletar(@PathParam("id") Integer id, @Context SecurityContext security) {
    return estoqueBO.deletar(id, security.getUserPrincipal().getName());
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response editar(@PathParam("id") Integer id, ProdutoDTO dados, @Context SecurityContext security) {
    return estoqueBO.editar(id, dados, security.getUserPrincipal().getName());
  }
}
