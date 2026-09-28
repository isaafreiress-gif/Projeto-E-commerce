package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dao.PedidoDAO;
import br.edu.ifg.luziania.pw.model.dao.UsuarioDAO;
import br.edu.ifg.luziania.pw.model.dto.DashboardDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class DashboardBO {

  @Inject
  PedidoDAO pedidoDAO;

  @Inject
  UsuarioDAO usuarioDAO;

  public Response stats() {
    double vendasValor = pedidoDAO.somarValorTotal();
    int vendasQtd = (int) pedidoDAO.contarTodos();
    int usuariosQtd = (int) usuarioDAO.contarTodos();

    return Response.ok(new DashboardDTO(vendasValor, vendasQtd, usuariosQtd)).build();
  }
}
