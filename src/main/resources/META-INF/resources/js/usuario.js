// Dispara a busca assim que a página terminar de carregar no navegador
document.addEventListener("DOMContentLoaded", function () {
  carregarTabelaUsuarios();
});

function carregarTabelaUsuarios() {
  // Aponta para a nova rota JSON que criamos no Quarkus
  const url = 'http://localhost:8080/usuario/lista';

  fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error("Não foi possível buscar a lista de usuários.");
      }
      return response.json(); // Transforma a resposta em array de objetos
    })
    .then(usuarios => {
      const corpoTabela = document.getElementById("corpo-tabela");
      corpoTabela.innerHTML = ""; // Limpa qualquer resíduo do HTML

      // Se a lista estiver vazia, avisa o Admin
      if (usuarios.length === 0) {
        corpoTabela.innerHTML = `<tr><td colspan="5" style="text-align: center; color: #a694c7;">Nenhum usuário cadastrado.</td></tr>`;
        return;
      }

      // Mapeia e insere cada usuário retornado do Java na tabela HTML
      usuarios.forEach(usuario => {
        const linha = document.createElement("tr");

        linha.innerHTML = `
                    <td>${usuario.id}</td>
                    <td>${usuario.nome}</td>
                    <td>${usuario.email}</td>
                    <td><span class="tag-status" style="background: #a100d5; padding: 3px 8px; border-radius: 4px; font-size: 0.8rem;">Cliente</span></td>
                    <td>
                        <button class="btn-excluir" onclick="deletarUsuario(${usuario.id})" style="background: #ff4d4d; border: none; color: white; padding: 5px 10px; cursor: pointer; border-radius: 4px;">Excluir</button>
                    </td>
                `;

        corpoTabela.appendChild(linha);
      });
    })
    .catch(error => {
      console.error("Erro na requisição dos usuários:", error);
      document.getElementById("corpo-tabela").innerHTML =
        `<tr><td colspan="5" style="text-align: center; color: #ff4d4d;">Erro crítico ao carregar dados do servidor.</td></tr>`;
    });
}

// Função placeholder para o botão excluir não quebrar o código
function deletarUsuario(id) {
  if(confirm(`Deseja mesmo excluir o usuário com ID ${id}?`)) {
    alert("Funcionalidade de exclusão será integrada com o banco de dados logo mais!");
  }
}
