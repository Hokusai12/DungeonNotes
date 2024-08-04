package com.ianhearne.dungeonnotes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ianhearne.dungeonnotes.models.TileMap;
import com.ianhearne.dungeonnotes.repositories.TileMapRepository;

@Service
public class TileMapService {
	@Autowired
	TileMapRepository tileMapRepo;
	
	public TileMap findById(Long id) {
		if(id == null) {
			return null;
		}
		Optional<TileMap> opTileMap = tileMapRepo.findById(id);
		if(opTileMap.isPresent()) {
			return opTileMap.get();
		}
		return null;
	}
	
	public TileMap saveTileMap(TileMap tileMap) {
		return tileMapRepo.save(tileMap);
	}
	
	public List<Integer> getBlankTileMapData(int mapWidth, int mapHeight) {
		List<Integer> mapData = new ArrayList<Integer>();
		
		for(int x = 0; x < mapWidth; x++) {
			for(int y = 0; y < mapHeight; y++) {
				mapData.add(0);
			}
		}
		
		return mapData;
	}
	
	public List<Integer> getStretchedTileMapData(List<byte[]> truncatedData) {
		List<Integer> stretchedData = new ArrayList<Integer>();
		
		System.out.println("Stretching...");
		for(byte[] data : truncatedData) {
			System.out.print("{");
			for(byte dataBits : data) {
				System.out.print(dataBits + ", ");
			}
			System.out.print("}, ");
		}
		
		System.out.println("Checking");
		
		for(int currentIndex = 0; currentIndex < truncatedData.size(); currentIndex++) {
			System.out.print("{");
			int currentTileType = truncatedData.get(currentIndex)[0];
			int tileAmountLeftByte = truncatedData.get(currentIndex)[1];
			int tileAmountRightByte = truncatedData.get(currentIndex)[2];
			if(tileAmountLeftByte < 0) {
				tileAmountLeftByte = (tileAmountLeftByte & 127) + 128;
			}
			if(tileAmountRightByte < 0) {
				tileAmountRightByte = (tileAmountRightByte & 127) + 128;
			}
			int tileAmount = (tileAmountLeftByte << 8) | tileAmountRightByte;
			System.out.print(currentTileType + ", ");
			System.out.print(tileAmount  + "}, ");
			for(int i = 0; i < tileAmount; i++) {
				stretchedData.add(currentTileType);
			}
		}
		
		return stretchedData;
	}
}
