package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.AutenticacaoDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("login")
public class LoginController {

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

  @GET      //busca os dados html
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance login() {
    return Templates.login();
  }

  @POST  //envia pro servidor
  @Path("autenticacao")
  @Consumes(MediaType.APPLICATION_JSON)                        //rota base pra autenticar
  public Response autenticar(AutenticacaoDTO dto) {

    String[] usuario = USUARIOS.get(dto.getEmail());

    if (usuario == null || !usuario[0].equals(dto.getSenha())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // Devolve só o perfil no corpo da resposta ("ADMIN" ou "CLIENTE")
    return Response.ok(usuario[1]).build();
  }
}
