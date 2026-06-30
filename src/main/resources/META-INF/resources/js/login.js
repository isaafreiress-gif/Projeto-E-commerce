var AutenticacaoDTO = {
  email: "",
  senha: "",

}
//populando o DTO com dados do html
function autenticar() {
  AutenticacaoDTO.email = document.getElementById("email").value;
  AutenticacaoDTO.senha = document.getElementById("password").value;

  //definindo parametros da requisiçao POST para autenticaçao de usuario
  var url = 'http://localhost:8080/autenticar';
  var configRequest ={
      method: 'POST',
      headers: {
        'accept': 'application/json',
        'Content-type': 'application/json',
      },
    body: JSON.stringify(AutenticacaoDTO)
  };

  //executando requisição Post
  fetch(url, configRequest)
    .then(function (response) {
        if (response.ok) {
            console.log("Dados enviados com sucesso!")
        } else {
            console.log("Falha ao enviar dados!");
        }
    })
    .catch(function (error) {
        console.error(error);
})
}

// 1. FUNÇÃO PARA CADASTRAR UM NOVO USUÁRIO
function cadastrar() {
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email-cadastro').value;
    const senha = document.getElementById('senha-cadastro').value;
    const confirmarSenha = document.getElementById('senha-confirmar').value;

    if (nome === '' || email === '' || senha === '') {
        alert("Ops! Você esqueceu de preencher algum campo.");
        return;
    }

    if (senha !== confirmarSenha) {
        alert("As senhas não batem! Dá uma conferida aí.");
        return;
    }

    alert("Prontinho! Seu cadastro foi feito com sucesso.");
    window.location.href = "Login.html";
}

// =========================================================================
// 2. FUNÇÃO PARA VALIDAR A ENTRADA DE CLIENTE / ADM (Corrigida)
// =========================================================================
function fazerLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    if (email === '' || senha === '') {
        alert("Por favor, preencha todos os campos para entrar.");
        return;
    }

    alert(`Uhul! Login feito com sucesso. Seja bem-vindo(a)!`);
    window.location.href = "cadastroprodutos";

    // Lógica para verificar se é Admin ou Cliente comum baseada no e-mail digitado
    if (email.toLowerCase().includes("admin") || email.toLowerCase().includes("adm")) {
        window.location.href = "cadastroprodutos.html"; /* Se for admin, vai para o painel */
    } else {
        window.location.href = "carrinho.html"; /* Se for cliente, vai para o carrinho ou index */
    }
}

// =========================================================================
// 3. FUNÇÃO DE CADASTRO DE ADM
// =========================================================================
function registrarAdmSeguro() {
    const nome      = document.getElementById("adm-nome").value;
    const matricula = document.getElementById("adm-matricula").value;
    const senha     = document.getElementById("adm-senha").value;

    if (nome === "" || matricula === "" || senha === "") {
        alert("Preencha todos os campos!");
        return;
    }

    if (senha.length < 6) {
        alert("A senha precisa ter pelo menos 6 caracteres!");
        return;
    }

    alert("ADM cadastrado com sucesso!");
    window.location.href = "cadastroprodutos.html";
}