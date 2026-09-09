package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.controller.ProdutoController;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class EstoqueBO {

  @Inject
  LogAuditoriaBO logAuditoriaBO;

  public void inicializar() {
    if (ProdutoController.PRODUTOS.isEmpty()) {
      ProdutoController.PRODUTOS.add(new ProdutoDTO(1, "Teclado Mecânico RGB", 299.90, "img/teclado.webp", "Teclado com iluminação customizável"));
      ProdutoController.PRODUTOS.add(new ProdutoDTO(2, "Mouse Gamer 16000 DPI", 189.90, "img/mouses.webp", "Mouse de alta precisão"));
    }
  }

  public List<ProdutoDTO> listar() {
    return ProdutoController.PRODUTOS;
  }

  public Response deletar(Integer id, String usuario) {

    ProdutoDTO produto = ProdutoController.PRODUTOS.stream()
      .filter(p -> p.getId().equals(id))
      .findFirst()
      .orElse(null);

    boolean removido = ProdutoController.PRODUTOS.removeIf(p -> p.getId().equals(id));

    if (removido) {
      String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
      logAuditoriaBO.registrar(
        "Removeu o produto \"" + produto.getNome() + "\" (ID: " + id + ")",
        executor
      );
    }

    return Response.noContent().build();
  }

  public Response editar(Integer id, ProdutoDTO dados, String usuario) {

    Response validacao = validar(dados);
    if (validacao != null) {
      return validacao;
    }

    for (ProdutoDTO p : ProdutoController.PRODUTOS) {
      if (p.getId().equals(id)) {
        p.setNome(dados.getNome());
        p.setPreco(dados.getPreco());

        String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
        logAuditoriaBO.registrar(
          "Editou o produto \"" + p.getNome() + "\" (ID: " + id + ")",
          executor
        );
        break;
      }
    }

    return Response.ok(dados).build();
  }

  private Response validar(ProdutoDTO dto) {
    if (dto.getNome() == null || dto.getNome().isBlank()) {
      return Response.status(Response.Status.BAD_REQUEST)
        .entity("Nome do produto é obrigatório").build();
    }
    if (dto.getPreco() == null || dto.getPreco() <= 0) {
      return Response.status(Response.Status.BAD_REQUEST)
        .entity("Preço deve ser maior que zero").build();
    }
    return null;
  }
}







