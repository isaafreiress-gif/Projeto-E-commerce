package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.LogAuditoriaBO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auditoria")
public class LogAuditoriaController {

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
