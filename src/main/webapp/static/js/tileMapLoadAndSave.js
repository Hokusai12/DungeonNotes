function initialize() {
	const tileMapDiv = document.getElementById("tile-map");
	const gridWidthInput = document.getElementById("gridWidthInput");
	const gridHeightInput = document.getElementById("gridHeightInput");
	
	const saveForm = document.querySelector("#save-map");
	saveForm.addEventListener("submit", onSaveTileMap);
	
	const gridWidth = Number.parseInt(tileMapDiv.getAttribute("data-width"));
	tileMapDiv.style.width = (gridWidth * 27).toString() + "px";
	
	gridWidthInput.addEventListener("change", onGridWidthChange);
	gridHeightInput.addEventListener("change", onGridHeightChange);
	
	gridWidthInput.value = gridWidth;
	gridHeightInput.value = tileMapDiv.children.length / gridWidth;
}

function createTile() {
	const tile = document.createElement("div");
	tile.classList.add("tile");
	tile.style.backgroundColor = "red";
	tile.setAttribute("data-tile-type", 0);
	return tile;
}

function onGridWidthChange(e) {
	const newGridWidth = Number.parseInt(e.currentTarget.value);
	const oldGridWidth = Number.parseInt(document.getElementById("tile-map").getAttribute("data-width"));
	
	if(newGridWidth > oldGridWidth) {
		onGridWidthGrow();
	} else {
		onGridWidthShrink();
	}
}

function onGridHeightChange(e) {
	const tileMap = document.getElementById("tile-map");
	const newGridHeight = Number.parseInt(e.currentTarget.value);
	const oldGridHeight = tileMap.children.length / Number.parseInt(tileMap.getAttribute("data-width"));

	if(newGridHeight > oldGridHeight) {
		onGridHeightGrow();
	} else {
		onGridHeightShrink();
	}
}

function onGridWidthShrink() {
	const tileMap = document.getElementById("tile-map");
	const newGridWidth = Number.parseInt(document.getElementById("gridWidthInput").value);
	const oldGridWidth = Number.parseInt(tileMap.getAttribute("data-width"));
	
	const tilesToDelete = new Array();
	
	//Scan for any of the client's work in the area to be deleted
	for(let i = newGridWidth; i < tileMap.children.length; i++) {
		if(i % oldGridWidth == 0) {
			i += newGridWidth;
		}
		const tileType = tileMap.children[i].getAttribute("data-tile-type");
		if(tileType != 0) {
			const confirmDelete = confirm("You are about to delete some of your work. Are you sure you want to do this?");
			if(confirmDelete) {
				break;
			} else {
				document.getElementById("gridWidthInput").value = oldGridWidth;
				return;
			}
		}
	}
	
	//Actually delete the grid tiles
	for(let i = newGridWidth; i < tileMap.children.length; i++){
		if(i % oldGridWidth == 0) {
			i += newGridWidth;
		}
		tilesToDelete.push(tileMap.children[i]);
	}
	
	for(const tile of tilesToDelete) {
		tileMap.removeChild(tile);
	}
	
	tileMap.setAttribute("data-width", newGridWidth);
	updateTileMapZoom();
}

function onGridHeightShrink() {
	const tileMapDiv = document.getElementById("tile-map");
	const gridWidth = Number.parseInt(document.getElementById("gridWidthInput").value)
	const newGridHeight = Number.parseInt(document.getElementById("gridHeightInput").value);
	
	for(let i = tileMapDiv.children.length - 1; i >= newGridHeight * gridWidth; i--) {
		const tile = tileMapDiv.children[i];
		tileMapDiv.removeChild(tile);
	}
}

function onGridWidthGrow() {
	const tileMapDiv = document.getElementById("tile-map");
	const newGridWidth = Number.parseInt(document.getElementById("gridWidthInput").value);
	const oldGridWidth = Number.parseInt(tileMapDiv.getAttribute("data-width"));
	const widthDiff = newGridWidth - oldGridWidth;
	for(let i = oldGridWidth; i < tileMapDiv.children.length; i += widthDiff + oldGridWidth) {
		for(let j = 0; j < widthDiff; j++) {
			const tile = createTile();
			tileMapDiv.insertBefore(tile, tileMapDiv.children[i]);
		}
	}	
	for(let j = 0; j < widthDiff; j++) {
		const tile = createTile();
		tileMapDiv.appendChild(tile);
	}
	tileMapDiv.setAttribute("data-width", newGridWidth);
	updateTileMapZoom();
}

function onGridHeightGrow() {
	const tileMapDiv = document.getElementById("tile-map");
	const gridWidth = Number.parseInt(document.getElementById("gridWidthInput").value);
	const oldGridHeight = tileMapDiv.children.length / gridWidth;
	const newGridHeight = Number.parseInt(document.getElementById("gridHeightInput").value);
	
	for(let j = 0; j < newGridHeight - oldGridHeight; j++) {
		for(let i = 0; i < gridWidth; i++) {
			const tile = createTile();
			tileMapDiv.appendChild(tile);
		}
	}
	updateTileMapZoom();
}

function truncatedTileData() {
	const tileMapDiv = document.getElementById("tile-map");
	const truncTileData = new Array();
	let currentTileType = Number.parseInt(tileMapDiv.children[0].getAttribute("data-tile-type"));
	let sameTileCount = 1;
	
	truncTileData.push(currentTileType);
	
	for(let i = 1; i < tileMapDiv.children.length; i++) {
		const tileType = Number.parseInt(tileMapDiv.children[i].getAttribute("data-tile-type"));
		
		if(tileType == currentTileType) {
			sameTileCount++;
		}
		else {
			truncTileData.push(sameTileCount);
			truncTileData.push(tileType);
			currentTileType = tileType;
			sameTileCount = 1;
		}
	}
	truncTileData.push(sameTileCount);
	return truncTileData;
}

function getTileData() {
	const tileData = new Array();
	
	tileData.push(truncatedTileData());
	return tileData;
}

function createFormInput(type, name, value) {
	const formInput = document.createElement("input");
	formInput.setAttribute("type", type);
	formInput.setAttribute("name", name);
	formInput.setAttribute("value", value);
	return formInput;
}

function onSaveTileMap() {
	const tileMapDiv = document.getElementById("tile-map");
	const saveForm = document.querySelector("#save-map");
	const params = new URLSearchParams(document.location.search);
	const gridWidth = Number.parseInt(tileMapDiv.getAttribute("data-width"));

	const worldIdInput = createFormInput("hidden", "worldId", params.get("world-id"));
	const tileMapIdInput = createFormInput("hidden", "tileMapId", Number.parseInt(params.get("tile-map-id")));
	const mapWidthInput = createFormInput("hidden", "mapWidth", gridWidth);
		
	const tileData = getTileData();
	
	const tileDataInput = createFormInput("hidden", "tileData", tileData.toString());
	
	saveForm.appendChild(worldIdInput);
	saveForm.appendChild(tileDataInput);
	saveForm.appendChild(tileMapIdInput);
	saveForm.appendChild(mapWidthInput);
}

initialize();