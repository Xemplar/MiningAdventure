/*
 * Mining Adventure is tycoon like game where the goal is to collect materials,
 * sell them, then upgrade you digger
 * 
 * Copyright (C) 2015  Rohan Loomis
 *
 * This file is part of Mining Adventure
 *
 * Mining Adventure is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License, or
 * any later version.
 *
 * Mining Adventure is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xemplar.games.android.miningadv.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.miningadv.blocks.Block;
import com.xemplar.games.android.miningadv.entities.Entity;
import com.xemplar.games.android.miningadv.entities.Jaxon;

public class World {
	private Level level;
    private Jaxon jaxon;
	
    public Jaxon getJaxon() {
        return jaxon;
    }
	
    public Level getLevel() {
        return level;
    }

	public Array<Entity> getEntities(){
		return level.getEntities();
	}
	
	public void setBlock(int x, int y, Block b){
		level.getBlocks()[x + y * level.getWidth()] = b.clone(new Vector2(x, y));
	}
	
	public Block getBlock(Vector2 pos){
		Array<Block> blocks = getBlocks(level.getWidth(), level.getHeight());
		for(Block block : blocks){
			if(block.getBounds().contains(pos)){
				return block;
            }
		}
		
		return null;
	}
	
    public Array<Block> getBlocks(int width, int height) {
        int x = (int)jaxon.getPosition().x - width;
        int y = (int)jaxon.getPosition().y - height;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
		
        int x2 = x + 2 * width;
        int y2 = y + 2 * height;
		
        if (x2 > level.getWidth()) {
            x2 = level.getWidth() - 1;
        }
        if (y2 > level.getHeight()) {
            y2 = level.getHeight() - 1;
        }
		
        Array<Block> blocks = new Array<Block>();
        Block block = null;
		
        for (int col = x; col <= x2; col++) {
            for (int row = y; row <= y2; row++) {
				if(col >= level.getWidth()) continue;
				if(row >= level.getHeight()) continue;
				
                block = level.getBlocks()[col + row * level.getWidth()];
                if (block != null) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }
	
    public Array<Block> getVisbleBlocks(int width, int height) {
    	int x = (int)jaxon.getPosition().x - width;
        int y = (int)jaxon.getPosition().y - height;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
		
        int x2 = x + 2 * width;
        int y2 = y + 2 * height;
		
        if (x2 > level.getWidth()) {
            x2 = level.getWidth() - 1;
        }
        if (y2 > level.getHeight()) {
            y2 = level.getHeight() - 1;
        }
		
        Array<Block> blocks = new Array<Block>();
        Block block = null;
		
        for (int col = x; col <= x2; col++) {
            for (int row = y; row <= y2; row++) {
				if(col >= level.getWidth()) continue;
				if(row >= level.getHeight()) continue;
				
                block = level.getBlocks()[col + row * level.getWidth()];
                if (block != null) {
                	//if(!block.isHidden()){
                		blocks.add(block);
                	//}
                }
            }
        }
        return blocks;
	}
    
	public World(int seed){
        level = new Level(seed);
		jaxon = new Jaxon(level.jaxonStart);
		getEntities().add(jaxon);
	}
}

