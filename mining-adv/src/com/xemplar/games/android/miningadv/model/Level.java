/*
 * NerdShooter is a pseudo library project for future Xemplar 2D Side Scroller Games.
 * Copyright (C) 2015  Rohan Loomis
 *
 * This file is part of NerdShooter
 *
 * NerdShooter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License, or
 * any later version.
 *
 * NerdShooter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xemplar.games.android.miningadv.model;
import static com.xemplar.games.android.miningadv.blocks.Block.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.miningadv.blocks.Block;
import com.xemplar.games.android.miningadv.entities.Entity;

public class Level {
	public Vector2 jaxonStart;
	private int width;
	private int height;
	
	private Block[] blocks;
	private Block[] extras;
	
	private Array<Entity> entities;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setEntites(Array<Entity> entities){
		this.entities = entities;
	}
	
	public Array<Entity> getEntities(){
		return entities;
	}
	
	public Block[] getBlocks() {
		return blocks;
	}

	public Block[] getExtras(){
		return extras;
	}
	
	public void setBlocks(Block[] blocks) {
		this.blocks = blocks;
	}
	
	public void setExtras(Block[] extras){
		this.extras = extras;
	}
	
	public Level(int levelNum){
		jaxonStart = loadLevel(levelNum);
	}
	
	public Block get(int i) {
		return blocks[i];
	}
	
	private Vector2 loadLevel(int seed){
		Vector2 value = new Vector2(1, 1);
		
		width = 50;
		height = 100;
		
		setupLevel(width, height);
		
		for(int x = 0; x < width; x++){
			addBlock(x, 4, "g");
		}
		
		for(int x = 0; x < width; x++){
			for(int y = 5; y < height; y++){
				addBlock(x, y, "s");
			}
		}
        
		return value;
	}
	
	public void setupLevel(int width, int height){
		entities = new Array<Entity>();
        
		blocks = new Block[width * height];
		extras = new Block[width * height];
		
		for (int index = 0; index < blocks.length; index++) {
			blocks[index] = null;
			extras[index] = null;
		}
	}
	
	@SuppressWarnings("unused")
	private void addExtra(int x, int y, String id){
		//extras[x + y * width] = new Block(new Vector2(x, y), id);
	}
	
	private void addBlock(int x, int y, String id){
		Vector2 pos = new Vector2(x, y);
		Block block = parseID(id, x, y);
		
		if(block == null) return;
		
		blocks[x + y * width] = block.clone(pos);
	}
	
	private Block parseID(String id, int x, int y){
        id = id.toLowerCase();
        id = id.trim();
        
		switch(id){
		case "g" :
    		return grass;
		case "s" :
    		return rock;
        }
		
		return null;
	}
}