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
package com.xemplar.games.android.miningadv;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.xemplar.games.android.miningadv.screens.OptionsScreen;
import com.xemplar.games.android.miningadv.screens.SplashScreen;
import com.xemplar.games.android.miningadv.utils.InterScreenData;

public class MiningAdventure extends Game {
	public static boolean PREF_AUDIO = false;
	public static boolean PREF_LEFTY = false;
	public static boolean PREF_DEBUG = false;
	
	public static Preferences prefs;
	public static final float BUTTON_HEIGHT = 6F;
	
    public static final int START_SCREEN = 0x000001;
    public static final int GAME_SCREEN = 0x000002;
    
    public static final String COMP_DATA = "compData";
    public static boolean sanic = false;
    public Boolean useKeys;
    public int[] keys;
    
    public static BitmapFont label, text;
    public static GlyphLayout layout;
    
    public static FreeTypeFontGenerator gen;
    public static FreeTypeFontParameter params;
    
	public static MiningAdventure mining;
    private int screen;
    
    public void create() {
        mining = this;
        
        layout = new GlyphLayout();
        prefs = Gdx.app.getPreferences("settings");
        
        reloadSettings();
        
        keys = (int[])InterScreenData.getInstance("desktop_keys").getData();
        if(keys != null){
        	useKeys = true;
        } else {
        	useKeys = false;
        }
        
        setScreen(new SplashScreen());
        new OptionsScreen();
    }
    
    public static void reloadSettings(){
    	PREF_AUDIO = Boolean.parseBoolean(prefs.getString("audio", Boolean.toString(true)));
    	PREF_LEFTY = Boolean.parseBoolean(prefs.getString("lefty", Boolean.toString(false)));
    	PREF_DEBUG = Boolean.parseBoolean(prefs.getString("debug", Boolean.toString(false)));
    }
    
    public int getCurrentScreen(){
        return screen;
    }
    
    public void setCurrentScreen(int screen){
        this.screen = screen;
    }
}

