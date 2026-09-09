package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.UsuarioBO;
import br.edu.ifg.luziania.pw.model.dto.CadastroDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cadastro")
public class CadastroController {

  @Inject
  UsuarioBO usuarioBO;

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
    return usuarioBO.registrar(dto);
  }
}
