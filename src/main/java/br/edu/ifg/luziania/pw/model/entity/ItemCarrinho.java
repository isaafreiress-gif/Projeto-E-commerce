package br.edu.ifg.luziania.pw.model.entity;

import br.edu.ifg.luziania.pw.model.dto.CarrinhoDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "item_carrinho")
public class ItemCarrinho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String nome;
  Double preco;
  Integer quantidade;

  public ItemCarrinho() {
  }

  public ItemCarrinho(CarrinhoDTO dto) {
    this.nome = dto.getNome();
    this.preco = dto.getPreco();
    this.quantidade = dto.getQuantidade();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(Integer quantidade) {
    this.quantidade = quantidade;
  }
}
