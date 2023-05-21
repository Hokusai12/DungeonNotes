<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/css/dungeonstyle.css"/>
<title>Dungeon Maker</title>
</head>
<body>
	<div id="dungeon-window">
		<div id="tile-map">
		</div>
	</div>
	
	<input type="number" id="zoom-input" step="0.1" value="1"/>
	<input type="color" id="color-input" value="red"/>
	<select name="toolType" id="tool-select">
		<option value="FILL">Fill</option>
		<option value="OUTLINE">Outline</option>
		<option value="LINE">Line</option>
		<option value="SINGLE">Single</option>
	</select>
	Erase
	<input type="checkbox" name="erase" id="erase-checkbox" value="1"/>
	
	<script type="text/javascript" src="/js/dungeonmaker.js"></script>
</body>
</html>