package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.LogAuditoriaDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/auditoria")
public class LogAuditoriaController {

  public static final List<LogAuditoriaDTO> LOGS = new ArrayList<>();
  private static int proximoId = 1;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance auditoria();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance get() {
    return Templates.auditoria();
  }

  @GET
  @Path("lista")
  @Produces(MediaType.APPLICATION_JSON)
  public Response lista() {
    List<LogAuditoriaDTO> copia = new ArrayList<>(LOGS);
    Collections.reverse(copia); // mais novo primeiro
    return Response.ok(copia).build();
  }

  public static void registrar(String acao, String usuarioExecutor) {
    LOGS.add(new LogAuditoriaDTO(
      proximoId++,
      acao,
      usuarioExecutor,
      LocalDateTime.now()
    ));
  }
}
