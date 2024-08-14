var tileMapDiv = document.querySelector("#tile-map");
var saveForm = document.querySelector("#save-map");

function initTileGrid() {
	var gridWidth = Number.parseInt(tileMapDiv.getAttribute("data-width"));
	tileMapDiv.style.width = (gridWidth * 27).toString() + "px";
	
	for(var i = 0; i < tileMapDiv.children.length; i++) {
		var tileType = Number.parseInt(tileMapDiv.children[i].getAttribute("data-tile-type"));
		if(tileType == 1) {
			tileMapDiv.children[i].style.backgroundColor = "red";
		}
	}
}

initTileGrid();

function truncatedTileData() {
	var truncTileData = new Array();
	var currentTileType = Number.parseInt(tileMapDiv.children[0].getAttribute("data-tile-type"));
	var sameTileCount = 1;
	var tileDataBuffer = new Array();
	
	truncTileData.push(currentTileType);
	
	for(var i = 1; i < tileMapDiv.children.length; i++) {
		var tileType = Number.parseInt(tileMapDiv.children[i].getAttribute("data-tile-type"));
		
		if(tileType == currentTileType) {
			sameTileCount++;
			tileDataBuffer.push(tileType);
		}
		else {
			if(sameTileCount > 1) {
				truncTileData.push(sameTileCount);
				truncTileData.push(tileType);
				tileDataBuffer = [];
			}
			else {
				tileDataBuffer.push(tileType);
				tileDataBuffer.forEach((tile) => {truncTileData.push(tile);});
				tileDataBuffer = [];
			}
			currentTileType = tileType;
			sameTileCount = 1;
		}
	}
	truncTileData.push(sameTileCount);
	return truncTileData;
}

function getTileData() {
	var tileData = new Array();
	
	tileData.push(truncatedTileData());
	return tileData;
}

function createFormInput(type, name, value) {
	var formInput = document.createElement("input");
	formInput.setAttribute("type", type);
	formInput.setAttribute("name", name);
	formInput.setAttribute("value", value);
	return formInput;
}

function onSaveTileMap() {
	var params = new URLSearchParams(document.location.search);
	console.log(document.location.search);

	var worldIdInput = createFormInput("hidden", "worldId", params.get("world-id"));
	var tileMapIdInput = createFormInput("hidden", "tileMapId", Number.parseInt(params.get("tile-map-id")));
	var mapWidthInput = createFormInput("hidden", "mapWidth", "50");
		
	var tileData = getTileData();
	
	var	tileDataInput = createFormInput("hidden", "tileData", tileData.toString());
	
	saveForm.appendChild(worldIdInput);
	saveForm.appendChild(tileDataInput);
	saveForm.appendChild(tileMapIdInput);
	saveForm.appendChild(mapWidthInput);
}

saveForm.addEventListener("submit", onSaveTileMap);