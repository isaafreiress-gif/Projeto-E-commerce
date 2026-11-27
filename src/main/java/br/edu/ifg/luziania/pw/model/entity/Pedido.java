package br.edu.ifg.luziania.pw.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  Double valorTotal;
  Integer quantidadeItens;
  LocalDateTime dataHora;
  String emailUsuario;

  public Pedido() {
  }

  public Pedido(Double valorTotal, Integer quantidadeItens, String emailUsuario) {
    this.valorTotal = valorTotal;
    this.quantidadeItens = quantidadeItens;
    this.emailUsuario = emailUsuario;
    this.dataHora = LocalDateTime.now().withNano(0);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(Double valorTotal) {
    this.valorTotal = valorTotal;
  }

  public Integer getQuantidadeItens() {
    return quantidadeItens;
  }

  public void setQuantidadeItens(Integer quantidadeItens) {
    this.quantidadeItens = quantidadeItens;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public String getEmailUsuario() {
    return emailUsuario;
  }

  public void setEmailUsuario(String emailUsuario) {
    this.emailUsuario = emailUsuario;
  }
}
