var items = document.querySelectorAll(".list .item");
var next = document.getElementById("next");
var prev = document.getElementById("prev");
var active = 0;
var countItems = items.length;

if (items.length > 0) {
  var refreshInterval = setInterval(function () {
    if (next) next.click();
  }, 5000);

  if (next) {
    next.onclick = function () {
      active = (active + 1) % countItems;
      showSlider();
    };
  }

  if (prev) {
    prev.onclick = function () {
      active = (active - 1 + countItems) % countItems;
      showSlider();
    };
  }

  function showSlider() {
    var oldActive = document.querySelector(".list .item.active");
    if (oldActive) oldActive.classList.remove("active");
    items[active].classList.add("active");

    clearInterval(refreshInterval);
    refreshInterval = setInterval(function () {
      if (next) next.click();
    }, 5000);
  }
}

