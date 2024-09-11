function initCamera() {
	const zoomInput = document.getElementById("zoomInput");
	zoomInput.addEventListener("change", updateTileMapZoom);
}

function updateTileMapZoom() {
	if(zoomInput.value < 0.2) {
		zoomInput.value = 0.2;
	}
	const tileMap = document.getElementById("tile-map");
	const startTileWidth = 25;
	const gridWidth = Number.parseInt(tileMap.getAttribute("data-width"));
	const tileList = tileMap.children;
	
	const newTileWidth = startTileWidth * zoomInput.value;
	const newBackgroundSize = newTileWidth * 10;	
	
	for(let i = 0; i < tileList.length; i++) {
		const tileBrush = BrushData[tileList[i].getAttribute("data-tile-type")];
		const newBackgroundPosX = tileBrush.xPos * zoomInput.value;
		const newBackgroundPosY = tileBrush.yPos * zoomInput.value;
		
		
		
		tileList[i].style.width = newTileWidth + "px";
		tileList[i].style.height = newTileWidth + "px";
		tileList[i].style.backgroundSize = `${newBackgroundSize}px ${newBackgroundSize}px`;
		tileList[i].style.backgroundPosition = `-${newBackgroundPosX}px -${newBackgroundPosY}px`
	}
	tileMap.style.width = `${(newTileWidth + 2) * gridWidth}px`;
}

initCamera();