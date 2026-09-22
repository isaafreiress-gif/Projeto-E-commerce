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
    return registrar(dto);
  }

  public List<UsuarioDTO> listarAdmins() {
    return dao.listarPorPerfil("ADMIN");
  }

  @Transactional
  public Response excluirAdmin(Integer id) {
    if (id == null) {
      return Response.status(Response.Status.BAD_REQUEST).entity("ID inválido.").build();
    }

    Usuario usuario = dao.buscarPorId(id);
    if (usuario == null) {
      return Response.status(Response.Status.NOT_FOUND).entity("Administrador não encontrado.").build();
    }

    if (!"ADMIN".equalsIgnoreCase(usuario.getPerfil())) {
      return Response.status(Response.Status.FORBIDDEN)
        .entity("Só é permitido excluir usuários com perfil ADMIN.").build();
    }

    long totalAdmins = dao.contarPorPerfil("ADMIN");
    if (totalAdmins <= 1) {
      return Response.status(Response.Status.CONFLICT)
        .entity("Não é possível excluir o último administrador do sistema.").build();
    }

    dao.delete(usuario);
    return Response.ok().build();
  }
}
