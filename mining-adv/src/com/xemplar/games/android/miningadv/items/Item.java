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
package com.xemplar.games.android.miningadv.items;
import com.xemplar.games.android.miningadv.blocks.ItemBlock;
import com.xemplar.games.android.miningadv.entities.Entity;

public class Item {
    public static Item BLUE_KEY = new Item(0, 8, "keyBlue");
    
    public int id;
    public int maxStack;
    public String regionID;
    
    protected ItemBlock block;
    
    protected Item(int id, int maxStack, String regionID){
        this.id = id;
        this.maxStack = maxStack;
        this.regionID = regionID;
    }
    
    public Item clone(){
        return new Item(id, maxStack, regionID);
    }
    
    public void setBlock(ItemBlock b){
        this.block = b;
    }
    
    public void returnToBlock(Entity e){
        e.inventory.removeItem(this);
        if(block != null){
            block.returnItem(this);
        }
    }
    
    public String getRegionID(){
        return regionID;
	}
}
