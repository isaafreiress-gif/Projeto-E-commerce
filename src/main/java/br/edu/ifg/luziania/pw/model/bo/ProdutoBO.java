package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.ProdutoDAO;
import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import br.edu.ifg.luziania.pw.model.entity.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class ProdutoBO {

  @Inject
  ProdutoDAO dao;

  @Inject
  LogAuditoriaBO logAuditoriaBO;

  @Transactional
  public Response registrar(ProdutoDTO dto, String usuario) {

    Response validacao = validar(dto);
    if (validacao != null) {
      return validacao;
    }

    Produto entity = new Produto(dto);
    dao.insert(entity);
    dto.setId(entity.getId());

    String executor = (usuario == null || usuario.isBlank())
      ? "Não Autenticado (Visitante)"
      : usuario;

    logAuditoriaBO.registrar(
      "Cadastrou o produto \"" + dto.getNome() + "\" (ID: " + dto.getId() + ")",
      executor
    );

    return Response.ok().build();
  }

  public Response listar() {
    return Response.ok(dao.listarTodos()).build();
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
