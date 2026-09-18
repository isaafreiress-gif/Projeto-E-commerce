package br.edu.ifg.luziania.pw.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "log-auditoria")
public class LogAuditoria {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  String acao;
  String usuarioExecutor;
  LocalDateTime dataHora;

  public LogAuditoria() {
  }

  public LogAuditoria(String acao, String usuarioExecutor) {
    this.acao = acao;
    this.usuarioExecutor = usuarioExecutor;
    this.dataHora = LocalDateTime.now();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getAcao() {
    return acao;
  }

  public void setAcao(String acao) {
    this.acao = acao;
  }

  public String getUsuarioExecutor() {
    return usuarioExecutor;
  }

  public void setUsuarioExecutor(String usuarioExecutor) {
    this.usuarioExecutor = usuarioExecutor;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }
}
