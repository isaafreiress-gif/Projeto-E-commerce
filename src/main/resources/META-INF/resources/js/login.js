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
    });

// 1. FUNÇÃO PARA CADASTRAR UM NOVO USUÁRIO (Roda no cadastro de clientes)

function cadastrar() {
    // buscar o que o usuário digitou nos campos da tela
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email-cadastro').value;
    const senha = document.getElementById('senha-cadastro').value;
    const confirmarSenha = document.getElementById('senha-confirmar').value;

    // Confere se está tudo preenchido
    if (nome === '' || email === '' || senha === '') {
        alert("Ops! Você esqueceu de preencher algum campo.");
        return;
    }

    // se a senha tiver diferente, corrija
    if (senha !== confirmarSenha) {
        alert("As senhas não batem! Dá uma conferida aí.");
        return;
    }

    // Alerta de sucesso direto (sem salvar no localStorage)
    alert("Prontinho! Seu cadastro foi feito com sucesso.");

    // Manda o usuário para a página de login
    window.location.href = "login";
}

// =========================================================================
// 2. FUNÇÃO PARA VALIDAR A ENTRADA DE CLIENTE (Roda no Login.html)
// =========================================================================
function fazerLogin() {
  // 1. Corrigido para 'password' para bater com o ID do HTML
  const email = document.getElementById('email').value;
  const senha = document.getElementById('password').value;

  if (email === '' || senha === '') {
    alert("Por favor, preencha todos os campos para entrar.");
    return;
  }

  alert(`Uhul! Login feito com sucesso. Seja bem-vindo(a)!`);
    window.location.href = "produto"; /* Se for cliente, vai para a loja (ajuste a extensão se necessário) */
}
// =========================================================================
// 3. FUNÇÃO DE CADASTRO DE ADM
// =========================================================================
// Chame essa função no botão "Cadastrar" da tela de cadastro
function registrarAdmSeguro() {
  const matricula = document.getElementById("adm-matricula").value;
  const senha     = document.getElementById("adm-senha").value;

  if (matricula === "" || senha === "") {
    alert("Preencha todos os campos!");
    return;
  }

  if (senha.length < 6) {
    alert("A senha precisa ter pelo menos 6 caracteres!");
    return;
  }

  // Salva a matrícula e a senha no localStorage do navegador
  localStorage.setItem("adm_matricula", matricula);
  localStorage.setItem("adm_senha", senha);

  alert("Administrador cadastrado com sucesso!");

  // Depois de cadastrar, manda ele para a tela de login
  window.location.href = "loginadm";
}


// 3. FUNÇÃO DE login DE ADM

// Chame essa função no botão "Validar ADM" da tela de login
function logarAdm() {
  const matriculaDigitada = document.getElementById("adm-matricula").value;
  const senhaDigitada     = document.getElementById("adm-senha").value;

  // Busca o que foi salvo lá na tela de cadastro
  const matriculaSalva = localStorage.getItem("adm_matricula");
  const senhaSalva     = localStorage.getItem("adm_senha");

  if (matriculaDigitada === "" || senhaDigitada === "") {
    alert("Por favor, preencha todos os campos!");
    return;
  }

  // Verifica se os dados batem com o cadastro
  if (matriculaDigitada === matriculaSalva && senhaDigitada === senhaSalva) {
    alert("Login de ADM realizado com sucesso!");
    window.location.href = "paginaadm"; // Manda para o painel de produtos
  } else {
    alert("Matrícula ou senha incorretas! (Ou ADM não cadastrado)");
  }
}

// =========================================================================
// 4. FUNÇÃO DE LOGIN GERAL (Clientes e ADMs)
// =========================================================================
function logar() {
  const loginInformado = document.getElementById('email-login').value;
  const senhaInformada = document.getElementById('senha-login').value;

  if (!loginInformado || !senhaInformada) {
    alert("Preencha os campos de login e senha!");
    return;
  }

  alert("Login efetuado com sucesso!");

  // Regra simples de simulação: se digitar "admin" no campo, vai para a tela de produtos
  if (loginInformado.toLowerCase().includes("admin") || loginInformado.includes("adm")) {
    window.location.href = "cadastroprodutos.html";
  } else {
    window.location.href = "index";
  }
}}

