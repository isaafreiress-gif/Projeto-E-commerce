package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.UsuarioBO;
import br.edu.ifg.luziania.pw.model.dto.CadastroDTO;
import br.edu.ifg.luziania.pw.model.dto.UsuarioDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("cadastroadm")
public class CadastroAdmController {

  @Inject
  UsuarioBO usuarioBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance cadastroadm();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance pagina() {
    return Templates.cadastroadm();
  }

  @GET
  @Path("todos")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UsuarioDTO> listarTodosUsuarios() {
    return usuarioBO.listarTodosUsuarios();
  }

  @POST
  @Path("registrar")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response registrarAdmin(CadastroDTO dto) {
    return usuarioBO.registrarAdmin(dto);
  }

  @PUT
  @Path("alterar-perfil/{id}")
  public Response alterarPerfil(@PathParam("id") Integer id, String novoPerfil) {
    return usuarioBO.alterarPerfil(id, novoPerfil);
  }

  @DELETE
  @Path("excluir/{id}")
  public Response excluirUsuario(@PathParam("id") Integer id) {
    return usuarioBO.excluirUsuario(id);
  }
}
