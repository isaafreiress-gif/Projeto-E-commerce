package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.CarrinhoDAO;
import br.edu.ifg.luziania.pw.model.dao.PedidoDAO;
import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import br.edu.ifg.luziania.pw.model.entity.ItemCarrinho;
import br.edu.ifg.luziania.pw.model.entity.Pedido;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class CarrinhoBO {

  @Inject
  CarrinhoDAO dao;

  @Inject
  PedidoDAO pedidoDAO;

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
    List<CarrinhoDTO> itens = dao.listarTodos();

    if (itens.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST)
        .entity("Carrinho está vazio").build();
    }

    double valorTotal = 0.0;
    int quantidadeItens = 0;
    for (CarrinhoDTO item : itens) {
      int qtd = item.getQuantidade() == null ? 1 : item.getQuantidade();
      valorTotal += item.getPreco() * qtd;
      quantidadeItens += qtd;
    }

    pedidoDAO.insert(new Pedido(valorTotal, quantidadeItens));
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
