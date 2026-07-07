function cadastrar() {
  let nome = document.getElementById("nome").value;
  let email = document.getElementById("email-cadastro").value;
  let senha = document.getElementById("senha-cadastro").value;

  if (nome === "" || email === "" || senha === "") {
    alert("Preencha todos os campos!");
    return;
  }

  let cadastroDTO = {
    nome: nome,
    email: email,
    senha: senha,
    perfilNome: "CLIENTE"
  };

  fetch('/cadastro/registrar', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(cadastroDTO)
  })
    .then(function (response) {
      if (response.ok) {
        alert("Cadastro realizado com sucesso!");
        window.location.href = "/login";
      } else if (response.status === 409) {
        alert("Esse e-mail já está cadastrado.");
      } else {
        alert("Falha ao cadastrar.");
      }
    })
    .catch(function (error) {
      console.error(error);
    });
}
