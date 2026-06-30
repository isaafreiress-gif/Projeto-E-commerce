
// CONFIGURAÇÃO DO SLIDER (HOME)
let items = document.querySelectorAll('.list .item');
let next = document.getElementById('next');
let prev = document.getElementById('prev');
let active = 0; // Indica qual banner está aparecendo agora 1 ou 2 ou 3 etc
let countItems = items.length;

if (items.length > 0) {
    let refreshInterval = setInterval(() => { if (next) next.click(); }, 5000); // Faz a vitrine rodar sozinha a cada 5 segundos

    if (next) {
        next.onclick = () => { // Avança um slide, e se for o último, volta pro começo
            active = (active + 1) % countItems;
            showSlider();
        };
    }

    if (prev) {
        prev.onclick = () => {  // Volta um slide, e se estiver no primeiro, vai pro último
            active = (active - 1 + countItems) % countItems;
            showSlider();
        };
    }

    // Esta função atualiza o visual ou seja apaga o banner antigo e acende o novo
    function showSlider() {
        let oldActive = document.querySelector('.list .item.active');
        if (oldActive) oldActive.classList.remove('active');
        items[active].classList.add('active');

        // Reseta o cronômetro de 5 segundos para a foto não mudar logo após o clique manual
        clearInterval(refreshInterval);
        refreshInterval = setInterval(() => { if (next) next.click(); }, 5000);
    }
}

// --- VERIFICAÇÃO DE ACESSO (CASO O USUÁRIO TENTE ENTRAR DIRETO NO CARRINHO) ---
(function verificarAutenticacao() {
    // Verifica se a página atual é o carrinho (pela classe no body ou nome do arquivo)
    if (window.location.pathname.includes("carrinho.html")) {
        const usuarioLogado = localStorage.getItem('logado');
        if (usuarioLogado == null) {
            alert("Para acessar o seu carrinho e finalizar compras, por favor, faça login.");
            window.location.href = "Login.html";
        }
    }
})();


// GESTÃO DO CARRINHO (LOCAL STORAGE)
// Busca o que já guardou no navegador. Se estiver vazio, cria uma nova.
function obterCarrinho() {
    return JSON.parse(localStorage.getItem('carrinhoZion')) || [];
}

// Salva a lista atualizada no navegador
function salvarCarrinho(carrinho) {
    localStorage.setItem('carrinhoZion', JSON.stringify(carrinho));
}

function atualizarContador() {
    const contadorHTML = document.getElementById('contador-carrinho');
    if (contadorHTML) {
        const carrinho = obterCarrinho();
        contadorHTML.innerText = carrinho.length; // Mostra a quantidade de itens
    }
}

// Lógica de clique nos botões "Comprar" da página de produtos
const botoesComprar = document.querySelectorAll('.btn-comprar');
botoesComprar.forEach(botao => {
    botao.onclick = () => {
        // Pega os detalhes do produto que estão "escondidos" no botão (data-attributes)
        const nome = botao.getAttribute('data-nome');
        const preco = parseFloat(botao.getAttribute('data-preco'));

        if (nome && !isNaN(preco)) {
            const carrinho = obterCarrinho();
            carrinho.push({ nome, preco }); // Adiciona o novo produto à lista
            salvarCarrinho(carrinho); // Salva a lista atualizada
            atualizarContador();        // Atualiza o número no menu
            alert(`${nome} adicionado ao carrinho!`);
        }
    };
});



//  RENDERIZAÇÃO DA PÁGINA DO CARRINHO

// Localiza onde a lista de produtos e o valor total devem aparecer na tela
const listaHTML = document.getElementById('lista-carrinho');
const totalHTML = document.getElementById('total-carrinho');


if (listaHTML) {
    renderizarCarrinho();
}

// Renderiza os itens do carrinho e calcula o valor total da transação.
function renderizarCarrinho() {
    const carrinho = obterCarrinho();   // Pega a lista de produtos salva
    let total = 0;   // Começamos a conta do total em zero

    // Caso o cliente não tenha escolhido nada ainda Verificação de integridade da lista
    if (carrinho.length === 0) {
        listaHTML.innerHTML = '<p style="text-align:center; padding:20px;">Seu carrinho está vazio.</p>';
        if (totalHTML) totalHTML.innerText = "R$ 0,00";
    // Se tem item, vamos percorrer a lista e transformar cada dado em um visual bonito
    } else {
        listaHTML.innerHTML = carrinho.map((item, index) => {
            total += item.preco;
            return `
                <div class="item-carrinho" style="display:flex; justify-content:space-between; padding:15px; border-bottom:1px solid #29002e;">
                    <div>
                        <strong>${item.nome}</strong><br>
                        <span style="color:#c600fe;">R$ ${item.preco.toFixed(2)}</span>
                    </div>
                    <button onclick="removerDoCarrinho(${index})" style="background:#ff4d4d; border:none; color:white; padding:5px 10px; border-radius:5px; cursor:pointer;">Remover</button>
                </div>`;//muda cor do botao ali na frente
        }).join('');
        // Aqui está o valor final que o cliente deve pagar."
        if (totalHTML) totalHTML.innerText = `R$ ${total.toFixed(2)}`;
    }
}

// Função para tirar um item da lista
window.removerDoCarrinho = function(index) {
    let carrinho = obterCarrinho();
    carrinho.splice(index, 1);  //Remove o item da posição X da lista
    salvarCarrinho(carrinho);
    renderizarCarrinho();
    atualizarContador();   // Atualiza
};

// Botão de Finalizar Compra

const btnFinalizar = document.querySelector('.btn-finalizar');

if (btnFinalizar) {
    btnFinalizar.onclick = () => {
        const carrinho = obterCarrinho();

        if (carrinho.length > 0) {
            alert("🚀 Pedido finalizado com sucesso!");
            localStorage.removeItem('carrinhoZion'); // Esvazia o carrinho após a compra
            window.location.href = "produto";
        } else {
            alert("Adicione itens ao carrinho primeiro!");
        }
    };
}




//  SISTEMA DE BUSCA (PESQUISA)

const botaoBusca = document.getElementById('btn-busca');
const inputBusca = document.getElementById('input-busca');

if (botaoBusca) {
    botaoBusca.onclick = () => {
        const termo = inputBusca.value.toLowerCase();  // O que o usuário quer achar
        const produtos = document.querySelectorAll('.card');

        produtos.forEach(produto => {
            const nome = produto.querySelector('h3').innerText.toLowerCase();
            // Se o nome do produto tem o que eu busquei, mostro. Se não, escondo.
            produto.style.display = nome.includes(termo) ? "block" : "none";
        });
    };
}

//  para deslogar o login existente, caso queira um outro login
function logout() {
    localStorage.removeItem('logado');
    window.location.href = "Login.html";
}

// Sempre que a página carregar, atualizamos o contador do menu
document.addEventListener('DOMContentLoaded', atualizarContador);




// FUNÇÃO PARA CADASTRAR PRODUTO (Envia para o Banco via API)
function cadastrarProduto() {
  // Pegando os valores dos inputs do HTML
  const nome = document.getElementById("prod-nome").value.trim();
  const preco = document.getElementById("prod-preco").value.trim();
  const imagem = document.getElementById("prod-imagem").value.trim();
  const descricao = document.getElementById("prod-descricao").value.trim();

  // Validação simples para não enviar campos vazios
  if (nome === "" || preco === "" || imagem === "" || descricao === "") {
    alert("🚨 Por favor, preencha todos os campos antes de salvar!");
    return;
  }

  // Montando o Objeto DTO correspondente ao Java
  const produtoDTO = {
    nome: nome,
    preco: parseFloat(preco), // Converte o texto para número decimal
    imagemUrl: imagem,
    descricao: descricao
  };

  // Configurando a requisição para o Quarkus
  const url = 'http://localhost:8080/produtos'; // Certifique-se de usar a sua rota correta do Java
  const configRequest = {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(produtoDTO)
  };

  // Executando a chamada HTTP
  fetch(url, configRequest)
    .then(response => {
      if (response.ok) {
        alert("✨ Produto cadastrado com total sucesso!");
        document.getElementById("form-produto").reset(); // Limpa os campos digitados
      } else {
        alert("❌ Erro no servidor ao tentar salvar o produto.");
      }
    })
    .catch(error => {
      console.error("Erro ao conectar na API:", error);
      alert("❌ Não foi possível conectar ao servidor. O backend está rodando?");
    });
}



// Executa assim que a página de estoque carregar
document.addEventListener("DOMContentLoaded", function() {
  if (document.getElementById("tabela-estoque-corpo")) {
    carregarEstoque();
  }
});

function carregarEstoque() {
  const url = 'http://localhost:8080/produtos'; // Sua rota GET do backend que lista os produtos

  fetch(url)
    .then(response => response.json())
    .then(produtos => {
      const corpoTabela = document.getElementById("tabela-estoque-corpo");
      corpoTabela.innerHTML = ""; // Limpa a tabela de exemplo antes de preencher

      produtos.forEach(prod => {
        corpoTabela.innerHTML += `
                    <tr>
                        <td>#${prod.id}</td>
                        <td><img src="${prod.imagemUrl}" alt="Imagem" style="width: 40px; height: 40px; object-fit: contain;"></td>
                        <td>${prod.nome}</td>
                        <td>R$ ${prod.preco.toFixed(2).replace('.', ',')}</td>
                        <td><span class="badge admin">Ativo</span></td>
                        <td>
                            <button class="btn-acao" onclick="editarProduto(${prod.id})">✏️</button>
                            <button class="btn-acao" onclick="excluirProduto(${prod.id})" style="border-color: #ff4d4d; color: #ff4d4d;">🗑️</button>
                        </td>
                    </tr>
                `;
      });
    })
    .catch(error => console.error("Erro ao carregar estoque:", error));
}

function excluirProduto(id) {
  if (confirm("Tem certeza que deseja deletar este produto?")) {
    fetch(`http://localhost:8080/produtos/${id}`, { method: 'DELETE' })
      .then(response => {
        if (response.ok) {
          alert("Produto removido!");
          carregarEstoque(); // Recarrega a tabela
        }
      });
  }
}
