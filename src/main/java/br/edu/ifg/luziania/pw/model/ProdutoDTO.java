package br.edu.ifg.luziania.pw.model;

public class ProdutoDTO {
    private String nome;
    private Double preco;
    private String descricao;

    // Construtor vazio obrigatório para o Jackson
    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, Double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
