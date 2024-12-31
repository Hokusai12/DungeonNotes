var closeAddFolderBtn = document.querySelector("#close-add-folder-btn");
var closeAddArticleBtn = document.querySelector("#close-add-article-btn");
var addFolderBtn = document.querySelector("#add-folder-btn");
var worldSettingsBtn = document.querySelector("#world-settings-btn");

var updateArticleForm = document.querySelector("#update-article-form");

var summaryLeftSpanElements = document.querySelectorAll("span.summary-left");
var ellipsisIcons = document.querySelectorAll("i.fa-ellipsis-vertical");


function getUrlQueryParameters() {
	var urlParamString = window.location.href.split("?")[1];
	var urlParamMap = new Map();
	var urlParamStringArr = urlParamString.split("&");
	for(var i = 0; i < urlParamStringArr.length; i++) {
		var urlParamAndValue = urlParamStringArr[i].split("=");
		urlParamMap.set(urlParamAndValue[0], urlParamAndValue[1]);
	}
	return urlParamMap;
}

function onArticleUpdateSubmit() {
	var articleText = document.querySelector("#text-editor").innerText;
	var articleTextInput = document.querySelector("#article-text");
	console.log("Submitting Form");
	articleTextInput.setAttribute("value", articleText);
}

function hideDiv(id) {
	var divToHide = document.querySelector(id);
	
	divToHide.style.display = "none";
}

function showDiv(id) {
	var hiddenDiv = document.querySelector(id);
	
	hiddenDiv.style.display = "flex";
	hiddenDiv.style.alignItems = "center";
	hiddenDiv.style.justifyContent = "center";
}

function deleteFolder(folderId) {
	
	var folder = document.getElementById(folderId);
	var folderContent = folder.querySelector(".folder-content");
	var folderHasContent = (folderContent.childNodes.length) > 0;
	
	if(folderHasContent && !confirm("This folder has content that will be deleted, are you sure you want to do that?")) {
		return;
	}
	
	
	var deleteFolderForm = document.createElement("form");
	var urlParams = getUrlQueryParameters();
	var worldId = urlParams.get("world-id");
	
	var hiddenInputDeleteMethod = document.createElement("input");
	hiddenInputDeleteMethod.type = "hidden";
	hiddenInputDeleteMethod.name = "_method";
	hiddenInputDeleteMethod.value = "DELETE";
	
	deleteFolderForm.action = `/world/delete-folder?world-id=${worldId}&folder-id=${folderId}`;
	deleteFolderForm.method = "POST";
	deleteFolderForm.appendChild(hiddenInputDeleteMethod);
	
	document.body.appendChild(deleteFolderForm);
	deleteFolderForm.submit();
}

function editArticleOrWorldName() {
	var urlParams = getUrlQueryParameters();
	var worldId = urlParams.get("world-id");
	var articleId = urlParams.get("article-id");
	
	var isWorldEdit = articleId == null;
	var nameHeader = (isWorldEdit) ? document.querySelector("#world-name") : document.querySelector("#article-name");
	
	var worldNameForm = document.createElement("form");
	worldNameForm.action = (isWorldEdit) ? "/world/update?world-id="+worldId : `/world/article/update-w-form?world-id=${worldId}&article-id=${articleId}`;
	worldNameForm.method = "POST";
	worldNameForm.style.display = "inline";
	
	var hiddenInput = document.createElement("input");
	hiddenInput.type = "hidden";
	hiddenInput.name = "_method";
	hiddenInput.value = "PUT";
	
	var worldNameInput = document.createElement("input");
	worldNameInput.type = "text";
	worldNameInput.name = "name";
	worldNameInput.id = "name";
	worldNameInput.value = nameHeader.innerText;
	
	var submitBtn = document.createElement("button");
	submitBtn.innerText = "Save Changes";
	
	worldNameForm.appendChild(hiddenInput);
	worldNameForm.appendChild(worldNameInput);
	worldNameForm.appendChild(submitBtn);
	
	nameHeader.replaceWith(worldNameForm);
}

function deleteWorldOrArticle() {
	var urlParams = getUrlQueryParameters();
	var worldId = urlParams.get("world-id");
	var articleId = urlParams.get("article-id");
	
	var isWorldDelete = articleId == null;
	
	var deleteForm = document.createElement("form");
	deleteForm.method = "POST";
	deleteForm.action = isWorldDelete ? `world/delete?world-id=${worldId}` : `world/delete?world-id=${worldId}&article-id=${articleId}`;
	
	var hiddenInput = document.createElement("input");
	hiddenInput.type = "hidden";
	hiddenInput.value = "DELETE";
	hiddenInput.name = "_method";
	
	deleteForm.appendChild(hiddenInput);
	
	document.body.appendChild(deleteForm);
	
	deleteForm.submit();	
}

function onSummaryClick(e) {
	var summaryLeftSpan = e.currentTarget;
	var caretIcon = summaryLeftSpan.getElementsByTagName("i")[0];
	
	if(caretIcon.className == "fa-solid fa-caret-right"){
		caretIcon.className = "fa-solid fa-caret-down";
	}
	else if(caretIcon.className == "fa-solid fa-caret-down") {
		caretIcon.className = "fa-solid fa-caret-right";
	}
};

function createPopUpOption(text, onClick) {
	var opt = document.createElement("p");
	opt.innerText = text;
	opt.classList.add("popup-opt");
	opt.addEventListener("click", onClick);
	return opt;
}

function selectFolderOption(folderId, isArticle) {
	var addObjectForm = (isArticle) ? document.querySelector("#add-article-form") : document.querySelector("#add-folder-form");
	var selectInput = addObjectForm.querySelector("select");
	var options = selectInput.querySelectorAll("option");
	
	for(var i = 0; i < options.length; i++) {
		if(options[i].value == folderId) {
			options[i].selected = true;
			return;
		}
	}
}

function onMouseOutInPopupDiv(e) {
	document.body.removeChild(e.currentTarget);
}

function onWorldSettingsClick(e) {
	var vpWidth = window.visualViewport.width;
	var vpHeight = window.visualViewport.height;
	
	var popupWidth = 125;
	var popupHeight = 250;
	
	var mouseX = e.clientX;
	var mouseY = e.clientY;
	
	

	var popupDiv = document.createElement("div");
	popupDiv.id = "popup-div";
	popupDiv.classList.add("world-settings-popup");
	
	popupDiv.style.left = (mouseX + popupWidth > vpWidth) ? `${mouseX - popupWidth}px` : `${mouseX}px`;
	popupDiv.style.top = (mouseY + popupHeight > vpHeight) ? `${mouseY - popupHeight}px` : `${mouseY}px`;
	
	var editNameOpt = createPopUpOption("Edit Name", editArticleOrWorldName);
	var deleteOpt = createPopUpOption("Delete", deleteWorldOrArticle);
	
	popupDiv.appendChild(editNameOpt);
	popupDiv.appendChild(deleteOpt);
	
	document.body.appendChild(popupDiv);
	
	popupDiv.addEventListener("mouseleave", onMouseOutInPopupDiv);
}

function onFolderEllipsisClick(e) {
	e.stopPropagation();
	e.preventDefault();
	
	var folderDetails = e.currentTarget.parentElement.parentElement;
	
	var mouseX = e.clientX;
	var mouseY = e.clientY;
	
	var addArticleOpt = createPopUpOption("Add Article", function() {showDiv("#add-article-hidden-div"); selectFolderOption(folderDetails.id, true)});
	var deleteFolderOpt = createPopUpOption("Delete Folder", function() {deleteFolder(folderDetails.id)});
	var addFolderOpt = createPopUpOption("Add Subfolder", function() {showDiv("#add-folder-hidden-div"); selectFolderOption(folderDetails.id, false)});
	
	var popupDiv = document.createElement("div");
	popupDiv.id = "popup-div";
	popupDiv.classList.add("folder-popup");
	
	popupDiv.style.left = `${mouseX - 20}px`;
	popupDiv.style.top = `${mouseY - 20}px`;
	
	popupDiv.appendChild(addArticleOpt);
	popupDiv.appendChild(deleteFolderOpt);
	popupDiv.appendChild(addFolderOpt);
	
	document.body.appendChild(popupDiv);
	
	popupDiv.addEventListener("mouseleave", onMouseOutInPopupDiv);
}

worldSettingsBtn.addEventListener("click", onWorldSettingsClick);

addFolderBtn.addEventListener("click", function(){showDiv("#add-folder-hidden-div")});
closeAddFolderBtn.addEventListener("click", function(){hideDiv("#add-folder-hidden-div")});
closeAddArticleBtn.addEventListener("click", function(){hideDiv("#add-article-hidden-div")});

for(var i = 0; i < summaryLeftSpanElements.length; i++) {
	summaryLeftSpanElements[i].addEventListener("click", onSummaryClick, true);
}

for(var i = 0; i < ellipsisIcons.length; i++) {
	ellipsisIcons[i].addEventListener("click", onFolderEllipsisClick, true);
}

if(updateArticleForm != null) {
	updateArticleForm.addEventListener("submit", onArticleUpdateSubmit);
}