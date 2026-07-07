// ==========================================
// CONFIGURAÇÃO E INICIALIZAÇÃO DO SISTEMA
// ==========================================

// Roda assim que a página termina de carregar completamente
window.addEventListener('load', () => {
  inicializarBotoesComprar();
  carregarCarrinho();
});

// ==========================================
// CONTROLE DE ADICIONAR PRODUTOS (PÁGINA DE PRODUTOS)
// ==========================================

function inicializarBotoesComprar() {
  let botoes = document.querySelectorAll('.btn-comprar');

  botoes.forEach(function (botao) {
    // Limpa cliques antigos para evitar bugs de repetição
    botao.onclick = null;

    botao.onclick = function () {
      let nome = botao.getAttribute('data-nome');
      let precoRaw = botao.getAttribute('data-preco');

      // Correção rápida para o preço do Drone (498000 -> 4980.00)
      if (nome.includes("Drone") && precoRaw === "498000") {
        precoRaw = "4980.00";
      }

      let preco = parseFloat(precoRaw);

      // Monta o objeto idêntico ao DTO esperado pelo Java
      let carrinhoDTO = {
        nome: nome,
        preco: preco
      };

      // Envia o produto para o banco de dados / memória do servidor
      fetch('/carrinho/adicionar', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(carrinhoDTO)
      })
        .then(function (response) {
          if (response.ok) {
            alert(nome + " adicionado ao carrinho!");
            // REQUISITO CHAVE: Recarrega o carrinho para puxar a nova lista do servidor
            // e atualizar o número do contador na mesma hora!
            carregarCarrinho();
          } else {
            alert("Falha ao adicionar produto ao servidor.");
          }
        })
        .catch(function (error) {
          console.error("Erro ao conectar com o backend:", error);
        });
    };
  });
}

// ==========================================
// RENDERIZAÇÃO E CONTADOR (PÁGINA DO CARRINHO / PRODUTOS)
// ==========================================

// Busca a lista atualizada do carrinho diretamente no back-end
function carregarCarrinho() {
  fetch('/carrinho/lista')
    .then(function (response) {
      if (!response.ok) throw new Error("Erro ao buscar itens do carrinho.");
      return response.json();
    })
    .then(function (itens) {
      // 1. Atualiza o número do contador que fica no menu superior
      atualizarContadorMenu(itens);

      // 2. Desenha a lista de produtos na tela (se a div do carrinho existir na página)
      renderizarCarrinhoNaTela(itens);
    })
    .catch(function (error) {
      console.error("Não foi possível carregar o carrinho:", error);
    });
}

// Atualiza o elemento "contador-carrinho" com base na resposta do Java
function atualizarContadorMenu(itens) {
  let contadorHTML = document.getElementById('contador-carrinho');
  if (contadorHTML) {
    // Soma a quantidade de todos os itens retornados pelo servidor
    let totalItens = itens.reduce((soma, item) => soma + (item.quantidade || 1), 0);
    contadorHTML.innerText = totalItens;
  }
}

// Desenha os itens na página de carrinho.html e calcula o valor total
function renderizarCarrinhoNaTela(itens) {
  let listaHTML = document.getElementById('lista-carrinho');
  let totalHTML = document.getElementById('total-carrinho');

  // Se o elemento não existir na página atual (ex: na página de produtos), para aqui.
  if (!listaHTML) return;

  let total = 0;
  listaHTML.innerHTML = "";

  if (itens.length === 0) {
    listaHTML.innerHTML = "<p style='text-align:center; padding:20px; color:#fff;'>Seu carrinho está vazio.</p>";
    if (totalHTML) totalHTML.innerText = "R$ 0,00";
  } else {
    itens.forEach(function (item) {
      let qtd = item.quantidade || 1;
      let subtotal = item.preco * qtd;
      total += subtotal;

      listaHTML.innerHTML += `
        <div class="item-carrinho" style="display:flex; justify-content:space-between; align-items:center; padding:15px; border-bottom:1px solid #29002e; color:#fff;">
            <div>
                <strong>${item.nome}</strong> (x${qtd})<br>
                <span style="color:#c600fe;">R$ ${subtotal.toFixed(2).replace('.', ',')}</span>
            </div>
            <button onclick="removerDoCarrinho('${item.nome}')" style="background:#ff4d4d; border:none; color:white; padding:5px 10px; border-radius:5px; cursor:pointer;">Remover</button>
        </div>`;
    });

    if (totalHTML) {
      totalHTML.innerText = "R$ " + total.toFixed(2).replace('.', ',');
    }
  }
}

// ==========================================
// AÇÕES DE EXCLUSÃO E FINALIZAÇÃO
// ==========================================

// Remove um item do carrinho enviando o nome para o endpoint do back-end
window.removerDoCarrinho = function(nome) {
  fetch('/carrinho/remover/' + encodeURIComponent(nome), {
    method: 'DELETE'
  })
    .then(function (response) {
      if (response.ok) {
        carregarCarrinho(); // Recarrega os dados do servidor para atualizar a tela
      } else {
        alert("Erro ao remover o item.");
      }
    })
    .catch(function (error) {
      console.error(error);
    });
};

// Finaliza a compra limpando o carrinho no servidor
function finalizarCompra() {
  fetch('/carrinho/finalizar', {
    method: 'POST'
  })
    .then(function (response) {
      if (response.ok) {
        alert("🚀 Pedido finalizado com total sucesso!");
        window.location.href = "/produto"; // Redireciona o usuário
      } else {
        alert("Não foi possível finalizar a compra. O carrinho está vazio?");
      }
    })
    .catch(function (error) {
      console.error(error);
    });
}

// Vincula o botão de finalizar compra se ele existir na tela
document.addEventListener('DOMContentLoaded', () => {
  const btnFinalizar = document.querySelector('.btn-finalizar');
  if (btnFinalizar) {
    btnFinalizar.onclick = finalizarCompra;
  }
});
