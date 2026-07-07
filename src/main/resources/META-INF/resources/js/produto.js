// ====================================================================
// CADASTRO DE PRODUTO
// ====================================================================

function cadastrarProduto() {
  let nome = document.getElementById("prod-nome").value.trim();
  let preco = document.getElementById("prod-preco").value.trim();
  let imagem = document.getElementById("prod-imagem").value.trim();
  let descricao = document.getElementById("prod-descricao").value.trim();

  if (nome === "" || preco === "" || imagem === "" || descricao === "") {
    alert("Preencha todos os campos!");
    return;
  }

  let produtoDTO = {
    nome: nome,
    preco: parseFloat(preco),
    imagem: imagem,
    descricao: descricao
  };

  fetch('/cadastroproduto/registrar', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'X-Usuario': localStorage.getItem('usuarioLogado') || ''
    },
    body: JSON.stringify(produtoDTO)
  })
    .then(function (response) {
      if (response.ok) {
        alert("Produto cadastrado com sucesso!");
        document.getElementById("form-produto").reset();
      } else {
        alert("Erro ao salvar o produto.");
      }
    })
    .catch(function (error) {
      console.error(error);
    });
}

// ====================================================================
// ESTOQUE: LISTAGEM, EXCLUSÃO E EDIÇÃO
// ====================================================================

document.addEventListener("DOMContentLoaded", function () {
  if (document.getElementById("tabela-estoque-corpo")) {
    carregarEstoque();
  }
});

function carregarEstoque() {
  fetch('/estoque/lista')
    .then(function (response) {
      return response.json();
    })
    .then(function (produtos) {
      let corpoTabela = document.getElementById("tabela-estoque-corpo");
      corpoTabela.innerHTML = "";

      produtos.forEach(function (prod) {
        corpoTabela.innerHTML +=
          "<tr>" +
          "<td>#" + prod.id + "</td>" +
          "<td><img src='" + prod.imagem + "' style='width: 40px; height: 40px; object-fit: contain;'></td>" +
          "<td>" + prod.nome + "</td>" +
          "<td>R$ " + prod.preco.toFixed(2).replace('.', ',') + "</td>" +
          "<td><span class='badge admin'>Ativo</span></td>" +
          "<td>" +
          "<button class='btn-acao' onclick='editarProduto(" + prod.id + ")' style='border-color: #4da6ff; color: #4da6ff; margin-right: 5px;'>✏️</button>" +
          "<button class='btn-acao' onclick='excluirProduto(" + prod.id + ")' style='border-color: #ff4d4d; color: #ff4d4d;'>🗑️</button>" +
          "</td>" +
          "</tr>";
      });
    })
    .catch(function (error) {
      console.error(error);
    });
}

function excluirProduto(id) {
  if (confirm("Tem certeza que deseja deletar este produto?")) {
    fetch('/estoque/' + id, {
      method: 'DELETE',
      headers: {
        'X-Usuario': localStorage.getItem('usuarioLogado') || ''
      }
    })
      .then(function(response) {
        if(response.ok) {
          alert("Produto removido!");
          carregarEstoque();
        }
      });
  }
}

function editarProduto(id) {
  let novoNome = prompt("Digite o novo nome do produto:");
  let novoPreco = prompt("Digite o novo preço:");

  if (novoNome !== null && novoPreco !== null) {
    let produtoAtualizado = {
      nome: novoNome,
      preco: parseFloat(novoPreco)
    };

    fetch('/estoque/' + id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'X-Usuario': localStorage.getItem('usuarioLogado') || ''
      },
      body: JSON.stringify(produtoAtualizado)
    })
      .then(function(response) {
        if (response.ok) {
          alert("Produto editado com sucesso!");
          carregarEstoque();
        } else {
          alert("Erro ao editar produto.");
        }
      })
      .catch(function(error) {
        console.error(error);
      });
  }
}

// ====================================================================
// LOGOUT
// ====================================================================

function logout() {
  localStorage.removeItem("usuarioLogado");
  localStorage.removeItem("perfilLogado");
  window.location.href = "login";
}
