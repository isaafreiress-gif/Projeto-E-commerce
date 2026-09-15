package br.edu.ifg.luziania.pw.model.entity;

import br.edu.ifg.luziania.pw.model.dto.ProdutoDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  private String nome;
  private Double preco;
  private String imagem;
  private String descricao;

  public Produto() {
  }

  public Produto(ProdutoDTO dto) {
    this.id = dto.getId() == null || dto.getId() == 0 ? null : dto.getId();
    this.nome = dto.getNome();
    this.preco = dto.getPreco();
    this.imagem = dto.getImagem();
    this.descricao = dto.getDescricao();
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

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
