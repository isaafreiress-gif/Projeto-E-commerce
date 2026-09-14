package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.model.dto.DashboardDTO;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class DashboardBO {

  public Response stats() {
    // Dados simulados (substituir por consulta real via DAO quando tiver persistência)
    DashboardDTO dashboard = new DashboardDTO(45890.50, 132, 87);
    return Response.ok(dashboard).build();
  }
}
