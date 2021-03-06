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

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer.Task;
import com.xemplar.games.android.miningadv.blocks.Block;
import com.xemplar.games.android.miningadv.controller.Controller;
import com.xemplar.games.android.miningadv.controller.EntityController;
import com.xemplar.games.android.miningadv.inventory.Inventory;

public abstract class Entity extends Block{
	public enum State {
        IDLE, WALKING, JUMPING, DYING
	}
	
    public static final float SPEED = 5f;  // unit per second
    public static final float JUMP_VELOCITY = 1f;
    public static final int UNLIMITED = 0xF00000;
    protected final int maxHealth;
    protected int health = 0;
    
    public Inventory inventory;
    protected boolean hidden, reset, hasSpawned;
    protected Controller controller;

	protected float stateTime = 0;
    protected Vector2 acceleration = new Vector2();
    protected Vector2 velocity = new Vector2();
    protected State state = State.IDLE;
    protected boolean facingLeft = true;
    
    public Entity(Vector2 position, int health) {
		super(position, "");
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
    }
	
    public Entity(Vector2 position, float size, int health) {
        super(position, "", size);
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
    }
    
    public Entity(Vector2 position, float width, float height, int health) {
    	super(position, "", width, height);
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
    }
    
	public Entity(Vector2 position, String regionID, int health){
		super(position, regionID);
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
	}

    public Entity(Vector2 position, String regionID, float size, int health){
    	super(position, regionID, size);
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
	}
    
    public Entity(Vector2 position, String regionID, float width, float height, int health){
    	super(position, regionID, width, height);
        this.health = health;
        this.maxHealth = health;
        this.controller = new EntityController(this);
	}
    
    public void setState(State newState) {
        this.state = newState;
    }

    public void setFacingLeft(boolean left){
        this.facingLeft = left;
    }

    public boolean isFacingLeft(){
        return facingLeft;
    }

    public State getState(){
        return state;
    }

    public float getStateTime(){
        return stateTime;
    }
    
	public void setPosition(Vector2 position) {
		this.position = position;
		this.bounds.setPosition(this.position);
	}

    public void setCheckPoint(Vector2 point){
        this.spawnPoint = point.cpy();
        this.spawnPoint.add(0.025F, 0);
    }
    
	public void respawn(){
        this.health = maxHealth;
        this.hidden = false;
        velocity.set(0F, 0F);
		setPosition(spawnPoint.cpy());
		
		if(this.hasInventory()){
			this.inventory.clear();
		}
        
        reset = false;
	}
	
	public Rectangle getBounds(){
		return bounds;
    }

	public Vector2 getVelocity(){
		return velocity;
	}

	public Vector2 getAcceleration(){
		return acceleration;
	}

	public Vector2 getPosition(){
		return position;
    }
    
    public Controller getController(){
    	return controller;
    }
    
    public final void kill(Entity e){
    	this.health = 0;
    	onKill(e);
    }
    
    protected void onKill(Entity e){
    	
    }
    
    public final void hurt(Entity e, int amt){
        if(!isDead()){
            this.health = this.health - amt;
            onHurt(e);
        }
    }

    protected void onHurt(Entity e){
    	
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    public boolean isDead(){
        return health <= 0;
    }
    
    public boolean isRespawnable(){
        return false;
    }
    
    public boolean collideWithEntities(){
        return false;
    }
    
    public boolean collideWithBlocks(){
        return false;
    }
    
    public boolean affectWithGravity(){
        return false;
    }
    
    public abstract boolean hasInventory();
    public abstract boolean hasInvSpace();
    public abstract void updateEntity(float delta);
    
    public void update(float delta){
        if(isDead()){
            if(hasInventory() && inventory.hasItems()){
                inventory.clear();
            }
        }
        
        controller.update(delta);
        
        if(!isHidden()){
            updateEntity(delta);
        }
    }
    
    protected Task respawn = new Task(){
        public void run(){
            respawn();
        }
    };
}
