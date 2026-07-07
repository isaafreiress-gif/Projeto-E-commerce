package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.CadastroDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cadastro")
public class CadastroController {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance cadastro();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance cadastro() {
    return Templates.cadastro();
  }

  @POST
  @Path("registrar")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registrar(CadastroDTO dto) {

    // Verifica se o e-mail já existe no mapa de usuários do LoginController
    if (LoginController.USUARIOS.containsKey(dto.getEmail())) {
      return Response.status(Response.Status.CONFLICT).build();
    }

    // Sem checagem de sessão (sem cookie): todo cadastro feito por aqui
    // entra sempre como CLIENTE, por segurança básica
    LoginController.USUARIOS.put(
      dto.getEmail(),
      new String[]{dto.getSenha(), "CLIENTE"}
    );

    return Response.ok().build();
  }
}
