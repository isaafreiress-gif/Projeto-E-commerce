package br.edu.ifg.luziania.pw.model;

public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String email;

    public UsuarioDTO() {
      id = 0;
      nome = "";
      email = "";
    }

    public UsuarioDTO(Integer id, String nome, String email) {
      this.id = id;
      this.nome = nome;
      this.email = email;
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
  }

