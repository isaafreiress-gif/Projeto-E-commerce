package br.edu.ifg.luziania.pw.model.dto;

import java.time.LocalDateTime;

public class LogAuditoriaDTO {
  private Integer id;
  private String acao;
  private String usuarioExecutor;
  private LocalDateTime dataHora;

  public LogAuditoriaDTO(Integer id, String acao, String usuarioExecutor, LocalDateTime dataHora) {
    this.id = id;
    this.acao = acao;
    this.usuarioExecutor = usuarioExecutor;
    this.dataHora = dataHora;
  }

  public Integer getId() { return id; }
  public String getAcao() { return acao; }
  public String getUsuarioExecutor() { return usuarioExecutor; }
  public LocalDateTime getDataHora() { return dataHora; }
}
