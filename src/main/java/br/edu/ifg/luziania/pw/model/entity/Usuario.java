package br.edu.ifg.luziania.pw.model.entity;

import br.edu.ifg.luziania.pw.model.dto.CadastroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String nome;
  String email;
  String senha;
  String perfil; // "ADMIN" ou "CLIENTE"

  public Usuario() {
  }

  public Usuario(CadastroDTO dto) {
    this.nome = dto.getNome();
    this.email = dto.getEmail();
    this.senha = dto.getSenha();
    this.perfil = dto.getPerfilNome();
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

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getPerfil() {
    return perfil;
  }

  public void setPerfil(String perfil) {
    this.perfil = perfil;
  }
}
