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

      // Guarda quem logou, pra usar depois em outras telas
      localStorage.setItem("usuarioLogado", email);
      localStorage.setItem("perfilLogado", perfil);

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
