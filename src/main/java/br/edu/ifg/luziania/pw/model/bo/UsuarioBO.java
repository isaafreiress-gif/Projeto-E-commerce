package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.CadastroDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class UsuarioBO {

  @Inject
  UsuarioDAO dao;

  @Transactional
  public Response registrar(CadastroDTO dto) {

    if (dao.buscarPorEmail(dto.getEmail()) != null) {
      return Response.status(Response.Status.CONFLICT).build();
    }

    Usuario entity = new Usuario(dto);
    dao.insert(entity);

    return Response.ok().build();
  }
}
