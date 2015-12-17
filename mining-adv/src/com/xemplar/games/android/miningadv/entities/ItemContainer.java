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
 package com.xemplar.games.android.miningadv.entities;

import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.miningadv.items.Item;

public class ItemContainer extends Entity{
	protected Item item;
	
	public ItemContainer(Vector2 position, String regionID, Item item, int health) {
		super(position, regionID, health);
		this.item = item;
	}
	
	public boolean hasInventory() {
		return false;
	}

	public boolean hasInvSpace() {
		return false;
	}

	public void onKill(Entity e){
		if(e != null){
			if(e.hasInventory() && e.hasInvSpace() && this.item != null){
				e.inventory.addItem(item);
				this.item = null;
			}
		}
	}
	
	public void updateEntity(float delta) {
		
	}

}
