var tileMap = document.querySelector("#tile-map");

const Brushes = Object.freeze({
	HIGHLIGHT: "gray",
	RED: "red"
});

const Tools = Object.freeze({
	LINE: "Line",
	RECT: "Rectangle",
	CIRC: "Circle",
	FILLRECT: "Filled Rectangle",
	FILLCIRC: "Filled Circle"
});
var currentTool = Tools.FILLCIRC;
var currentBrush = Brushes.RED;

const highlightedTileData = new Map();

var MouseGridPoints = {
	x1: 0,
	y1: 0,
	x2: 0,
	y2: 0
};

var isHighlighting = false;
var isDrawing = false;

function clearMouseGridPoints() {
	MouseGridPoints.x1 = 0;
	MouseGridPoints.y1 = 0;
	MouseGridPoints.x2 = 0;
	MouseGridPoints.y2 = 0;
}

//EventListeners

function onMouseMovesOverGrid(event) {
	if(isDrawing) {
		clearHighlightedTiles();
		let relativeX = (event.clientX > Number.parseInt(tileMap.style.width)) ? Number.parseInt(tileMap.style.width) : event.clientX;
		let relativeY = (event.clientY > Number.parseInt(tileMap.style.height)) ? Number.parseInt(tileMap.style.height) : event.clientY;
		MouseGridPoints.x2 = Math.floor(relativeX / 27);
		MouseGridPoints.y2 = Math.ceil(relativeY / 27);
		drawUsingTool();
	}
}

function onTileMapClick(event) {
	isHighlighting = !isHighlighting;
	isDrawing = true;
	if(isHighlighting) {
		relativeX = (event.clientX > Number.parseInt(tileMap.style.width)) ? Number.parseInt(tileMap.style.width) : event.clientX;
		relativeY = (event.clientY > Number.parseInt(tileMap.style.height)) ? Number.parseInt(tileMap.style.height) : event.clientY;
		MouseGridPoints.x1 = Math.floor(relativeX / 27);
		MouseGridPoints.y1 = Math.ceil(relativeY / 27);
		paintTile(MouseGridPoints.x1, MouseGridPoints.y1);
	} else {
		onMouseMovesOverGrid(event);
		clearMouseGridPoints();
		isDrawing = false;
	}
}

tileMap.addEventListener("mousemove", onMouseMovesOverGrid);

//DRAW FUNCTIONS

function drawUsingTool() {
	switch(currentTool) {
		case Tools.LINE:
			drawLine(MouseGridPoints.x1, MouseGridPoints.y1, MouseGridPoints.x2, MouseGridPoints.y2);
			break;
		case Tools.RECT:
			drawRectangle(MouseGridPoints.x1, MouseGridPoints.y1, MouseGridPoints.x2, MouseGridPoints.y2);
			break;
		case Tools.CIRC: 
			drawCircle(MouseGridPoints.x1, MouseGridPoints.y1, MouseGridPoints.x2, MouseGridPoints.y2);
			break;
		case Tools.FILLRECT:
			drawFillRectangle(MouseGridPoints.x1, MouseGridPoints.y1, MouseGridPoints.x2, MouseGridPoints.y2);
			break;
		case Tools.FILLCIRC:
			drawFillCircle(MouseGridPoints.x1, MouseGridPoints.y1, MouseGridPoints.x2, MouseGridPoints.y2);
			break;
	}
}

function clearHighlightedTiles() {
	for(const [tile, bgColor] of highlightedTileData) {
		tile.style.backgroundColor = bgColor;
	}
	highlightedTileData.clear();
}

function paintTile(x, y) {
	let gridWidth = Number.parseInt(tileMap.getAttribute("data-width"));
	let tileIndex = ((y - 1) * gridWidth) + x;
	
	if(tileIndex < 0 
		|| tileIndex > tileMap.children.length
		|| x > gridWidth - 1
		|| x < 0) {
		return;
	}
	
	let tile = tileMap.children[tileIndex];
	if(isHighlighting) {
		if(!highlightedTileData.has(tile))
			highlightedTileData.set(tile, tile.style.backgroundColor);
		tile.style.backgroundColor = Brushes.HIGHLIGHT;
	} else {
		tile.style.backgroundColor = currentBrush;
	}
}

function drawLine(x1, y1, x2, y2) {
	if(x1 == x2) {
		let lowY = (y2 > y1) ? y2 : y1; //Lower on the grid (Higher value)
		let highY = (y2 > y1) ? y1 : y2; //Higher on the grid (Lower value)
		
		for(var i = highY; i <= lowY; i++) {
			paintTile(x1, i);
		}
		return;
	}
	var rightX = (x1 > x2) ? x1 : x2;
	var leftX = (x1 > x2) ? x2 : x1;
	var leftY = (leftX == x1) ? y1 : y2;
	var rightY = (leftX == x1) ? y2 : y1;
	var slope = (rightY - leftY) / (rightX - leftX);
	for(var i = leftX; i <= rightX; i++) {
		let yIntercept = leftY - (slope * leftX);
		let newY = Math.round(slope * i + yIntercept);
		paintTile(i, newY);
	}	
}

function drawRectangle(x1, y1, x2, y2) {
	drawLine(x1, y1, x2, y1);
	drawLine(x2, y1, x2, y2);
	drawLine(x1, y2, x2, y2);
	drawLine(x1, y1, x1, y2);
}

function drawCircle(x1, y1, x2, y2) {
	var r = Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	for(var i = x1 - r; i <= x1 + r; i++) {
		var yPos = Math.round(y1 + Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		var yNeg = Math.round(y1 - Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		if(yPos == yNeg) {
			paintTile(i, yPos);	
		} else {
			paintTile(i, yPos);
			paintTile(i, yNeg);
		}
	}
	for(var i = y1 - r; i <= y1 + r; i++) {
		var xPos = Math.round(x1 + Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		var xNeg = Math.round(x1 - Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		if(xPos == xNeg) {
			paintTile(xPos, i);	
		} else {
			paintTile(xPos, i);
			paintTile(xNeg, i);
		}
	}
}

function drawFillCircle(x1, y1, x2, y2) {
	var r = Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	for(var i = x1 - r; i <= x1 + r; i++) {
		var yPos = Math.round(y1 + Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		var yNeg = Math.round(y1 - Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		if(yPos == yNeg) {
			paintTile(i, yPos);	
		} else {
			drawLine(i, yPos, i, yNeg);
		}
	}
	for(var i = y1 - r; i <= y1 + r; i++) {
		var xPos = Math.round(x1 + Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		var xNeg = Math.round(x1 - Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		if(xPos == xNeg) {
			paintTile(xPos, i);	
		} else {
			drawLine(xPos, i, xNeg, i);
		}
	}
}

function drawFillRectangle(x1, y1, x2, y2) {
	var bigY = (y2 > y1) ? y2 : y1;
	var smallY = (y2 > y1) ? y1 : y2;
	for(var i = smallY; i <= bigY; i++) {
		drawLine(x1, i, x2, i);
	}
}
