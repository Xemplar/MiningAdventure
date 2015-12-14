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
package com.xemplar.games.android.miningadv.blocks;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.xemplar.games.android.miningadv.entities.Entity;
import com.xemplar.games.android.miningadv.screens.GameScreen;

public class Block {
	public static final Vector2 empty = new Vector2(2, 2);
	//Block Definitions
		//Grass
		public static Block grass = new Block(empty, "grass_side");
		public static Block rock = new Block(empty, "stone");
		
		
		
	//Start Class
		
	public long id = 0L;
	
	public String regionID;
	
    protected Vector2 position = new Vector2();
	protected Vector2 spawnPoint = new Vector2();
    protected Rectangle bounds = new Rectangle();

    protected Block(Vector2 pos, String regionID) {
		this.regionID = regionID;
		this.spawnPoint = new Vector2(pos);
        this.position = pos;
		this.bounds.setPosition(pos);
        this.bounds.setSize(1F);
        
        id = (new Random()).nextLong();
    }
    
    protected Block(Vector2 pos, String regionID, float size) {
    	this.regionID = regionID;
		this.spawnPoint = new Vector2(pos);
        this.position = pos;
		this.bounds.setPosition(pos);
    	this.bounds.setSize(size);
        

        id = (new Random()).nextLong();
    }
    
    protected Block(Vector2 pos, String regionID, float width, float height) {
    	this.regionID = regionID;
		this.spawnPoint = new Vector2(pos);
        this.position = pos;
		this.bounds.setPosition(pos);
        this.bounds.setSize(width, height);
        
        id = (new Random()).nextLong();
    }
    
    public boolean isCollideable(){
        return true;
    }
    
    public boolean isHidden(){
        return false;
    }
    
    public boolean isTouchable(){
        return false;
    }
    
    public boolean isAnimated(){
        return false;
    }
    
    public void onTouch(Entity e){
        
    }
    
	public TextureRegion getTexture(){
		return GameScreen.getTextureAltlas().findRegion(regionID);
	}
	
	public Rectangle getBounds(){
		return bounds;
    }
	
	public Vector2 getPosition(){
		return position;
    }
	
	public Vector2 getSpawnPoint(){
		return spawnPoint;
	}
    
	public Block clone(Vector2 pos){
		Block b = new Block(pos, regionID, this.bounds.getWidth(), this.bounds.getWidth());
		return b;
	}
	
    public void render(SpriteBatch batch){
        if(!isHidden()){
            batch.draw(getTexture(), getPosition().x, getPosition().y, bounds.getWidth(), bounds.getHeight());
        }   
    }
}