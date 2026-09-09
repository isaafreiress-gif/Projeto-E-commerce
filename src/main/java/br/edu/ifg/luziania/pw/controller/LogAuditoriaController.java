package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.LogAuditoriaBO;
import br.edu.ifg.luziania.pw.model.dto.LogAuditoriaDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("auditoria")
public class LogAuditoriaController {

  public static final List<LogAuditoriaDTO> LOGS = new ArrayList<>();

  @Inject
  LogAuditoriaBO logAuditoriaBO;

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
    return logAuditoriaBO.listar();
  }
}
