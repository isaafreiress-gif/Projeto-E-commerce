package br.edu.ifg.luziania.pw.model;

public class CarrinhoDTO {
  private String nome;
  private Double preco;
  private Integer quantidade;

  public CarrinhoDTO() {
    this.nome = "";
    this.preco = 0.0;
    this.quantidade = 1;
  }

  public CarrinhoDTO(String nome, Double preco, Integer quantidade) {
    this.nome = nome;
    this.preco = preco;
    this.quantidade = quantidade;
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
