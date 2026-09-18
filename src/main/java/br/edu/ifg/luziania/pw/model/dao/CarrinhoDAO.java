package br.edu.ifg.luziania.pw.model.dao;

import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import br.edu.ifg.luziania.pw.model.entity.ItemCarrinho;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

@RequestScoped
public class CarrinhoDAO {

  @Inject
  EntityManager entityManager;

  public void insert(ItemCarrinho entity) {
    entityManager.persist(entity);
  }

  public void update(ItemCarrinho entity) {
    entityManager.merge(entity);
  }

  public void deleteTodosPorNome(String nome) {
    //language=jpql
    String jpql = "delete from ItemCarrinho i where i.nome = :nome";
    entityManager.createQuery(jpql)
      .setParameter("nome", nome)
      .executeUpdate();
  }

  public void limparTudo() {
    //language=jpql
    entityManager.createQuery("delete from ItemCarrinho").executeUpdate();
  }

  public ItemCarrinho buscarPorNome(String nome) {
    //language=jpql
    String jpql = "from ItemCarrinho i where i.nome = :nome";
    List<ItemCarrinho> resultado = entityManager.createQuery(jpql, ItemCarrinho.class)
      .setParameter("nome", nome)
      .getResultList();
    return resultado.isEmpty() ? null : resultado.get(0);
  }

  public List<CarrinhoDTO> listarTodos() {
    //language=jpql
    String jpql = "select new br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO(i.nome, i.preco, i.quantidade) from ItemCarrinho i";
    return entityManager.createQuery(jpql, CarrinhoDTO.class).getResultList();
  }
}
