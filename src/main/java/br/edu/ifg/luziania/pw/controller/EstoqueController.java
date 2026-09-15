package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.EstoqueBO;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

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
  public Response deletar(@PathParam("id") Integer id, @HeaderParam("X-Usuario") String usuario) {
    return estoqueBO.deletar(id, usuario);
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response editar(@PathParam("id") Integer id, ProdutoDTO dados, @HeaderParam("X-Usuario") String usuario) {
    return estoqueBO.editar(id, dados, usuario);
  }
}
