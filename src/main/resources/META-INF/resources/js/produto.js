function previewImagem() {
  var campoUrl = document.getElementById("prod-imagem");
  var imagem = document.getElementById("preview-imagem");
  var mensagem = document.getElementById("preview-msg");

  if (!campoUrl || !imagem) return;

  var url = campoUrl.value.trim();

  if (url === "") {
    imagem.style.display = "none";
    imagem.src = "";
    if (mensagem) mensagem.textContent = "";
    return;
  }

  imagem.src = url;
  imagem.style.display = "block";

  imagem.onerror = function () {
    imagem.style.display = "none";
    if (mensagem) {
      mensagem.textContent = "Não foi possível carregar a imagem. Verifique o link.";
    }
  };

  imagem.onload = function () {
    if (mensagem) mensagem.textContent = "";
  };
}

function cadastrarProduto() {
  var nome = document.getElementById("prod-nome").value.trim();
  var preco = document.getElementById("prod-preco").value.trim();
  var imagem = document.getElementById("prod-imagem").value.trim();
  var descricao = document.getElementById("prod-descricao").value.trim();

  if (nome === "" || preco === "" || imagem === "" || descricao === "") {
    alert("Preencha todos os campos!");
    return;
  }

  var dados = {
    nome: nome,
    preco: parseFloat(preco),
    imagem: imagem,
    descricao: descricao
  };

  fetch("/cadastroproduto/registrar", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(dados)
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Produto cadastrado com sucesso! Ele já aparece na loja.");
        document.getElementById("form-produto").reset();

        var preview = document.getElementById("preview-imagem");
        if (preview) {
          preview.style.display = "none";
          preview.src = "";
        }
      } else {
        alert("Erro ao salvar o produto.");
      }
    })
    .catch(function () {
      alert("Erro de conexão ao salvar o produto.");
    });
}

function carregarProdutosLoja() {
  var grid = document.getElementById("grid-produtos");
  if (!grid) return;

  fetch("/produto/lista")
    .then(function (resposta) {
      if (!resposta.ok) throw new Error("erro");
      return resposta.json();
    })
    .then(function (produtos) {
      grid.innerHTML = "";

      if (!produtos || produtos.length === 0) {
        grid.innerHTML =
          '<p style="color: #a694c7; text-align: center; width: 100%;">Nenhum produto cadastrado ainda.</p>';
        return;
      }

      produtos.forEach(function (prod) {
        var precoFormatado = Number(prod.preco).toFixed(2).replace(".", ",");
        var card = document.createElement("div");
        card.className = "card";

        card.innerHTML =
          '<img src="' + (prod.imagem || "") + '" alt="' + (prod.nome || "") + '">' +
          '<div class="card-info">' +
          "<h3>" + (prod.nome || "") + "</h3>" +
          "<p>R$ " + precoFormatado + "</p>" +
          '<button class="btn-comprar" data-nome="' + (prod.nome || "") + '" data-preco="' + prod.preco + '">Comprar</button>' +
          "</div>";

        grid.appendChild(card);
      });

      if (typeof inicializarBotoesComprar === "function") {
        inicializarBotoesComprar();
      }
    })
    .catch(function () {
      grid.innerHTML =
        '<p style="color: #ff4d4d; text-align: center; width: 100%;">Erro ao carregar produtos.</p>';
    });
}

function carregarEstoque() {
  var corpoTabela = document.getElementById("tabela-estoque-corpo");
  if (!corpoTabela) return;

  fetch("/estoque/lista")
    .then(function (resposta) {
      return resposta.json();
    })
    .then(function (produtos) {
      corpoTabela.innerHTML = "";

      if (!produtos || produtos.length === 0) {
        corpoTabela.innerHTML =
          '<tr><td colspan="6" style="text-align:center;color:#a694c7;">Nenhum produto no estoque.</td></tr>';
        return;
      }

      produtos.forEach(function (prod) {
        corpoTabela.innerHTML +=
          "<tr>" +
          "<td>#" + prod.id + "</td>" +
          "<td><img src='" + (prod.imagem || "") + "' style='width:40px;height:40px;object-fit:contain;'></td>" +
          "<td>" + prod.nome + "</td>" +
          "<td>R$ " + Number(prod.preco).toFixed(2).replace(".", ",") + "</td>" +
          "<td><span class='badge admin'>Ativo</span></td>" +
          "<td>" +
          "<button class='btn-acao' onclick='editarProduto(" + prod.id + ")' style='border-color:#4da6ff;color:#4da6ff;margin-right:5px;'>✏️</button>" +
          "<button class='btn-acao' onclick='excluirProduto(" + prod.id + ")' style='border-color:#ff4d4d;color:#ff4d4d;'>🗑️</button>" +
          "</td>" +
          "</tr>";
      });
    })
    .catch(function (erro) {
      console.error(erro);
    });
}

function excluirProduto(id) {
  if (!confirm("Tem certeza que deseja deletar este produto?")) return;

  fetch("/estoque/" + id, {
    method: "DELETE"
  }).then(function (resposta) {
    if (resposta.ok) {
      alert("Produto removido!");
      carregarEstoque();
    }
  });
}

function editarProduto(id) {
  var novoNome = prompt("Digite o novo nome do produto:");
  var novoPreco = prompt("Digite o novo preço:");

  if (novoNome === null || novoPreco === null) return;

  var dados = {
    nome: novoNome,
    preco: parseFloat(novoPreco)
  };

  fetch("/estoque/" + id, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(dados)
  })
    .then(function (resposta) {
      if (resposta.ok) {
        alert("Produto editado com sucesso!");
        carregarEstoque();
      } else {
        alert("Erro ao editar produto.");
      }
    })
    .catch(function (erro) {
      console.error(erro);
    });
}

document.addEventListener("DOMContentLoaded", function () {
  if (document.getElementById("tabela-estoque-corpo")) {
    carregarEstoque();
  }

  if (document.getElementById("grid-produtos")) {
    carregarProdutosLoja();
  }

  var campoImagem = document.getElementById("prod-imagem");
  if (campoImagem) {
    campoImagem.addEventListener("input", previewImagem);
  }
});

function logout() {
  window.location.href = "/login";
}
