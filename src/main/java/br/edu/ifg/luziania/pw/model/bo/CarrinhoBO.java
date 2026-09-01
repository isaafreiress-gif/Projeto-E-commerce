package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.controller.CarrinhoController;
import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class CarrinhoBO {

  public Response listar() {
    return Response.ok(CarrinhoController.ITENS).build();
  }
  
 @Transactional
  public Response adicionar(CarrinhoDTO dto) {

    Response validacao = validar(dto);
    if (validacao != null) {
      return validacao;
    }

    // Procura se o produto já está no carrinho
    for (CarrinhoDTO item : CarrinhoController.ITENS) {
      if (item.getNome().equals(dto.getNome())) {
        item.setQuantidade(item.getQuantidade() + 1);
        return Response.ok().build();
      }
    }

    // Se não estava, adiciona como novo item
    CarrinhoController.ITENS.add(new CarrinhoDTO(dto.getNome(), dto.getPreco(), 1));
    return Response.ok().build();
  }

  public Response remover(String nome) {
    CarrinhoController.ITENS.removeIf(item -> item.getNome().equals(nome));
    return Response.ok().build();
  }

  public Response finalizar() {
    CarrinhoController.ITENS.clear();
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
