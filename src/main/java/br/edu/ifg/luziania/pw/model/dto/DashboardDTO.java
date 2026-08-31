package br.edu.ifg.luziania.pw.model.dto;

public class DashboardDTO {
  private Double vendasValor;
  private Integer vendasQtd;
  private Integer usuariosQtd;

  public DashboardDTO() {
    this.vendasValor = 0.0;
    this.vendasQtd = 0;
    this.usuariosQtd = 0;
  }

  public DashboardDTO(Double vendasValor, Integer vendasQtd, Integer usuariosQtd) {
    this.vendasValor = vendasValor;
    this.vendasQtd = vendasQtd;
    this.usuariosQtd = usuariosQtd;
  }

  public Double getVendasValor() {
    return vendasValor;
  }

  public void setVendasValor(Double vendasValor) {
    this.vendasValor = vendasValor;
  }

  public Integer getVendasQtd() {
    return vendasQtd;
  }

  public void setVendasQtd(Integer vendasQtd) {
    this.vendasQtd = vendasQtd;
  }

  public Integer getUsuariosQtd() {
    return usuariosQtd;
  }

  public void setUsuariosQtd(Integer usuariosQtd) {
    this.usuariosQtd = usuariosQtd;
  }
}
