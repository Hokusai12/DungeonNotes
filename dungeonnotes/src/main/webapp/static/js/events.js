var deleteForm = document.querySelector(".delete-form");
var avatarUsername = document.querySelector("#avatar-username");

if(deleteForm)
	deleteForm.addEventListener("submit", onDeleteButtonClick);
if(avatarUsername)
	avatarUsername.addEventListener("click", avatarButtonClick);

function avatarButtonClick() {
	var profileWindow = document.getElementById("profile-window");
	profileWindow.classList.toggle("is-hidden");
}


function onDeleteButtonClick(event) {
	var deleteItem;
	switch(deleteForm.getAttribute("id")) {
		case "world-delete":
			deleteItem = "worlds";
			break;
		case "character-delete":
			deleteItem = "characters";
			break;
		case "poi-delete":
			deleteItem = "points of interest";
			break;
		default:
			deleteItem = "important objects";
			break;
	}
	const response = confirm("You are about to delete one of your ".concat(deleteItem) + ".\nThis action can't be undone. Continue?");
	if(response == false) {
		event.preventDefault();
	}
	return response;
}
