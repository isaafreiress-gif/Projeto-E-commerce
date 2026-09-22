package br.edu.ifg.luziania.pw.model.dto;

public class UsuarioDTO {
  private Integer id;
  private String nome;
  private String email;
  private String perfil;

  public UsuarioDTO() {
    this.id = 0;
    this.nome = "";
    this.email = "";
    this.perfil = "";
  }

  public UsuarioDTO(Integer id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.perfil = "";
  }

  public UsuarioDTO(Integer id, String nome, String email, String perfil) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.perfil = perfil;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPerfil() {
    return perfil;
  }

  public void setPerfil(String perfil) {
    this.perfil = perfil;
  }
}
