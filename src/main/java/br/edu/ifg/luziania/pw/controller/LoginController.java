package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.UsuarioBO;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("login")
public class LoginController {

  @Inject
  UsuarioBO usuarioBO;

  // identificador (email do cliente OU id do admin) -> {senha, perfil}
  public static final Map<String, String[]> USUARIOS = new HashMap<>();

  static {
    USUARIOS.put("isadora@gmail.com", new String[]{"123456", "CLIENTE"});
    USUARIOS.put("202410", new String[]{"admin123", "ADMIN"});
  }

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance login();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance login() {
    return Templates.login();
  }

  @POST
  @Path("autenticacao")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response autenticar(AutenticacaoDTO dto) {
    return usuarioBO.autenticar(dto);
  }
}
