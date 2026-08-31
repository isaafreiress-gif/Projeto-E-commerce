package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("estoque")
public class EstoqueController {

  @PostConstruct
  public void init() {
    if (ProdutoController.PRODUTOS.isEmpty()) {
      ProdutoController.PRODUTOS.add(new ProdutoDTO(1, "Teclado Mecânico RGB", 299.90, "img/teclado.webp", "Teclado com iluminação customizável"));
      ProdutoController.PRODUTOS.add(new ProdutoDTO(2, "Mouse Gamer 16000 DPI", 189.90, "img/mouses.webp", "Mouse de alta precisão"));
    }
  }

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
  public List<ProdutoDTO> lista() { return ProdutoController.PRODUTOS; }

  @DELETE
  @Path("{id}")
  public Response deletar(@PathParam("id") Integer id, @HeaderParam("X-Usuario") String usuario) {
    ProdutoDTO produto = ProdutoController.PRODUTOS.stream()
      .filter(p -> p.getId().equals(id))
      .findFirst()
      .orElse(null);

    boolean removido = ProdutoController.PRODUTOS.removeIf(p -> p.getId().equals(id));

    if (removido) {
      String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
      LogAuditoriaController.registrar(
        "Removeu o produto \"" + produto.getNome() + "\" (ID: " + id + ")",
        executor
      );
    }
    return Response.noContent().build();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response editar(@PathParam("id") Integer id, ProdutoDTO dados, @HeaderParam("X-Usuario") String usuario) {
    for (ProdutoDTO p : ProdutoController.PRODUTOS) {
      if (p.getId().equals(id)) {
        p.setNome(dados.getNome());
        p.setPreco(dados.getPreco());

        String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
        LogAuditoriaController.registrar(
          "Editou o produto \"" + p.getNome() + "\" (ID: " + id + ")",
          executor
        );
        break;
      }
    }
    return Response.ok(dados).build();
  }
}
