var tileWidth = 25;
var viewport = document.getElementById("viewport");
var tileMap = document.getElementById("tile-map");
var gridWidth = Number.parseInt(tileMap.getAttribute("data-width"));
var zoomScale = 2.0;

function updateTileMapZoom() {
	var tileList = tileMap.children;
	for(var i = 0; i < tileList.length; i++) {
		tileList[i].style.width = Math.round(tileWidth * zoomScale) + "px";
		tileList[i].style.height = Math.round(tileWidth * zoomScale) + "px";
	}
	tileMap.style.width = (Math.round((tileWidth * zoomScale) + 2) * gridWidth).toString() + "px";
}