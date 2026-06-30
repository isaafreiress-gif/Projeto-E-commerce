package br.edu.ifg.luziania.pw.controller;

import br.edu.ifg.luziania.pw.model.UsuarioDTO;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("usuario")
public class UsuarioController {

  @CheckedTemplate
  public static class Templates {
    public static native TemplateInstance usuario();
  }

  // 1. Mantém sua rota que exibe a página HTML
  @GET
  @Produces(MediaType.TEXT_HTML)
  public TemplateInstance usuario() {
    return UsuarioController.Templates.usuario();
  }

  // 2. ADICIONE ESTA NOVA ROTA: Retorna os dados para o JavaScript
  @GET
  @Path("/lista")
  @Produces(MediaType.APPLICATION_JSON)
  public List<UsuarioDTO> listarUsuarios() {
    List<UsuarioDTO> lista = new ArrayList<>();

    // Dados simulados (substitua pela busca do seu banco de dados depois)
    lista.add(new UsuarioDTO(1, "Ana Silva", "ana.silva@email.com"));
    lista.add(new UsuarioDTO(2, "Carlos Souza", "carlos@email.com"));
    lista.add(new UsuarioDTO(3, "Mariana Costa", "mari@email.com"));

    return lista;
  }
}
