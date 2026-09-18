package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.LogAuditoriaDAO;
import br.edu.ifg.luziania.pw.model.entity.LogAuditoria;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class LogAuditoriaBO {

  @Inject
  LogAuditoriaDAO dao;

  @Transactional
  public void registrar(String acao, String usuarioExecutor) {
    dao.insert(new LogAuditoria(acao, usuarioExecutor));
  }

  public Response listar() {
    return Response.ok(dao.listarTodos()).build();
  }
}
