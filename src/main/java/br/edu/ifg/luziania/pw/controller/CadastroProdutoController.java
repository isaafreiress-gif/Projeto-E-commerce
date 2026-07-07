package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.ProdutoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cadastroproduto")
public class CadastroProdutoController {

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
  public Response registrar(ProdutoDTO dto, @HeaderParam("X-Usuario") String usuario) {

    if (dto.getNome() == null || dto.getNome().isBlank()) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    if (dto.getPreco() == null || dto.getPreco() <= 0) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    int novoId = ProdutoController.PRODUTOS.size() + 1;
    dto.setId(novoId);
    ProdutoController.PRODUTOS.add(dto);

    String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;

    LogAuditoriaController.registrar(
      "Cadastrou o produto \"" + dto.getNome() + "\" (ID: " + dto.getId() + ")",
      executor
    );

    return Response.ok().build();
  }
}
