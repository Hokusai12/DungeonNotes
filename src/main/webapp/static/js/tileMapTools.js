const BrushData = new Array();

const Tools = Object.freeze({
	SINGLE: "Single",
	LINE: "Line",
	RECT: "Rectangle",
	CIRC: "Circle",
	FILLRECT: "Filled Rectangle",
	FILLCIRC: "Filled Circle"
});

const DrawData = {
	currentBrush: 0,
	currentTool: Tools.FILLRECT,
	highlightedTileData: new Map(),
	MouseGridPoints: {
			x1: 0,
			y1: 0,
			x2: 0,
			y2: 0
		},
	isHighlighting: false,
	isDrawing: false
}

function initializeToolsAndBrushes() {	
	const toolButtonDiv = document.getElementById("tool-button-div");
	const tileMap = document.getElementById("tile-map");
	const brushSelectDiv = document.getElementById("brush-select-div");
	const brushesHttpRequest = new XMLHttpRequest();
	
	brushesHttpRequest.responseType = "json";
	brushesHttpRequest.open("GET", "/js/brushes.json");
	brushesHttpRequest.send();
	
	brushesHttpRequest.onload = () => {
		const jsonResponse = brushesHttpRequest.response;
		for(const brush of jsonResponse.brushes) {
			BrushData.push(brush);
			if(brush.index == 2) {
				DrawData.currentBrush = brush;
			}
		}
		initTileTextures();
		initBrushSelect();
	};
	
	for(let i = 0; i < toolButtonDiv.children.length; i++) {
		toolButtonDiv.children[i].addEventListener("click", updateToolSelection);
	}
	
	tileMap.addEventListener("mousemove", onMouseMovesOverGrid);
}


function getTileWidthWithMargin() {
	const tile = document.querySelectorAll("div.tile").item(0);
	return tile.clientWidth + 2;
}

function createBrushThumbnail(brush) {
	const brushThumbnail = document.createElement("span");
	brushThumbnail.id = brush.index;
	brushThumbnail.classList.add("tile");
	brushThumbnail.style.backgroundPosition = `-${brush.xPos}px -${brush.yPos}px`;
	brushThumbnail.addEventListener("click", () => {onBrushUpdate(event)});
	if(brush.index == 2) {
		brushThumbnail.classList.add("box-shadow");
	}
	return brushThumbnail;
}

function initBrushSelect() {
	const brushDiv = document.getElementById("brush-select-div");
	for(const brush of BrushData) {
		if(brush.index == 1) {
			continue;
		}
		
		let brushCategoryNode = null;
		for(let i = 0; i < brushDiv.children.length; i++) {
			if(brush.category == brushDiv.children[i].id) {
				brushCategoryNode = brushDiv.children[i];
			}
		}
		const brushThumbnail = createBrushThumbnail(brush);
		brushCategoryNode.appendChild(brushThumbnail);
	}
}

function onBrushUpdate(event) {
	const selectedBrushThumbnail = event.currentTarget;
	const currentBrushSpan = document.getElementById(`${DrawData.currentBrush.index}`);
	for(const brush of BrushData) {
		if(selectedBrushThumbnail.id == brush.index) {
			DrawData.currentBrush = brush;
		}
	}
	currentBrushSpan.classList.remove("box-shadow");
	selectedBrushThumbnail.classList.add("box-shadow");
}

function initTileTextures() {
	const tileMap = document.getElementById("tile-map");
	for(const tile of tileMap.children) {
		const brushIndex = Number.parseInt(tile.getAttribute("data-tile-type"));
		useBrush(tile, BrushData[brushIndex]);
	}
}

function clearMouseGridPoints() {
	DrawData.MouseGridPoints.x1 = 0;
	DrawData.MouseGridPoints.y1 = 0;
	DrawData.MouseGridPoints.x2 = 0;
	DrawData.MouseGridPoints.y2 = 0;
}

function getCurrentMouseGridPoint(mouseEvent) { //Returns an array of two position values (x, y)
	const tileMap = document.getElementById("tile-map");
	const mouseXTileMap = mouseEvent.clientX + viewport.scrollLeft;
	const mouseYTileMap = mouseEvent.clientY + viewport.scrollTop;
	const tileMapWidth = Number.parseInt(tileMap.style.width);
	const tileMapHeight = Number.parseInt(tileMap.style.height);
	
	if(mouseXTileMap > tileMapWidth || mouseYTileMap > tileMapHeight) {
		return [0, 0];
	}
	
	const mouseXGrid = Math.floor(mouseXTileMap / getTileWidthWithMargin());
	const mouseYGrid = Math.ceil(mouseYTileMap / getTileWidthWithMargin());
	return [mouseXGrid, mouseYGrid];
}

//EventListeners

function updateToolSelection(event) {
	const selectedButton = event.currentTarget;
	const oldSelectedButton = document.querySelectorAll("button.selected")[0];
	oldSelectedButton.classList.remove("selected");
	selectedButton.classList.add("selected");
	
	switch(selectedButton.id) {
		case "circle-button":
			DrawData.currentTool = Tools.CIRC;
			break;
		case "filled-circle-button":
			DrawData.currentTool = Tools.FILLCIRC;
			break;
		case "rectangle-button":
			DrawData.currentTool = Tools.RECT;
			break;
		case "filled-rectangle-button":
			DrawData.currentTool = Tools.FILLRECT;
			break;
		case "single-button":
			DrawData.currentTool = Tools.SINGLE;
			break;
		case "line-button":
			DrawData.currentTool = Tools.LINE;
			break;
		default:
			DrawData.currentTool = Tools.LINE;
			break;
	}
}

function onMouseMovesOverGrid(event) {
	if(DrawData.isDrawing) {
		clearHighlightedTiles();
		const mouseGridPoint = getCurrentMouseGridPoint(event);
		DrawData.MouseGridPoints.x2 = mouseGridPoint[0];
		DrawData.MouseGridPoints.y2 = mouseGridPoint[1];
		drawUsingTool();
	}
}

function onTileMapClick(event) {
	DrawData.isHighlighting = !DrawData.isHighlighting;
	DrawData.isDrawing = true;
	if(DrawData.isHighlighting && DrawData.currentTool != Tools.SINGLE) {
		let mouseGridPoint = getCurrentMouseGridPoint(event);
		DrawData.MouseGridPoints.x1 = mouseGridPoint[0];
		DrawData.MouseGridPoints.y1 = mouseGridPoint[1];
		paintTile(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1);
	} else if(DrawData.currentTool == Tools.SINGLE) {
		DrawData.isHighlighting = false;
		DrawData.isDrawing = false;
		let mouseGridPoint = getCurrentMouseGridPoint(event);
		DrawData.MouseGridPoints.x1 = mouseGridPoint[0];
		DrawData.MouseGridPoints.y1 = mouseGridPoint[1];
		paintTile(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1);
	}else {
		onMouseMovesOverGrid(event);
		clearMouseGridPoints();
		DrawData.isDrawing = false;
	}
}


//DRAW FUNCTIONS

function drawUsingTool() {
	switch(DrawData.currentTool) {
		case Tools.SINGLE:
			paintTile(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1);
			break;
		case Tools.LINE:
			drawLine(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1, DrawData.MouseGridPoints.x2, DrawData.MouseGridPoints.y2);
			break;
		case Tools.RECT:
			drawRectangle(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1, DrawData.MouseGridPoints.x2, DrawData.MouseGridPoints.y2);
			break;
		case Tools.CIRC: 
			drawCircle(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1, DrawData.MouseGridPoints.x2, DrawData.MouseGridPoints.y2);
			break;
		case Tools.FILLRECT:
			drawFillRectangle(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1, DrawData.MouseGridPoints.x2, DrawData.MouseGridPoints.y2);
			break;
		case Tools.FILLCIRC:
			drawFillCircle(DrawData.MouseGridPoints.x1, DrawData.MouseGridPoints.y1, DrawData.MouseGridPoints.x2, DrawData.MouseGridPoints.y2);
			break;
	}
}

function useBrush(tile, brush) {
	const zoomScale = document.getElementById("zoomInput").value;
	tile.style.backgroundPosition = `-${brush.xPos * zoomScale}px -${brush.yPos * zoomScale}px`;
	tile.setAttribute("data-tile-type", brush.index);
}

function clearHighlightedTiles() {
	for(const [tile, tileType] of DrawData.highlightedTileData) {
		useBrush(tile, BrushData[tileType]);
	}
	DrawData.highlightedTileData.clear();
}

function paintTile(x, y) {
	const tileMap = document.getElementById("tile-map");
	const gridWidth = Number.parseInt(tileMap.getAttribute("data-width"));
	const tileIndex = ((y - 1) * gridWidth) + x;
	
	if(tileIndex < 0 
		|| tileIndex > tileMap.children.length
		|| x > gridWidth - 1
		|| x < 0
		|| y < 0
		|| y > (tileMap.children.length / gridWidth)) {
		return;
	}
	
	const tile = tileMap.children[tileIndex];
	if(DrawData.isHighlighting) {
		if(!DrawData.highlightedTileData.has(tile)) {
			DrawData.highlightedTileData.set(tile, tile.getAttribute("data-tile-type"));
		}
		useBrush(tile, BrushData[1]);
	} else {
		useBrush(tile, DrawData.currentBrush);
	}
}

function drawLine(x1, y1, x2, y2) {		
	const lowY = (y2 > y1) ? y2 : y1; //Lower on the grid (Higher value)
	const highY = (y2 > y1) ? y1 : y2; //Higher on the grid (Lower value)
	if(x1 == x2) {
		for(let i = highY; i <= lowY; i++) {
			paintTile(x1, i);
		}
		return;
	}
	const rightX = (x1 > x2) ? x1 : x2;
	const leftX = (x1 > x2) ? x2 : x1;
	const leftY = (leftX == x1) ? y1 : y2;
	const rightY = (leftX == x1) ? y2 : y1;
	const slope = (rightY - leftY) / (rightX - leftX);
	const yIntercept = leftY - (slope * leftX);
	for(let i = leftX; i <= rightX; i++) {
		const newY = Math.round(slope * i + yIntercept);
		paintTile(i, newY);
	}	
	if(slope != 0) {
		for(let i = highY; i <= lowY; i++) {
			const newX = Math.round((i - yIntercept) / slope);
			paintTile(newX, i);
		}
	}
}

function drawRectangle(x1, y1, x2, y2) {
	drawLine(x1, y1, x2, y1);
	drawLine(x2, y1, x2, y2);
	drawLine(x1, y2, x2, y2);
	drawLine(x1, y1, x1, y2);
}

function drawCircle(x1, y1, x2, y2) {
	const r = Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	for(let i = x1 - r; i <= x1 + r; i++) {
		const yPos = Math.round(y1 + Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		const yNeg = Math.round(y1 - Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		if(yPos == yNeg) {
			paintTile(i, yPos);	
		} else {
			paintTile(i, yPos);
			paintTile(i, yNeg);
		}
	}
	for(let i = y1 - r; i <= y1 + r; i++) {
		const xPos = Math.round(x1 + Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		const xNeg = Math.round(x1 - Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		if(xPos == xNeg) {
			paintTile(xPos, i);	
		} else {
			paintTile(xPos, i);
			paintTile(xNeg, i);
		}
	}
}

function drawFillCircle(x1, y1, x2, y2) {
	
	const r = Math.round(Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)));
	for(let i = x1 - r; i <= x1 + r; i++) {
		const yPos = Math.round(y1 + Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		const yNeg = Math.round(y1 - Math.sqrt(Math.pow(r, 2) - Math.pow((x1 - i), 2)));
		if(yPos == yNeg) {
			paintTile(i, yPos);	
		} else {
			drawLine(i, yPos, i, yNeg);
		}
	}
	for(let i = y1 - r; i <= y1 + r; i++) {
		const xPos = Math.round(x1 + Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		const xNeg = Math.round(x1 - Math.sqrt(Math.pow(r, 2) - Math.pow((y1 - i), 2)));
		if(xPos == xNeg) {
			paintTile(xPos, i);	
		} else {
			drawLine(xPos, i, xNeg, i);
		}
	}
}

function drawFillRectangle(x1, y1, x2, y2) {
	const bigY = (y2 > y1) ? y2 : y1;
	const smallY = (y2 > y1) ? y1 : y2;
	for(let i = smallY; i <= bigY; i++) {
		drawLine(x1, i, x2, i);
	}
}

initializeToolsAndBrushes();
