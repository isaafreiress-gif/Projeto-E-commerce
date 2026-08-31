package br.edu.ifg.luziania.pw.model.dto;

public class ProdutoDTO {
  private Integer id;
  private String nome;
  private Double preco;
  private String imagem;
  private String descricao;

  public ProdutoDTO() {
    this.id = 0;
    this.nome = "";
    this.preco = 0.0;
    this.imagem = "";
    this.descricao = "";
  }

  public ProdutoDTO(Integer id, String nome, Double preco, String imagem, String descricao) {
    this.id = id;
    this.nome = nome;
    this.preco = preco;
    this.imagem = imagem;
    this.descricao = descricao;
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
