/*
 * MiningAdventure is a pseudo library project for future Xemplar 2D Side Scroller Games.
 * 
 * Copyright (C) 2015  Rohan Loomis
 *
 * This file is part of MiningAdventure
 *
 * MiningAdventure is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License, or
 * any later version.
 *
 * MiningAdventure is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xemplar.games.android.miningadv.screens;

import static com.badlogic.gdx.graphics.GL20.GL_BLEND;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.graphics.GL20.GL_ONE_MINUS_SRC_ALPHA;
import static com.badlogic.gdx.graphics.GL20.GL_SRC_ALPHA;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.xemplar.games.android.miningadv.MiningAdventure;
import com.xemplar.games.android.miningadv.controller.JaxonController;
import com.xemplar.games.android.miningadv.entities.Entity;
import com.xemplar.games.android.miningadv.entities.Jaxon;
import com.xemplar.games.android.miningadv.model.World;
import com.xemplar.games.android.miningadv.view.WorldRenderer;

public class GameScreen implements Screen, InputProcessor {
	public static boolean useGameDebugRenderer = false;
    public static GameScreen instance;
    public static long gameTicks = 0L;
	private static TextureAtlas atlas;
	private Rectangle left, right, jump, fire, sanic;
    public World world;
    private Jaxon jaxon;
    public float buttonSize = 0F;
    
    public static Texture tex;
    public static int numPressed = 0;
    
    private WorldRenderer renderer;
    private JaxonController controller;
	private ShapeRenderer button;
	private SpriteBatch batch;
	private BitmapFont font;
    public int width, height;
	
	private TextureRegion controlLeft;
	private TextureRegion controlRight;
	private TextureRegion controlUp;
	
	public GameScreen(int seed){
		gameTicks = 0L;
        instance = this;
        
        tex = new Texture(Gdx.files.internal("scatt.png"));
		atlas = new TextureAtlas(Gdx.files.internal("textures/mining-adventure.atlas"));
		
		font = MiningAdventure.gen.generateFont(MiningAdventure.params);
		font.setColor(1, 1, 1, 1);
        
		controlLeft = atlas.findRegion("HUDLeft");
		controlRight = atlas.findRegion("HUDRight");
		controlUp = atlas.findRegion("HUDJump");
		
		world = new World(seed);
		jaxon = world.getJaxon();
		controller = jaxon.getController();
		
		button = new ShapeRenderer();
		batch = new SpriteBatch();
		
		left = new Rectangle();
		right = new Rectangle();
		
		jump = new Rectangle();
		fire = new Rectangle();
		sanic = new Rectangle();
	}
	
    public void show() {
        gameTicks = 0L;
        
        renderer = new WorldRenderer(world, useGameDebugRenderer);
        controller.reset();
        Gdx.input.setInputProcessor(this);
        
        MiningAdventure.mining.setCurrentScreen(MiningAdventure.GAME_SCREEN);
    }
	
    public void render(float delta) {
		gameTicks++;
		
        long seconds = (long)((gameTicks / 60D) * 10L);
        
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        
		updateEntities(delta);
        renderer.render();
        
        button.begin(ShapeRenderer.ShapeType.Filled);{
            if (Gdx.app.getType().equals(Application.ApplicationType.Android)){
                Gdx.gl.glEnable(GL_BLEND);
                Gdx.gl.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

                button.setColor(0.0F, 0.5F, 0.5F, 0.5F);
                button.rect(left.x, left.y, left.width, left.height);
                button.rect(right.x, right.y, right.width, right.height);

                button.setColor(1.0F, 1.0F, 1.0F, 0.5F);
                button.rect(jump.x, jump.y, jump.width, jump.height);
                button.rect(fire.x, fire.y, fire.width, fire.height);
            }
            
            jaxon.inventory.renderGUI(button, width, height, buttonSize * 0.75F);
        } button.end();
        
        batch.begin(); {
            if (Gdx.app.getType().equals(Application.ApplicationType.Android)){
                batch.draw(controlLeft, left.x, left.y, left.width, left.height);
                batch.draw(controlRight, right.x, right.y, right.width, right.height);
				batch.draw(controlUp, jump.x, jump.y, jump.width, jump.height);
            }
            
            jaxon.inventory.renderItems(batch, width, height, buttonSize * 0.75F);
            if(MiningAdventure.PREF_DEBUG){
            	font.draw(batch, "Time: " + (seconds / 10D) + " seconds, FPS: " + Gdx.graphics.getFramesPerSecond(), 0, height - 10);
            }
        } batch.end();
    }
	    
    public void resize(int width, int height) {
        renderer.setSize(width, height);
        this.width = width;
        this.height = height;
		buttonSize = height / MiningAdventure.BUTTON_HEIGHT;
		
		left.set(buttonSize / 2F, buttonSize / 2F, buttonSize, buttonSize);
		right.set(buttonSize * 2F, buttonSize / 2F, buttonSize, buttonSize);
		
		jump.set(width - (buttonSize * 3F/2F), buttonSize / 2F, buttonSize, buttonSize);
		fire.set(width - (buttonSize * 3F/2F), buttonSize * 2F, buttonSize, buttonSize);
		
		sanic.set(0, height - buttonSize, buttonSize, buttonSize);
		
		if(!MiningAdventure.PREF_LEFTY){
			left.set(buttonSize / 2F, buttonSize / 2F, buttonSize, buttonSize);
			right.set(buttonSize * 2F, buttonSize / 2F, buttonSize, buttonSize);
			
			jump.set(width - (buttonSize * 3F/2F), buttonSize / 2F, buttonSize, buttonSize);
			fire.set(width - (buttonSize * 3F/2F), buttonSize * 2F, buttonSize, buttonSize);
		} else {
			left.set(width - (buttonSize * 3F), buttonSize / 2F, buttonSize, buttonSize);
			right.set(width - (buttonSize * 3F/2F), buttonSize / 2F, buttonSize, buttonSize);
			
			jump.set(buttonSize / 2F, buttonSize / 2F, buttonSize, buttonSize);
			fire.set(buttonSize / 2F, buttonSize * 2F, buttonSize, buttonSize);
		}
    }
	
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
	
    public void pause() {
    }
	
    public void resume() {
        MiningAdventure.mining.setScreen(StartScreen.instance);
    }
	
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }
	
    public boolean keyDown(int keycode) {
    	if(MiningAdventure.mining.useKeys){
    		if (keycode == MiningAdventure.mining.keys[0]){
		    	controller.leftPressed(-1);
    		}
        	if (keycode == MiningAdventure.mining.keys[1]){
		    	controller.rightPressed(-1);
        	}
        	if (keycode == MiningAdventure.mining.keys[2]){
		    	controller.jumpPressed(-1);
        	}
    	} else {
    		if (keycode == Keys.LEFT){
		    	controller.leftPressed(-1);
    		}
        	if (keycode == Keys.RIGHT){
        		controller.rightPressed(-1);
        	}
        	if (keycode == Keys.SPACE){
				controller.jumpPressed(-1);
        	}
    	}
    	
    	if (keycode == Keys.S && MiningAdventure.sanic){
        	MiningAdventure.sanic = false;
        	jaxon.loadTextures();
        	StartScreen.reloadMusic();
    	} else if (keycode == Keys.S && !MiningAdventure.sanic){
        	MiningAdventure.sanic = true;
        	jaxon.loadTextures();
        	StartScreen.reloadMusic();
    	}
    	
    	if (keycode == Keys.NUM_1){
	    	jaxon.inventory.setSelctedItem(3);
		}
    	if (keycode == Keys.NUM_2){
    		jaxon.inventory.setSelctedItem(2);
    	}
    	if (keycode == Keys.NUM_3){
    		jaxon.inventory.setSelctedItem(1);
    	}
    	if (keycode == Keys.NUM_4){
    		jaxon.inventory.setSelctedItem(0);
    	}
    	
        return true;
    }
	
    public boolean keyUp(int keycode) {
    	if(MiningAdventure.mining.useKeys){
    		if (keycode == MiningAdventure.mining.keys[0]){
		    	controller.leftReleased();
    		}
        	if (keycode == MiningAdventure.mining.keys[1]){
		    	controller.rightReleased();
        	}
        	if (keycode == MiningAdventure.mining.keys[2]){
		    	controller.jumpReleased();
        	}
    	} else {
    		if (keycode == Keys.LEFT){
		    	controller.leftReleased();
    		}
        	if (keycode == Keys.RIGHT){
		    	controller.rightReleased();
        	}
        	if (keycode == Keys.SPACE){
		    	controller.jumpReleased();
        	}
    	}
        return true;
    }
	
    public boolean keyTyped(char character) {
        return false;
    }
	
	
    public boolean touchDown(int x, int y, int pointer, int button) {
		if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
        	if(left.contains(x, (y - height) * -1)){
				controller.leftPressed(pointer);
			}
			
			if(right.contains(x, (y - height) * -1)){
				controller.rightPressed(pointer);
			}
		
			if(jump.contains(x, (y - height) * -1)){
				controller.jumpPressed(pointer);
			}
		    
            if(sanic.contains(x, (y - height) * -1)){
                if (MiningAdventure.sanic){
                    MiningAdventure.sanic = false;
                    jaxon.loadTextures();
                    StartScreen.reloadMusic();

                    numPressed = 0;
                } else if (!MiningAdventure.sanic){
                    System.out.println(numPressed);
                    if(numPressed == 2){
                        MiningAdventure.sanic = true;
                        jaxon.loadTextures();
                        StartScreen.reloadMusic();
                    }

                    numPressed++;
                }
                return true;
            }
		}
		
		return jaxon.inventory.pressed(x, (y - height) * -1);
    }
	
    public boolean touchUp(int x, int y, int pointer, int button) {
		if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
        	if(left.contains(x, (y - height) * -1)) {
            	controller.leftReleased();
        	}
		
        	if(right.contains(x, (y - height) * -1)) {
            	controller.rightReleased();
        	}
		
        	if(jump.contains(x, (y - height) * -1)){
				controller.jumpReleased();
			}
        	return true;
		}
		return false;
    }
	
    public boolean touchDragged(int x, int y, int pointer) {
		if(Gdx.app.getType().equals(Application.ApplicationType.Android)){
			if(controller.isLeftDown() && !left.contains(x, (y - height) * -1) && controller.leftPointer == pointer) {
            	controller.leftReleased();
        	}
			if(controller.isRightDown() && !right.contains(x, (y - height) * -1) && controller.rightPointer == pointer) {
            	controller.rightReleased();
        	}
			if(controller.isJumpDown() && !jump.contains(x, (y - height) * -1) && controller.jumpPointer == pointer) {
        	    controller.jumpReleased();
        	}
			
			if(!controller.isLeftDown() && left.contains(x, (y -height) * -1) && controller.leftPointer == -1){
				controller.leftPressed(pointer);
			}
			
			if(!controller.isRightDown() && right.contains(x, (y -height) * -1) && controller.rightPointer == -1){
				controller.rightPressed(pointer);
			}
			
			if(!controller.isJumpDown() && jump.contains(x, (y -height) * -1) && controller.jumpPointer == -1){
				controller.jumpPressed(pointer);
			}
        	return true;
		}
		return false;
    }
	
    public boolean mouseMoved(int x, int y) {
        return false;
    }
    
    public boolean scrolled(int amount) {
        return false;
    }
	
	public void updateEntities(float delta){
		for(Entity e : world.getEntities()){
			int w = world.getLevel().getWidth();
			int h = world.getLevel().getHeight();
			
			if(e.getPosition().x + 1 < 0){
				e.kill(null);
				continue;
			}
			
			if(e.getPosition().x > w){
				e.kill(null);
				continue;
			}
			
			if(e.getPosition().y + 1 < 0){
				e.kill(null);
				continue;
			}
			
			if(e.getPosition().y > h){
				e.kill(null);
				continue;
			}
			
			e.update(delta);
		}
	}
	
	public static TextureAtlas getTextureAltlas(){
		return atlas;
	}
}
