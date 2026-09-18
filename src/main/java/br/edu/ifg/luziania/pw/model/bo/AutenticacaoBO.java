package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class AutenticacaoBO {

  @Inject
  UsuarioDAO dao;

  public Response autenticar(AutenticacaoDTO dto) {
    // Biroken ti usuario iti database babaen ti email
    Usuario usuario = dao.buscarPorEmail(dto.getEmail());

    // Sumungbat ti UNAUTHORIZED (401) no awan ti usuario wenno madi ti password
    if (usuario == null || !usuario.getSenha().equals(dto.getSenha())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // Isungbat ti perfil ("ADMIN" wenno "CLIENTE") no adda ti usuario
    return Response.ok(usuario.getPerfil()).build();
  }
}
