package br.edu.ifg.luziania.pw.controller;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public class DashboardController {
    @Path("dashboard")
    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance dashboard();
    }

    @GET()
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dashboard() {
        return DashboardController.Templates.dashboard();
    }
}



