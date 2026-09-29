window.addEventListener("load", function () {
  carregarUsuarios();
});

// Carrega TODOS os usuários
function carregarUsuarios() {
  fetch("/cadastroadm/todos")
    .then(function (resposta) {
      if (!resposta.ok) throw new Error("erro");
      return resposta.json();
    })
    .then(function (usuarios) {
      var corpo = document.getElementById("tabela-admins-corpo");
      corpo.innerHTML = "";

      if (!usuarios || usuarios.length === 0) {
        corpo.innerHTML =
          '<tr><td colspan="5" style="text-align:center;color:#a694c7;">Nenhum usuário encontrado.</td></tr>';
        return;
      }

      usuarios.forEach(function (usuario) {
        var ehAdmin = usuario.perfil === "ADMIN";

        var badgeEstilo = ehAdmin
          ? 'background:#a100d5;padding:3px 8px;border-radius:4px;font-size:0.8rem;color:white;'
          : 'background:#6c757d;padding:3px 8px;border-radius:4px;font-size:0.8rem;color:white;';

        var botaoPerfil = ehAdmin
          ? '<button onclick="alternarPerfil(' + usuario.id + ', \'' + usuario.perfil + '\')" style="background:#ffc107;border:none;color:#000;padding:5px 10px;cursor:pointer;border-radius:4px;margin-right:5px;font-weight:bold;">Rebaixar a Cliente</button>'
          : '<button onclick="alternarPerfil(' + usuario.id + ', \'' + usuario.perfil + '\')" style="background:#28a745;border:none;color:white;padding:5px 10px;cursor:pointer;border-radius:4px;margin-right:5px;font-weight:bold;">Promover a Admin</button>';

        var linha = document.createElement("tr");
        linha.innerHTML =
          "<td>" + usuario.id + "</td>" +
          "<td>" + usuario.nome + "</td>" +
          "<td>" + usuario.email + "</td>" +
          '<td><span style="' + badgeEstilo + '">' + (usuario.perfil || 'CLIENTE') + '</span></td>' +
          "<td>" +
          botaoPerfil +
          '<button onclick="excluirUsuario(' + usuario.id + ')" style="background:#ff4d4d;border:none;color:white;padding:5px 10px;cursor:pointer;border-radius:4px;">Excluir</button>' +
          "</td>";

        corpo.appendChild(linha);
      });
    })
    .catch(function () {
      document.getElementById("tabela-admins-corpo").innerHTML =
        '<tr><td colspan="5" style="text-align:center;color:#ff4d4d;">Erro ao carregar usuários.</td></tr>';
    });
}

// Promove ou rebaixa o usuário
function alternarPerfil(idUsuario, perfilAtual) {
  var novoPerfil = perfilAtual === "ADMIN" ? "CLIENTE" : "ADMIN";
  var acaoTexto = novoPerfil === "ADMIN"
    ? "promover este usuário a Administrador?"
    : "rebaixar este Administrador a Cliente?";

  if (!confirm("Deseja realmente " + acaoTexto)) return;

  fetch("/cadastroadm/alterar-perfil/" + idUsuario, {
    method: "PUT",
    headers: { "Content-Type": "text/plain" },
    body: novoPerfil
  })
    .then(function (response) {
      if (response.ok) {
        alert("Perfil alterado com sucesso!");
        carregarUsuarios();
      } else {
        response.text().then(function (msg) {
          alert("Aviso: " + (msg || "Não foi possível alterar o perfil."));
        });
      }
    })
    .catch(function () {
      alert("Erro de conexão ao alterar o perfil.");
    });
}

// Exclui um usuário (Cliente ou Admin)
function excluirUsuario(id) {
  if (!confirm("Deseja realmente excluir este usuário?")) return;

  fetch("/cadastroadm/excluir/" + id, {
    method: "DELETE"
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Usuário excluído com sucesso!");
        carregarUsuarios();
      } else {
        resposta.text().then(function (msg) {
          alert("Aviso: " + (msg || "Não foi possível excluir o usuário."));
        });
      }
    })
    .catch(function () {
      alert("Erro de conexão ao excluir.");
    });
}

// Cadastra um novo Administrador
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
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(dados)
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Administrador cadastrado com sucesso!");
        document.getElementById("nome-adm").value = "";
        document.getElementById("email-adm").value = "";
        document.getElementById("senha-adm").value = "";
        carregarUsuarios();
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

function logout() {
  window.location.href = "/login";
}
