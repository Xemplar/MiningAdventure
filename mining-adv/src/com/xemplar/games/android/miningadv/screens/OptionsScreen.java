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

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.xemplar.games.android.miningadv.MiningAdventure;
import com.xemplar.games.android.miningadv.screens.ui.Button;
import com.xemplar.games.android.miningadv.screens.ui.Label;
import com.xemplar.games.android.miningadv.screens.ui.SwitchButton;
import com.xemplar.games.android.miningadv.screens.ui.View;

public class OptionsScreen implements Screen, InputProcessor {
    public static OptionsScreen instance;
	private float buttonHeight;
	
    protected SpriteBatch buttonRenderer;
    protected BitmapFont text;

    protected Label lbl_audio;
    protected Label lbl_lefty;
    protected Label lbl_debug;
    protected SwitchButton audio;
    protected SwitchButton lefty;
    protected SwitchButton debug;
    protected Button back;
    
	protected Array<View> views = new Array<View>();
	protected float width, height;

	public OptionsScreen(){
		instance = this;
	}
	
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		
        buttonRenderer.begin(); {
		    for(View view : views){
                view.render(buttonRenderer);
            }
		} buttonRenderer.end();
	}
	
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.buttonHeight = height / 9F;
		
		float spacer = 10F;
		float buttonWidth = (width * ((3F / 4F) / 2F));
        
        text = MiningAdventure.label;
        
        lbl_audio = new Label(MiningAdventure.text, "Audio", (width / 2F) - (buttonWidth), height - (buttonHeight + spacer), buttonWidth, buttonHeight);
        lbl_lefty = new Label(MiningAdventure.text, "Lefty", (width / 2F) - (buttonWidth), height - ((buttonHeight + spacer) * 2), buttonWidth, buttonHeight);
        lbl_debug = new Label(MiningAdventure.text, "Debug", (width / 2F) - (buttonWidth), height - ((buttonHeight + spacer) * 3), buttonWidth, buttonHeight);
        
        audio = new SwitchButton(text, lbl_audio.x + (lbl_audio.width * 3F/2F) + spacer, height - (buttonHeight + spacer), buttonWidth / 2F, buttonHeight, "audio");
        lefty = new SwitchButton(text, lbl_lefty.x + (lbl_lefty.width * 3F/2F) + spacer, height - ((buttonHeight + spacer) * 2), buttonWidth / 2F, buttonHeight, "lefty");
        debug = new SwitchButton(text, lbl_debug.x + (lbl_debug.width * 3F/2F) + spacer, height - ((buttonHeight + spacer) * 3), buttonWidth / 2F, buttonHeight, "debug");
        
        back = new Button(text, "Back", (width / 2F) - (buttonWidth), spacer, (buttonWidth * 2F), buttonHeight);
        back.setActionNumber(1);
        
        views.clear();
        
        views.add(lbl_audio);
        views.add(lbl_lefty);
        views.add(lbl_debug);
        views.add(audio);
        views.add(lefty);
        views.add(debug);
        views.add(back);
        
        loadSettings();
	}

	public void show() {
		buttonRenderer = new SpriteBatch();
		Gdx.input.setInputProcessor(this);
	}
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	
	public void pause() {
		
	}
	public void resume() {
		
	}
	
	public void loadSettings(){
		Array<View> saveAbles = new Array<View>();
		for(View v : views){
			if(v instanceof SwitchButton){
				saveAbles.add(v);
			}
		}
		
		for(View v : saveAbles){
			if(v instanceof SwitchButton){
				SwitchButton b = (SwitchButton)v;
				String pref = MiningAdventure.prefs.getString(b.getKey());
				if(pref != null){
					b.setFromSave(pref);
				}
			}
		}
	}
	
	public void saveSettings(){
		Array<View> saveAbles = new Array<View>();
		for(View v : views){
			if(v instanceof SwitchButton){
				saveAbles.add(v);
			}
		}
		
		for(View v : saveAbles){
			if(v instanceof SwitchButton){
				SwitchButton b = (SwitchButton)v;
				MiningAdventure.prefs.putString(b.getKey(), b.getSaveState());
			}
		}
		MiningAdventure.prefs.flush();
		MiningAdventure.reloadSettings();
	}
	
	public void doAction(int action){
        if(action == 1){
        	saveSettings();
        	MiningAdventure.mining.setScreen(StartScreen.instance);
        }
    }
	
	public boolean touchDown(int pX, int pY, int pointer, int button) {
        float x = pX;
        float y = height - pY;
        
        boolean value = false;
        
        for(int i = 0; i < views.size; i++){
        	View current = views.get(i);
            if(current.isInside(x, y)){
            	if(current instanceof SwitchButton){
            		continue;
            	} else if(current instanceof Button){
            		((Button)current).setPressed(true);
            	}
                value |= true;
            }
        }
        
        return value;
	}

	public boolean touchUp(int pX, int pY, int pointer, int button) {
		float x = pX;
        float y = height - pY;

        boolean value = false;

        for(int i = 0; i < views.size; i++){
        	View current = views.get(i);
            if(current.isInside(x, y)){
            	if(current instanceof SwitchButton){
            		((SwitchButton)current).toggle();
            	} else if(current instanceof Button){
            		((Button)current).setPressed(false);
                    doAction(((Button)current).getAction());
            	}
                value |= true;
            }
        }

        return value;
	}

	public boolean touchDragged(int pX, int pY, int pointer) {
        float x = pX;
        float y = height - pY;
        
        for(int i = 0; i < views.size; i++){
        	View current = views.get(i);
            if(current.isInside(x, y)){
            	if(current instanceof SwitchButton){
            		continue;
            	} else if(current instanceof Button){
            		((Button)current).setPressed(current.isInside(x, y));
            	}
            }
        }
        
		return false;
	}
	
	public boolean keyDown(int key) {
		return false;
	}
	public boolean keyTyped(char key) {
		return false;
	}
	public boolean keyUp(int key) {
		return false;
	}
	public boolean mouseMoved(int p1, int p2) {
		return false;
	}
	public boolean scrolled(int e) {
		return false;
	}	
}
