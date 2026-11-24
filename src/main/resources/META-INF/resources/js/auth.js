// O cookie do JWT é HttpOnly: só o servidor consegue apagá-lo
function logout() {
  fetch('/login/logout', { method: 'POST' })
    .finally(function () {
      window.location.href = "/login";
    });
}
