package br.edu.ifg.luziania.pw.model;

public class AutenticacaoDTO {
  //atributos
  private String email;
  private String senha;

  //construtor vazio
  public AutenticacaoDTO(){
    email = "";
    senha = "";
  }

  //contrutor completo
  public AutenticacaoDTO(String senha, String email) {
    this.senha = senha;
    this.email = email;
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
}
