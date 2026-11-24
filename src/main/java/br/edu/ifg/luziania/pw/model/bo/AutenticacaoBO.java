package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.time.ZonedDateTime;

@RequestScoped
public class AutenticacaoBO {

  @Inject
  UsuarioDAO dao;

  @Inject
  LogAuditoriaBO logAuditoriaBO;

  @Transactional
  public Response autenticar(AutenticacaoDTO dto) {
    Usuario usuario = dao.buscarPorEmail(dto.getEmail());

    if (usuario == null || !usuario.getSenha().equals(dto.getSenha())) {
      String executor = (dto.getEmail() != null && !dto.getEmail().isBlank()) ? dto.getEmail() : "Desconhecido";
      logAuditoriaBO.registrar("FALHA_LOGIN", "Tentativa de login com e-mail: " + executor);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    logAuditoriaBO.registrar("LOGIN_SUCESSO", usuario.getEmail());

    String perfil = perfilDe(usuario);
    String token = gerarJWT(usuario, perfil);

    // Corpo: perfil (o login.js usa para escolher a tela)
    // Cabeçalho jwt-token: o ResponseFilter transforma em cookie HttpOnly
    return Response.ok(perfil).header("jwt-token", token).build();
  }

  private String perfilDe(Usuario usuario) {
    if (usuario.getPerfil() == null || usuario.getPerfil().isBlank()) {
      return "CLIENTE";
    }
    return usuario.getPerfil().trim().toUpperCase();
  }

  private String gerarJWT(Usuario usuario, String perfil) {
    return Jwt
      .issuer("http://localhost/issuer")
      .upn(usuario.getEmail())
      .groups(perfil)
      .claim("id", usuario.getId())
      .claim("nome", usuario.getNome())
      .claim("data", ZonedDateTime.now().toString())
      .expiresIn(Duration.ofHours(1))
      .sign();
  }
}
