/*
		DATA STRUCTURES
*/

class Tile {
	xpos;
	ypos;
	width;
	height;
	tileColor;
	highlightColor;
	tileDomId;
	
	constructor(xpos, ypos, width, height) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.width = width;
		this.height = height;
		this.tileColor = "#000000";
		
		var tileDiv = document.createElement("div");
		tileDiv.classList.add("tile");
		tileDiv.style.width = width.toString().concat("px");
		tileDiv.style.height = height.toString().concat("px");
			
		tileDiv.addEventListener("click", onTileClick);
		tileDiv.addEventListener("dblclick", clearTile);
		tileDiv.addEventListener("mouseenter", highlightTile);
		tileDiv.addEventListener("mouseleave", unhighlightTile);
		this.tileDomId = tileDiv;
	}
	
	setBackgroundColor(color) {
		this.tileColor = color;
		this.tileDomId.style.backgroundColor = color;
	}
	
	zoomUpdate(zoom) {
		this.width = 20 * zoom;
		this.height = 20 * zoom;
		
		this.tileDomId.style.width = this.width.toString().concat("px");
		this.tileDomId.style.height = this.height.toString().concat("px");
	}
	
	highlight() {
		this.tileDomId.style.opacity = "0.5";
	}
	
	highlight(color) {
		this.highlightColor = color;
		this.tileDomId.style.backgroundColor = this.highlightColor;
		this.tileDomId.style.opacity = "0.5";
	}
	
	unhighlight() {
		this.highlightColor = null;
		this.tileDomId.style.backgroundColor = this.tileColor;
		this.tileDomId.style.opacity = "1.0";
	}
}

class TileMap {
	static tileArr = new Array();
	static tileMap = document.getElementById("tile-map");
	static zoom;
	static backgroundColor = "#000000";
	static xDim = 100;
	static yDim = 50;
	static minX;
	static minY;
	
	static xPos = 0;
	static yPos = 0;
	static isPanning = false;
	
	#zoomInput;
	
	static init() {
		this.zoomInput = document.getElementById("zoom-input");
		this.zoomInput.addEventListener("change", onZoomChange);
		this.zoom = Number(this.zoomInput.value);
		
		this.minX = (window.innerWidth * .6) - (this.xDim * 20 * this.zoom);
		this.minY = (window.innerHeight * .8) - (this.yDim * 20 * this.zoom);
	}
	
	
	static addTile(tile) {
		this.tileArr.push(tile);
		this.tileMap.appendChild(tile.tileDomId);
	}
	
	static getTileObject(domElement) {
		for(var i = 0; i < this.tileArr.length; i++) {
			if(domElement == this.tileArr[i].tileDomId) {
				return this.tileArr[i];
			}
		}
	}
	
	static updateTileZoom(zoom) {
		this.zoom = zoom;
		for(var i = 0; i < TileMap.tileArr.length; i++) {
			this.tileArr[i].zoomUpdate(this.zoom);
		}
		this.minX = (window.innerWidth * .6) - (this.xDim * 20 * this.zoom);
		this.minY = (window.innerHeight * .8) - (this.yDim * 20 * this.zoom);
		this.pan(0, 0); 
	}
	
	static clearHighlights() {
		for(var i = 0; i < TileMap.tileArr.length; i++) {
			this.tileArr[i].unhighlight();
		}
	}
	
	static pan(dx, dy) {
		this.xPos += dx;
		this.yPos += dy;
		
		//Please for the love of christ fix this later
		
		if((this.xDim * 20 * this.zoom) >= window.innerWidth * .6 && (this.yDim * 20 * this.zoom) >= window.innerHeight * .8) {
			if(this.xPos <= 0 && this.xPos >= this.minX) {
				this.tileMap.style.left = this.xPos.toString().concat("px");
			} else {
				this.xPos = (this.xPos >= 0) ? 0 : this.minX;
				this.tileMap.style.left = this.xPos.toString().concat("px");
			}
			if(this.yPos <= 0 && this.yPos >= this.minY) {
				this.tileMap.style.top = this.yPos.toString().concat("px");
			} else {
				this.yPos = (this.yPos >= 0) ? 0 : this.minY;
				this.tileMap.style.top = this.yPos.toString().concat("px");
			}
		} else if((this.xDim * 20 * this.zoom) <= window.innerWidth * .6 && (this.yDim * 20 * this.zoom) >= window.innerHeight * .8) {
			if(this.yPos <= 0 && this.yPos >= this.minY) {
				this.tileMap.style.top = this.yPos.toString().concat("px");
			} else {
				this.yPos = (this.yPos >= 0) ? 0 : this.minY;
				this.tileMap.style.top = this.yPos.toString().concat("px");
			}
		} else if((this.yDim * 20 * this.zoom) <= window.innerHeight * .8 && (this.xDim * 20 * this.zoom) >= window.innerWidth * .6) {
			if(this.xPos <= 0 && this.xPos >= this.minX) {
				this.tileMap.style.left = this.xPos.toString().concat("px");
			} else {
				this.xPos = (this.xPos >= 0) ? 0 : this.minX;
				this.tileMap.style.left = this.xPos.toString().concat("px");
			}
		}
	}
}

class Tool {
	static toolType = "FILL";
	static currentColor = "#ff0000";
	static isErase = false;
	static selectedTile;
	static isHighlight = false;
	
	#toolInput;
	#colorInput;
	#eraseCheckbox;
	
	static init() {
		this.toolInput = document.getElementById("tool-select");
		this.colorInput = document.getElementById("color-input");
		this.eraseCheckbox = document.getElementById("erase-checkbox");
		this.toolInput.addEventListener("change", updateTool);
		this.colorInput.addEventListener("change", onColorChange);
		this.eraseCheckbox.addEventListener("change", updateEraseTool);
		
		this.colorInput.value = "#ff0000";
	}
	
	static useFillTool(x1, y1, x2, y2) {
		var selectedTiles = new Array();
		var maxY = (y1 > y2) ? y1 : y2;
		var maxX = (x1 > x2) ? x1 : x2;
		var minY = (y1 < y2) ? y1 : y2;
		var minX = (x1 < x2) ? x1 : x2;
		
		for(var y = minY; y <= maxY; y++) {
			for(var x = minX; x <= maxX; x++) {
				selectedTiles.push(TileMap.tileArr[x + (y * 100)]);
			}
		}
		return selectedTiles;
	}
	
		
	static useOutlineTool(x1, y1, x2, y2) {
		var selectedTiles = new Array();
		var maxY = (y1 > y2) ? y1 : y2;
		var maxX = (x1 > x2) ? x1 : x2;
		var minY = (y1 < y2) ? y1 : y2;
		var minX = (x1 < x2) ? x1 : x2
		
		for(var y = minY; y <= maxY; y++) {
			for(var x = minX; x <= maxX; x++) {
				selectedTiles.push(TileMap.tileArr[x + (y * 100)]);
				if(y != minY && y != maxY && x == minX) {
					x = maxX - 1;
				}
			}
		}
		return selectedTiles;
	}

	static useLineTool(x1, y1, x2, y2) {
		var selectedTiles = new Array();
		
		var maxY = (y1 > y2) ? y1 : y2;
		var maxX = (x1 > x2) ? x1 : x2;
		var minY = (y1 < y2) ? y1 : y2;
		var minX = (x1 < x2) ? x1 : x2;
		
		if(x1 == x2) {
			for(var y = minY; y <= maxY; y++) {
				selectedTiles.push(TileMap.tileArr[maxX + (y * 100)]);
			}
			return selectedTiles;
		}
		
		let slope = (y2 - y1) / (x2 - x1);
		let intercept = y1 - (slope * x1);
		
		if(Math.abs(slope) <= 1) {
			for(var x = minX; x <= maxX; x++) {
				let y = Math.round(slope * x + intercept);
				selectedTiles.push(TileMap.tileArr[x + (y * 100)]);
			}
		} else {
			for(var y = minY; y <= maxY; y++) {
				let x = Math.round((y - intercept) / slope);
				selectedTiles.push(TileMap.tileArr[x + (y * 100)]);
			}
		}
		return selectedTiles;
	}
	
	static selectTiles(lastTile, isPreview = false) {
		
		var selectedTiles;
		
		if(this.selectedTile != null){
			let x1 = this.selectedTile.xpos;
			let y1 = this.selectedTile.ypos;
			let x2 = lastTile.xpos;
			let y2 = lastTile.ypos;
			
			switch(this.toolType) {
				case "FILL":
					selectedTiles = Tool.useFillTool(x1, y1, x2, y2);
					break;
				case "OUTLINE":
					selectedTiles = Tool.useOutlineTool(x1, y1, x2, y2);
					break;
				case "LINE":
					selectedTiles = Tool.useLineTool(x1, y1, x2, y2);
					break;
				default:
					break;
			}
			if(!isPreview){
				this.selectedTile = null;
				this.isHighlight = false;
			}
			return selectedTiles;
		} else {
			if(this.toolType == "SINGLE") {
				return new Array(lastTile);
			} else {
				this.isHighlight = true;
				this.selectedTile = lastTile;
			}
			
			return null;
		}
	}
	
	static applyTool(tileList) {
		for(var i = 0; i < tileList.length; i++) {
			if(this.isErase) {
				tileList[i].setBackgroundColor(TileMap.backgroundColor);
			} else {
				tileList[i].setBackgroundColor(this.currentColor);
			}
		}
	}

}

/*
		EVENT FUNCTIONS
*/

function onDungeonWindowLoad(width, height) {
	TileMap.init();
	var gridTemplateColumnString = "";
	var tileWidth = 20 * TileMap.zoom;
	var tileHeight = 20 * TileMap.zoom;
	
	for(var i = 0; i < width; i++) {
		gridTemplateColumnString = gridTemplateColumnString.concat("auto ");
	}
	
	for(var y = 0; y < height; y++) {
		for(var x = 0; x < width; x++) {
			var tile = new Tile(x, y, tileWidth, tileHeight);
			TileMap.addTile(tile);
		}
	}
	TileMap.tileMap.style.gridTemplateColumns = gridTemplateColumnString;	
	Tool.init();
}

function updateEraseTool(event) {
	Tool.isErase = event.target.checked;
}

function onColorChange(event) {
	Tool.currentColor = event.target.value;
}

function updateTool(event) {
	Tool.toolType = event.target.value;
}

function highlightTile(event) {
	var tile = TileMap.getTileObject(event.target);
	
	if(!Tool.isHighlight) {
		if(Tool.isErase) {
			tile.highlight(TileMap.backgroundColor);
		} else {
			tile.highlight();
		}
	} else {
		var tileList = Tool.selectTiles(tile, true);
		for(var i = 0; i < tileList.length; i++) {
			if(Tool.isErase) {
				tileList[i].highlight(TileMap.backgroundColor);
			} else {
				tileList[i].highlight(Tool.currentColor);
			}
		}
	}
}

function unhighlightTile(event) {
	TileMap.clearHighlights();
	var tile = TileMap.getTileObject(event.target);
	tile.unhighlight();
}


function clearTile(event) {
	var tile = TileMap.getTileObject(event.target);
	tile.setBackgroundColor(TileMap.backgroundColor);
}

function onTileClick(event) {
	TileMap.clearHighlights();
	var currentTile = TileMap.getTileObject(event.target);
	
	var tileList = Tool.selectTiles(currentTile);
	if(tileList != null) {
		Tool.applyTool(tileList);
	}
} 

function onZoomChange(event) {
	TileMap.updateTileZoom(Number(event.target.value));
}

function onTileMapClick(event) {
	event.preventDefault();
	if(event.button == 1) {
		TileMap.isPanning = true;
	}
}

function onTileMapMouseMove(event) {
	if(TileMap.isPanning) {		
		TileMap.pan(event.movementX, event.movementY);
	}
}


function onTileMapMouseUp() {
	TileMap.isPanning = false;
}

/*
		GLOBAL CODE
*/

window.addEventListener("load", function() { onDungeonWindowLoad(100, 50); });
window.addEventListener("resize", onZoomChange);
var dungeonWindow = document.getElementById("dungeon-window");


dungeonWindow.addEventListener("mousedown", onTileMapClick);
dungeonWindow.addEventListener("mouseup", onTileMapMouseUp);
dungeonWindow.addEventListener("mousemove", onTileMapMouseMove);