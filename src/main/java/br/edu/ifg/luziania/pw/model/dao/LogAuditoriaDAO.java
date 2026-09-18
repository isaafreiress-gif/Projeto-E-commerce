package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.dto.LogAuditoriaDTO;
import br.edu.ifg.luziania.pw.model.entity.LogAuditoria;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class LogAuditoriaDAO {

  @Inject
  EntityManager entityManager;

  public void insert(LogAuditoria entity) {
    entityManager.persist(entity);
  }

  public List<LogAuditoriaDTO> listarTodos() {
    //language=jpql
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.LogAuditoriaDTO(l.id, l.acao, l.usuarioExecutor, l.dataHora) " +
      "from LogAuditoria l order by l.dataHora desc";
    return entityManager.createQuery(jpql, LogAuditoriaDTO.class).getResultList();
  }
}
