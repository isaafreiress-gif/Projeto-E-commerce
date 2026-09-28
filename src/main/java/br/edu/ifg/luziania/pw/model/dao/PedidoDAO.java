package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.entity.Pedido;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class PedidoDAO {

  @Inject
  EntityManager entityManager;

  public void insert(Pedido entity) {
    entityManager.persist(entity);
  }

  public long contarTodos() {
    //language=jpql
    return entityManager.createQuery("select count(p) from Pedido p", Long.class)
      .getSingleResult();
  }

  public double somarValorTotal() {
    //language=jpql
    Double soma = entityManager.createQuery(
        "select sum(p.valorTotal) from Pedido p", Double.class)
      .getSingleResult();
    return soma == null ? 0.0 : soma;
  }
}
