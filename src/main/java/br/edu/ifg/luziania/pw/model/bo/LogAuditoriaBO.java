package br.edu.ifg.luziania.pw.model.bo;

import br.edu.ifg.luziania.pw.controller.LogAuditoriaController;
import br.edu.ifg.luziania.pw.model.dto.LogAuditoriaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class LogAuditoriaBO {

  private int proximoId = 1;

  public void registrar(String acao, String usuarioExecutor) {
    LogAuditoriaController.LOGS.add(new LogAuditoriaDTO(
      proximoId++,
      acao,
      usuarioExecutor,
      LocalDateTime.now()
    ));
  }

  public Response listar() {
    List<LogAuditoriaDTO> copia = new ArrayList<>(LogAuditoriaController.LOGS);
    Collections.reverse(copia); // mais novo primeiro
    return Response.ok(copia).build();
  }
}
