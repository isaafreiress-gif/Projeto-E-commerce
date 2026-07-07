window.addEventListener('load', () => {
  carregarStats();
});

function carregarStats() {
  fetch('/paginaadm/stats')
    .then(function (response) {
      return response.json();
    })
    .then(function (dados) {
      document.getElementById("vendas-valor").innerText =
        "R$ " + dados.vendasValor.toFixed(2).replace('.', ',');
      document.getElementById("vendas-qtd").innerText = dados.vendasQtd;
      document.getElementById("usuarios-qtd").innerText = dados.usuariosQtd;
    })
    .catch(function (error) {
      console.error(error);
    });
}

function atualizarDados() {
  carregarStats();
  alert("Dados atualizados com sucesso!");
}

function logout() {
  window.location.href = "/login";
}
