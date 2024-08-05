var tileMapDiv = document.querySelector("#tile-map");
var saveForm = document.querySelector("#save-map");

function initTileGrid() {
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

function onSaveTileMap() {
	var params = new URLSearchParams(document.location.search);
	console.log(document.location.search);

	var worldIdInput = document.createElement("input");
	worldIdInput.setAttribute("type", "hidden");
	worldIdInput.setAttribute("name", "worldId");
	worldIdInput.setAttribute("value", params.get("world-id"));
	console.log(params.get("world-id"))
	
	var tileMapIdInput = document.createElement("input");
	tileMapIdInput.setAttribute("type", "hidden");
	tileMapIdInput.setAttribute("name", "tileMapId");
	tileMapIdInput.setAttribute("value", parseInt(params.get("tile-map-id")));
	console.log(params.get("tile-map-id"))
	
	var mapWidthInput = document.createElement("input");
	mapWidthInput.setAttribute("type", "hidden");
	mapWidthInput.setAttribute("name", "mapWidth");
	mapWidthInput.setAttribute("value", "50");
		
	var tileData = getTileData();
	console.log(tileData);
	
	var	tileDataInput = document.createElement("input");
	tileDataInput.setAttribute("type", "hidden");
	tileDataInput.setAttribute("name", "tileData");
	tileDataInput.setAttribute("value", tileData.toString());
	
	saveForm.appendChild(worldIdInput);
	saveForm.appendChild(tileDataInput);
	saveForm.appendChild(tileMapIdInput);
	saveForm.appendChild(mapWidthInput);
}

saveForm.addEventListener("submit", onSaveTileMap);