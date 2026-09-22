window.addEventListener("load", function () {
  carregarAdmins();
});

function carregarAdmins() {
  fetch("/cadastroadm/lista")
    .then(function (resposta) {
      if (!resposta.ok) throw new Error("erro");
      return resposta.json();
    })
    .then(function (admins) {
      var corpo = document.getElementById("tabela-admins-corpo");
      corpo.innerHTML = "";

      if (!admins || admins.length === 0) {
        corpo.innerHTML =
          '<tr><td colspan="5" style="text-align:center;color:#a694c7;">Nenhum administrador cadastrado.</td></tr>';
        return;
      }

      admins.forEach(function (admin) {
        var linha = document.createElement("tr");
        linha.innerHTML =
          "<td>" + admin.id + "</td>" +
          "<td>" + admin.nome + "</td>" +
          "<td>" + admin.email + "</td>" +
          '<td><span style="background:#a100d5;padding:3px 8px;border-radius:4px;font-size:0.8rem;">ADMIN</span></td>' +
          "<td>" +
          '<button onclick="excluirAdmin(' + admin.id + ')" style="background:#ff4d4d;border:none;color:white;padding:5px 10px;cursor:pointer;border-radius:4px;">Excluir</button>' +
          "</td>";
        corpo.appendChild(linha);
      });
    })
    .catch(function () {
      document.getElementById("tabela-admins-corpo").innerHTML =
        '<tr><td colspan="5" style="text-align:center;color:#ff4d4d;">Erro ao carregar administradores.</td></tr>';
    });
}

function cadastrarAdmin() {
  var nome = document.getElementById("nome-adm").value.trim();
  var email = document.getElementById("email-adm").value.trim();
  var senha = document.getElementById("senha-adm").value;

  if (!nome || !email || !senha) {
    alert("Preencha todos os campos!");
    return;
  }

  var dados = {
    nome: nome,
    email: email,
    senha: senha,
    perfilNome: "ADMIN"
  };

  fetch("/cadastroadm/registrar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(dados)
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Administrador cadastrado com sucesso!");
        document.getElementById("nome-adm").value = "";
        document.getElementById("email-adm").value = "";
        document.getElementById("senha-adm").value = "";
        carregarAdmins();
      } else if (resposta.status === 409) {
        alert("Esse e-mail já está cadastrado.");
      } else {
        alert("Falha ao cadastrar administrador.");
      }
    })
    .catch(function () {
      alert("Erro de conexão ao cadastrar.");
    });
}

function excluirAdmin(id) {
  if (!confirm("Deseja realmente excluir este administrador?")) return;

  fetch("/cadastroadm/excluir/" + id, {
    method: "DELETE"
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Administrador excluído com sucesso!");
        carregarAdmins();
      } else if (resposta.status === 409) {
        alert("Não é possível excluir o último administrador.");
      } else {
        alert("Falha ao excluir administrador.");
      }
    })
    .catch(function () {
      alert("Erro de conexão ao excluir.");
    });
}

function logout() {
  window.location.href = "/login";
}
