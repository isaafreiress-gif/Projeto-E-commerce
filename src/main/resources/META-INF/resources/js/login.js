

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
    window.location.href = "Login";
}

// =========================================================================
// 2. FUNÇÃO PARA VALIDAR A ENTRADA DE CLIENTE (Roda no Login.html)
// =========================================================================
function fazerLogin() {
    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    if (email === '' || senha === '') {
        alert("Por favor, preencha todos os campos para entrar.");
        return;
    }

    // Como não há banco de dados, simulamos o sucesso com qualquer dado preenchido
    alert(`Uhul! Login feito com sucesso. Seja bem-vindo(a)!`);
    window.location.href = "produto";
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
  window.location.href = "cadastroprodutos";
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
        window.location.href = "index.html";
    }
}




