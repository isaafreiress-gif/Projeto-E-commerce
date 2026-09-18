package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.CarrinhoDAO;
import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import br.edu.ifg.luziania.pw.model.entity.ItemCarrinho;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class CarrinhoBO {

  @Inject
  CarrinhoDAO dao;

  public Response listar() {
    return Response.ok(dao.listarTodos()).build();
  }

  @Transactional
  public Response adicionar(CarrinhoDTO dto) {

    Response validacao = validar(dto);
    if (validacao != null) {
      return validacao;
    }

    ItemCarrinho existente = dao.buscarPorNome(dto.getNome());

    if (existente != null) {
      existente.setQuantidade(existente.getQuantidade() + 1);
      dao.update(existente);
    } else {
      dao.insert(new ItemCarrinho(new CarrinhoDTO(dto.getNome(), dto.getPreco(), 1)));
    }

    return Response.ok().build();
  }

  @Transactional
  public Response remover(String nome) {
    dao.deleteTodosPorNome(nome);
    return Response.ok().build();
  }

  @Transactional
  public Response finalizar() {
    dao.limparTudo();
    return Response.ok().build();
  }

  private Response validar(CarrinhoDTO dto) {
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
