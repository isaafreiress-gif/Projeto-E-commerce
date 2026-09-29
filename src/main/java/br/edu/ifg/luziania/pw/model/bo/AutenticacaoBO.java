package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.AutenticacaoDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class AutenticacaoBO {

  @Inject
  UsuarioDAO dao;

  @Inject
  LogAuditoriaBO logAuditoriaBO; // Injeção do BO de Auditoria

  @Transactional
  public Response autenticar(AutenticacaoDTO dto) {
    Usuario usuario = dao.buscarPorEmail(dto.getEmail());

    // Se a senha estiver incorreta ou o usuário não existir
    if (usuario == null || !usuario.getSenha().equals(dto.getSenha())) {
      String executor = (dto.getEmail() != null && !dto.getEmail().isBlank()) ? dto.getEmail() : "Desconhecido";
      logAuditoriaBO.registrar("FALHA_LOGIN", "Tentativa de login com e-mail: " + executor);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    // Registra o sucesso no login
    logAuditoriaBO.registrar("LOGIN_SUCESSO", usuario.getEmail());

    return Response.ok(usuario.getPerfil()).build();
  }
}
