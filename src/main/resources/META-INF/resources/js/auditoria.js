function carregarLogs() {
  var carregando = document.getElementById("carregando");
  var containerTabela = document.getElementById("container-tabela");
  var erro = document.getElementById("erro");

  carregando.classList.remove("d-none");
  containerTabela.classList.add("d-none");
  erro.classList.add("d-none");

  fetch('http://localhost:8080/auditoria/lista')
    .then(function (response) {
      if (!response.ok) throw new Error("Erro na rede");
      return response.json();
    })
    .then(function (data) {
      popularTabela(data);
      carregando.classList.add("d-none");
      containerTabela.classList.remove("d-none");
    })
    .catch(function (error) {
      console.error(error);
      carregando.classList.add("d-none");
      erro.classList.remove("d-none");
    });
}

function popularTabela(logs) {
  var corpo = document.getElementById("tabela-corpo");
  corpo.innerHTML = "";

  logs.forEach(function (log) {
    // Converte a data do Java para um formato legível
    var data = new Date(log.dataHora);
    var dataFormatada = data.toLocaleString();

    var linha = `<tr>
                        <td>${dataFormatada}</td>
                        <td>${log.usuarioExecutor}</td>
                        <td>${log.acao}</td>
                     </tr>`;
    corpo.innerHTML += linha;
  });
}

// Carrega ao abrir a página
window.onload = carregarLogs;
