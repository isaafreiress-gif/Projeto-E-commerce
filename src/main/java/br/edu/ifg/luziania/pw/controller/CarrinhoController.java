package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.CarrinhoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("carrinho")
public class CarrinhoController {

  // Lista única, compartilhada, guardada em memória
  private static final List<CarrinhoDTO> ITENS = new ArrayList<>();

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance carrinho();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance carrinho() {
    return Templates.carrinho();
  }

  @GET
  @Path("lista")
  @Produces(MediaType.APPLICATION_JSON)
  public Response lista() {
    return Response.ok(ITENS).build();
  }

  @POST
  @Path("adicionar")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response adicionar(CarrinhoDTO dto) {

    // Procura se o produto já está no carrinho
    for (CarrinhoDTO item : ITENS) {
      if (item.getNome().equals(dto.getNome())) {
        item.setQuantidade(item.getQuantidade() + 1);
        return Response.ok().build();
      }
    }

    // Se não estava, adiciona como novo item
    ITENS.add(new CarrinhoDTO(dto.getNome(), dto.getPreco(), 1));
    return Response.ok().build();
  }

  @DELETE
  @Path("remover/{nome}")
  public Response remover(@PathParam("nome") String nome) {
    ITENS.removeIf(item -> item.getNome().equals(nome));
    return Response.ok().build();
  }

  @POST
  @Path("finalizar")
  public Response finalizar() {
    ITENS.clear();
    return Response.ok().build();
  }
}
