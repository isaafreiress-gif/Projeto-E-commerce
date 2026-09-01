package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.CarrinhoBO;
import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("carrinho")
public class CarrinhoController {

  // Lista única, compartilhada, guardada em memória
  public static final List<CarrinhoDTO> ITENS = new ArrayList<>();

  @Inject
  CarrinhoBO carrinhoBO;

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
    return carrinhoBO.listar();
  }

  @POST
  @Path("adicionar")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response adicionar(CarrinhoDTO dto) {
    return carrinhoBO.adicionar(dto);
  }

  @DELETE
  @Path("remover/{nome}")
  public Response remover(@PathParam("nome") String nome) {
    return carrinhoBO.remover(nome);
  }

  @POST
  @Path("finalizar")
  public Response finalizar() {
    return carrinhoBO.finalizar();
  }
}
