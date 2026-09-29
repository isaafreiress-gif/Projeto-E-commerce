package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.CadastroDTO;
import br.edu.ifg.luziania.pw.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class UsuarioBO {

  @Inject
  UsuarioDAO dao;

  @Inject
  LogAuditoriaBO logAuditoriaBO;

  @Transactional
  public Response registrar(CadastroDTO dto) {
    if (dto.getEmail() == null || dto.getEmail().isBlank()
      || dto.getSenha() == null || dto.getSenha().isBlank()
      || dto.getNome() == null || dto.getNome().isBlank()) {
      return Response.status(Response.Status.BAD_REQUEST)
        .entity("Preencha todos os campos obrigatórios.").build();
    }

    if (dao.buscarPorEmail(dto.getEmail()) != null) {
      return Response.status(Response.Status.CONFLICT)
        .entity("Esse e-mail já está cadastrado.").build();
    }

    if (dto.getPerfilNome() == null || dto.getPerfilNome().isBlank()) {
      dto.setPerfilNome("CLIENTE");
    }

    Usuario entity = new Usuario(dto);
    dao.insert(entity);

    return Response.ok().build();
  }

  @Transactional
  public Response registrarAdmin(CadastroDTO dto) {
    dto.setPerfilNome("ADMIN");
    Response response = registrar(dto);

    if (response.getStatus() == Response.Status.OK.getStatusCode()) {
      logAuditoriaBO.registrar("CADASTRO_ADMIN", "Novo Admin Cadastrado: " + dto.getEmail());
    }

    return response;
  }

  public List<UsuarioDTO> listarAdmins() {
    return dao.listarPorPerfil("ADMIN");
  }

  public List<UsuarioDTO> listarTodosUsuarios() {
    return dao.listarTodos();
  }

  // Permite múltiplos ADMs!
  @Transactional
  public Response alterarPerfil(Integer id, String novoPerfil) {
    if (id == null || novoPerfil == null || novoPerfil.isBlank()) {
      return Response.status(Response.Status.BAD_REQUEST).entity("Dados inválidos.").build();
    }

    // Limpa aspas ou espaços extras
    novoPerfil = novoPerfil.replace("\"", "").trim();

    Usuario usuarioAlvo = dao.buscarPorId(id);
    if (usuarioAlvo == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
    }

    // AÇÃO 1: Promover para ADMIN (sem rebaixar os outros)
    if ("ADMIN".equalsIgnoreCase(novoPerfil)) {
      usuarioAlvo.setPerfil("ADMIN");
      logAuditoriaBO.registrar("PROMOCAO_ADMIN",
        "Usuário " + usuarioAlvo.getEmail() + " foi promovido a Administrador.");

      return Response.ok().build();
    }

    // AÇÃO 2: Rebaixar para CLIENTE
    if ("CLIENTE".equalsIgnoreCase(novoPerfil)) {
      long totalAdmins = dao.contarPorPerfil("ADMIN");

      // Impede rebaixar se ele for o ÚNICO Admin restante
      if (totalAdmins <= 1 && "ADMIN".equalsIgnoreCase(usuarioAlvo.getPerfil())) {
        return Response.status(Response.Status.CONFLICT)
          .entity("Não é possível rebaixar. O sistema precisa ter pelo menos um Administrador.").build();
      }

      usuarioAlvo.setPerfil("CLIENTE");
      logAuditoriaBO.registrar("REBAIXAMENTO_ADMIN",
        "Usuário " + usuarioAlvo.getEmail() + " foi rebaixado para CLIENTE.");

      return Response.ok().build();
    }

    return Response.status(Response.Status.BAD_REQUEST).entity("Perfil inválido.").build();
  }

  // Exclui Cliente ou Admin
  @Transactional
  public Response excluirUsuario(Integer id) {
    if (id == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("ID inválido.").build();
    }

    Usuario usuario = dao.buscarPorId(id);
    if (usuario == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Usuário não encontrado.").build();
    }

    // Se for um ADMIN, verifica se não é o único antes de apagar
    if ("ADMIN".equalsIgnoreCase(usuario.getPerfil())) {
      long totalAdmins = dao.contarPorPerfil("ADMIN");
      if (totalAdmins <= 1) {
        return Response.status(Response.Status.CONFLICT)
          .entity("Não é possível excluir o único administrador do sistema.").build();
      }
    }

    String emailExcluido = usuario.getEmail();
    String perfilExcluido = usuario.getPerfil();

    dao.delete(usuario);

    logAuditoriaBO.registrar("EXCLUSAO_USUARIO", "Usuário (" + perfilExcluido + ") Excluído: " + emailExcluido);

    return Response.ok().build();
  }
}
