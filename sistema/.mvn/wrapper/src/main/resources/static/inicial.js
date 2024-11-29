
let currentZoom = 100;

document.getElementById("zoom-in").addEventListener("click", () => {
  if (currentZoom < 150) {
    currentZoom += 10;
    document.body.style.zoom = `${currentZoom}%`;
  }
});

document.getElementById("zoom-out").addEventListener("click", () => {
  if (currentZoom > 50) {
    currentZoom -= 10;
    document.body.style.zoom = `${currentZoom}%`;
  }
});
