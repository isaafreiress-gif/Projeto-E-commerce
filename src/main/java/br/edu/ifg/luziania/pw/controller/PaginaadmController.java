package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.bo.DashboardBO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("paginaadm")
public class PaginaadmController {

  @Inject
  DashboardBO dashboardBO;

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance paginaadm();
  }

  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance paginaadm() {
    return Templates.paginaadm();
  }

  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public Response stats() {
    return dashboardBO.stats();
  }
}
