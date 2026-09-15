package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import br.edu.ifg.luziania.pw.model.entity.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class ProdutoDAO {

  @Inject
  EntityManager entityManager;

  public void insert(Produto entity) {
    entityManager.persist(entity);
  }

  public void update(Produto entity) {
    entityManager.merge(entity);
  }

  public void delete(Produto entity) {
    entityManager.remove(entity);
  }

  public Produto buscarPorId(Integer id) {
    return entityManager.find(Produto.class, id);
  }

  public List<ProdutoDTO> listarTodos() {
    //language=jpql
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.ProdutoDTO(p.id, p.nome, p.preco, p.imagem, p.descricao) from Produto p";
    return entityManager.createQuery(jpql, ProdutoDTO.class).getResultList();
  }

  public long contarTodos() {
    //language=jpql
    String jpql = "select count(p) from Produto p";
    return entityManager.createQuery(jpql, Long.class).getSingleResult();
  }
}
