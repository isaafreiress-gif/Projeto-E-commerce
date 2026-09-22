package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.dto.UsuarioDTO;
import br.edu.ifg.luziania.pw.model.entity.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

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
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.UsuarioDTO(u.id, u.nome, u.email, u.perfil) from Usuario u";
    return entityManager.createQuery(jpql, UsuarioDTO.class).getResultList();
  }

  public List<UsuarioDTO> listarPorPerfil(String perfil) {
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.UsuarioDTO(u.id, u.nome, u.email, u.perfil) from Usuario u where u.perfil = :perfil";
    return entityManager.createQuery(jpql, UsuarioDTO.class)
      .setParameter("perfil", perfil)
      .getResultList();
  }

  public Usuario buscarPorId(Integer id) {
    return entityManager.find(Usuario.class, id);
  }

  public Usuario buscarPorEmail(String email) {
    String jpql = "from Usuario u where u.email = :email";
    List<Usuario> resultado = entityManager.createQuery(jpql, Usuario.class)
      .setParameter("email", email)
      .getResultList();
    return resultado.isEmpty() ? null : resultado.get(0);
  }

  public long contarPorPerfil(String perfil) {
    String jpql = "select count(u) from Usuario u where u.perfil = :perfil";
    return entityManager.createQuery(jpql, Long.class)
      .setParameter("perfil", perfil)
      .getSingleResult();
  }
}
