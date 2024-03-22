var addArticleBtn = document.querySelector("#add-article-btn");

addArticleBtn.addEventListener("click", () => {
	
	var articleFormHiddenDiv = document.querySelector(".hidden-div");
	var articleForm = document.querySelector("#add-article-form");
	
	console.log("Hidden Div Value: ", articleFormHiddenDiv.innerHTML);
	
	articleFormHiddenDiv.style.display = "flex";
	articleFormHiddenDiv.style.alignItems = "center";
	articleFormHiddenDiv.style.justifyContent = "center";
});