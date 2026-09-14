package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.controller.LoginController;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class AutenticacaoBO {

  public Response autenticar(AutenticacaoDTO dto) {

    String[] usuario = LoginController.USUARIOS.get(dto.getEmail());

    if (usuario == null || !usuario[0].equals(dto.getSenha())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // Devolve só o perfil no corpo da resposta ("ADMIN" ou "CLIENTE")
    return Response.ok(usuario[1]).build();
  }
}
