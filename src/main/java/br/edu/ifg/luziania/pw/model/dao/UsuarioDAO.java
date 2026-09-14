package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.Response;

import java.util.List;

@RequestScoped
public class UsuarioDAO {

  @Inject
  EntityManager entityManager;

  public void insert(Usuario entity) {
    entityManager.persist(entity);
  }

  public void update(Usuario entity) {
    entityManager.merge(entity);
  }

  public void delete(Usuario entity) {
    entityManager.remove(entity);
  }

  public List<UsuarioDTO> listarTodos() {
    //language=jpql
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.UsuarioDTO(u.id, u.nome, u.email) from Usuario u";
    return entityManager.createQuery(jpql).getResultList();
  }

  public Usuario buscarPorEmail(String email) {
    //language=jpql
    String jpql = "from Usuario u where u.email = :email";
    List<Usuario> resultado = entityManager.createQuery(jpql, Usuario.class)
      .setParameter("email", email)
      .getResultList();
    return resultado.isEmpty() ? null : resultado.get(0);
  }
}
