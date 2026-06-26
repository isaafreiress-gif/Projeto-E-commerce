package br.edu.ifg.luziania.pw.model;

public class AutenticacaoDTO {
    // Atributos
    private String email;
    private String senha;

    //Construtor vazio
    public AutenticacaoDTO() {
      email = "";
      senha = "";
    }

    //Construtor completo
    public AutenticacaoDTO(String email, String senha) {
      this.email = email;
      this.senha = senha;
    }

    // Getters and Setters
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
