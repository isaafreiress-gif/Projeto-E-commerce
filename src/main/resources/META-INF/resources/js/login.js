function autenticar() {
  let email = document.getElementById("email").value;
  let senha = document.getElementById("senha").value;

  if (email === "" || senha === "") {
    alert("Preencha todos os campos!");
    return;
  }

  let autenticacaoDTO = { email: email, senha: senha };

  fetch('/login/autenticacao', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(autenticacaoDTO)
  })
    .then(function (response) {
      if (response.ok) {
        return response.text();
      } else {
        throw new Error("unauthorized");
      }
    })
    .then(function (perfil) {
      alert("Seja bem-vindo(a)!");

      // O JWT já foi gravado pelo servidor em um cookie HttpOnly;
      // o perfil devolvido serve só para escolher a tela.
      if (perfil === "ADMIN") {
        window.location.href = "/paginaadm";
      } else {
        window.location.href = "/carrinho";
      }
    })
    .catch(function () {
      alert("Email ou senha incorretos!");
    });
}
