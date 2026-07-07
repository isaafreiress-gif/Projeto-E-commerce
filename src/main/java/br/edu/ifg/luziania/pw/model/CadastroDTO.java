package br.edu.ifg.luziania.pw.model;

public class CadastroDTO {
  private String nome;
  private String email;
  private String senha;
  private String perfilNome; // "ADMIN" ou "CLIENTE"

  public CadastroDTO() {
    this.nome = "";
    this.email = "";
    this.senha = "";
    this.perfilNome = "CLIENTE";
  }

  public CadastroDTO(String nome, String email, String senha, String perfilNome) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.perfilNome = perfilNome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getPerfilNome() {
    return perfilNome;
  }

  public void setPerfilNome(String perfilNome) {
    this.perfilNome = perfilNome;
  }
}
