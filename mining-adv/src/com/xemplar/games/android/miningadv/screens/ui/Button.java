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
package com.xemplar.games.android.miningadv.screens.ui;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xemplar.games.android.miningadv.MiningAdventure;

public class Button extends Label{
    protected boolean pressed;
    protected Texture tex_not_pressed;
    protected Texture tex_pressed;
    protected int action;
    
    public Button(BitmapFont font, String text, float x, float y, float width, float height){
        super(font, text, x, y, width, height);
    	
    	this.text = text;
        this.font = font;

        Pixmap pix_not_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_not_pressed.setColor(0.5F, 0.5F, 0.5F, 1.0F);
        pix_not_pressed.fill();
        tex_not_pressed = new Texture(pix_not_pressed);
        
        Pixmap pix_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_pressed.setColor(0.7F, 0.7F, 0.7F, 1.0F);
        pix_pressed.fill();
        tex_pressed = new Texture(pix_pressed);
    }
    
    public Button(BitmapFont font, String text, float[] colors, float x, float y, float width, float height){
    	super(font, text, x, y, width, height);
        
        Pixmap pix_not_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_not_pressed.setColor(colors[0], colors[1], colors[2], colors[3]);
        pix_not_pressed.fill();
        tex_not_pressed = new Texture(pix_not_pressed);

        Pixmap pix_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_pressed.setColor(colors[0], colors[1], colors[2], colors[3]);
        pix_pressed.fill();
        tex_pressed = new Texture(pix_pressed);
    }
    
    public Button(BitmapFont font, String text, float[] colors, float[] pressedColors, float x, float y, float width, float height){
    	super(font, text, x, y, width, height);
        
        Pixmap pix_not_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_not_pressed.setColor(colors[0], colors[1], colors[2], colors[3]);
        pix_not_pressed.fill();
        tex_not_pressed = new Texture(pix_not_pressed);

        Pixmap pix_pressed = new Pixmap((int)width, (int)height, Pixmap.Format.RGBA8888);
        pix_pressed.setColor(pressedColors[0], pressedColors[1], pressedColors[2], pressedColors[3]);
        pix_pressed.fill();
        tex_pressed = new Texture(pix_pressed);
    }
    
    public Button setActionNumber(int action){
        this.action = action;
        
        return this;
    }
    
    public int getAction(){
        return action;
    }
    
    public Button setPressed(boolean pressed){
        this.pressed = pressed;
        
        return this;
    }
    
    public boolean isPressed(){
    	return pressed;
    }
    
    public void render(SpriteBatch batch){
        if(pressed){
            batch.draw(tex_pressed, x, y, width, height);
        } else {
            batch.draw(tex_not_pressed, x, y, width, height);
        }
        
        MiningAdventure.layout.setText(MiningAdventure.label, text);
        float width = MiningAdventure.layout.width;
        
        font.draw(batch, text, bounds.getX() + ((bounds.width / 2) - (width / 2)), bounds.getY() + ((bounds.getHeight() / 2F) + (font.getLineHeight() / 4F)));
    }
}
