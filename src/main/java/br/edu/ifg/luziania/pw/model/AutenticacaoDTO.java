package br.edu.ifg.luziania.pw.model;

public class AutenticacaoDTO {
  private String email;
  private String senha;

  public AutenticacaoDTO() {
    this.email = "";
    this.senha = "";
  }

  public AutenticacaoDTO(String email, String senha) {
    this.email = email;
    this.senha = senha;
  }

  public String getEmail() {
    return email;
  }  //le

  public void setEmail(String email) {
    this.email = email;
  }    //preenche

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
}
