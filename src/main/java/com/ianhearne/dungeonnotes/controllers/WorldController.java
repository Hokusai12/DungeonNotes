package com.ianhearne.dungeonnotes.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ianhearne.dungeonnotes.models.Article;
import com.ianhearne.dungeonnotes.models.Folder;
import com.ianhearne.dungeonnotes.models.TileMap;
import com.ianhearne.dungeonnotes.models.User;
import com.ianhearne.dungeonnotes.models.World;
import com.ianhearne.dungeonnotes.services.ArticleService;
import com.ianhearne.dungeonnotes.services.FolderService;
import com.ianhearne.dungeonnotes.services.TileMapService;
import com.ianhearne.dungeonnotes.services.UserService;
import com.ianhearne.dungeonnotes.services.WorldService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/world")
public class WorldController {
	
	@Autowired
	WorldService worldService;
	@Autowired
	UserService userService;
	@Autowired
	FolderService folderService;
	@Autowired
	ArticleService articleService;
	@Autowired
	TileMapService tileMapService;
	
	@GetMapping("")
	public String viewWorld(
			@RequestParam(name="world-id") Long worldId,
			@RequestParam(name="article-id", required=false) Long articleId,
			Model model,
			HttpSession session) {
		World currentWorld = worldService.getWorldById(worldId);
		model.addAttribute("world", currentWorld);
		model.addAttribute("newFolder", new Folder());
		model.addAttribute("newArticle", new Article());
		
		if(articleId !=  null) {
			model.addAttribute("selectedArticle", articleService.getArticleById(articleId));
		}
		
		//folderService.printFolderStructure(currentWorld.getRootFolder());
		
		
		return "world_templates/viewWorld";
	}
	
	@GetMapping("/new")
	public String newWorld(Model model, HttpSession session) {
		model.addAttribute("newWorld", new World());
		
		return "world_templates/newWorld";
	}
	
	@PostMapping("/new")
	public String saveWorld(
			@Valid @ModelAttribute("newWorld") World newWorld,
			BindingResult result,
			Model model,
			HttpSession session) {
		if(result.hasFieldErrors("name")) {
			return "world_templates/newWorld";
		}
		
		Long userId = (Long) session.getAttribute("userId");
		User currentUser = userService.getUserById(userId);
		
		newWorld.setCreator(currentUser);
		World returnWorld = worldService.saveWorld(newWorld);
		
		if(returnWorld == null) {
			return "world_templates/newWorld";
		}
		
		return "redirect:/home";
	}
	
	@PutMapping("/update")
	public String updateWorld(
			@RequestParam(name="world-id") Long worldId,
			@RequestParam HashMap<String, String> formData,
			Model model,
			HttpSession session) {
		
		World dbWorld = worldService.getWorldById(worldId);
		
		dbWorld.setName(formData.get("name"));
		
		worldService.updateWorld(dbWorld);
		
		return "redirect:/world?world-id=" + dbWorld.getId();
	}
	
	@PostMapping("/search")
	public String searchWorld(
			@RequestParam HashMap<String, String> formData) {
		String searchText = formData.get("search-text");
		return "redirect:/world/search?q="+searchText;
	}
	
	@GetMapping("/search")
	public String returnSearchResults(
		@RequestParam(name="q") String query,
		Model model) {
		
		List<World> searchResults = worldService.searchWorlds(query);
		model.addAttribute("worldList", searchResults);
		
		return "searchResults";
	}
	
	@PostMapping("/add-folder")
	public String addFolder(
			@Valid @ModelAttribute("newFolder") Folder newFolder,
			BindingResult result,
			@RequestParam(name="world-id") Long id,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute("world", worldService.getWorldById(id));
			return "world_templates/viewWorld";
		}
		
		Folder savedFolder = folderService.saveFolder(newFolder);
		
		if(savedFolder.getParentFolder() == null) {
			System.out.println("It's a root folder");
		}
		
		return "redirect:/world?world-id=" + id;
	}
	
	@DeleteMapping("/delete")
	public String deleteWorld(
			@RequestParam(name="world-id") Long worldId,
			@RequestParam(name="article-id", required=false) Long articleId,
			@RequestParam HashMap<String, String> formData) {
		
		if(articleId == null) {
			worldService.deleteById(worldId);
			return "redirect:/home";
		}
		else {
			articleService.deleteArticleById(articleId);
			return "redirect:/world?world-id="+worldId;
		}
	}
	
	@DeleteMapping("/delete-folder")
	public String deleteFolder(
			@RequestParam("world-id") Long worldId,
			@RequestParam("folder-id") Long folderId) {
		World world = worldService.getWorldById(worldId);
		Folder folder = folderService.getFolderById(folderId);
		
		if(worldService.worldHasFolder(world, folder)) {
			folderService.deleteFolder(folderId);
		}
		
		return "redirect:/world?world-id=" + worldId;
	}
	
	@GetMapping("/tile-map-creator")
	public String tileMapCreator(
			@RequestParam("world-id") Long worldId,
			@RequestParam(name="tile-map-id", required=false) Long tileMapId,
			Model model) {
		
		TileMap tileMap = tileMapService.findById(tileMapId);
		List<Integer> tileMapData;
		
		if(tileMap == null) {
			tileMap = new TileMap();
			tileMap.setWidth(50);
			tileMapData = tileMapService.getBlankTileMapData(tileMap.getWidth(), 25);
		}
		else {
			tileMapData = tileMapService.getStretchedTileMapData(tileMap.getTileMapData());
		}
		model.addAttribute("tileMap", tileMap);
		model.addAttribute("tileMapData", tileMapData);
		
		
		return "world_templates/tileMapCreator";
	}
	
	@PostMapping("/save-tile-map")
	public String saveTileMap(
			@RequestParam HashMap<String, String> formData,
			HttpSession session) {
		
		Long userId = (Long) session.getAttribute("userId");
		User user = userService.getUserById(userId);
		Long worldId = Long.parseLong(formData.get("worldId"));
		TileMap tileMap;
		
		//Converts the string of array data from the form to a list of integer data to store in the db
		String[] mapDataString = formData.get("tileData").split(",");
		List<byte[]> newTileMapData = new ArrayList<byte[]>();
		
		

		for(int i = 0; i < mapDataString.length; i++) {
			byte tileType = Byte.parseByte(mapDataString[i]);
			int tileCount = Integer.parseInt(mapDataString[i + 1]);
			byte leftHalf = (byte)((tileCount & -256) >> 8);
			byte rightHalf = (byte)(tileCount & -1);
			byte[] tileData = {tileType, leftHalf, rightHalf};
			newTileMapData.add(tileData);
			i++;
		}
		
		
		//Checks if the tile map already exists or if it is a new creation
		if(!formData.get("tileMapId").equals("NaN")) {
			Long tileMapId = Long.parseLong(formData.get("tileMapId"));
			tileMap = tileMapService.findById(tileMapId);
		} else{
			String mapName = formData.get("mapName");
			Integer mapWidth = Integer.parseInt(formData.get("mapWidth"));
			tileMap = new TileMap();
			tileMap.setWorld(worldService.getWorldById(worldId));
			tileMap.setCreator(user);
			tileMap.setName(mapName);
			tileMap.setWidth(mapWidth);
		}
		tileMap.setTileMapData(newTileMapData);
		
		TileMap savedTileMap = tileMapService.saveTileMap(tileMap);
		
		return "redirect:/world/tile-map-creator?world-id="+worldId+"&tile-map-id="+savedTileMap.getId();
	}
}
