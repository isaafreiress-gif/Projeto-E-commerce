package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.ProdutoDAO;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import br.edu.ifg.luziania.pw.model.entity.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class EstoqueBO {

  @Inject
  ProdutoDAO dao;

  @Inject
  LogAuditoriaBO logAuditoriaBO;

  public List<ProdutoDTO> listar() {
    return dao.listarTodos();
  }

  @Transactional
  public Response deletar(Integer id, String usuario) {

    Produto produto = dao.buscarPorId(id);

    if (produto == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    dao.delete(produto);

    String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
    logAuditoriaBO.registrar(
      "Removeu o produto \"" + produto.getNome() + "\" (ID: " + id + ")",
      executor
    );

    return Response.noContent().build();
  }

  @Transactional
  public Response editar(Integer id, ProdutoDTO dados, String usuario) {

    Response validacao = validar(dados);
    if (validacao != null) {
      return validacao;
    }

    Produto produto = dao.buscarPorId(id);
    if (produto == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    produto.setNome(dados.getNome());
    produto.setPreco(dados.getPreco());
    dao.update(produto);

    String executor = (usuario == null || usuario.isBlank()) ? "Não Autenticado (Visitante)" : usuario;
    logAuditoriaBO.registrar(
      "Editou o produto \"" + produto.getNome() + "\" (ID: " + id + ")",
      executor
    );

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
