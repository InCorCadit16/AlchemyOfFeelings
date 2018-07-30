package com.mygdx.game.logic;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;

public class AtlasPage {
    public int title;
    public ArrayList<Button> elements;
    
    public AtlasPage() {
        title = 0;
        elements = new ArrayList<>();
    }
    
    public void add(Button btn) {
        elements.add(btn);
    }
    
    public void sort() {
        final int ATLAS_START_X = 75, ATLAS_START_Y = 375, ATLAS_ADD_X = 390, ATLAS_ADD_Y = 80;
        int x = ATLAS_START_X, y = ATLAS_START_Y;
        for (int i = 0; i < elements.size();i++) {
            elements.get(i).setPosition(x,y);
            // Устанавливаем координаты иконок
            x -= 5;
            y -= ATLAS_ADD_Y;
            if (i+1 == 10) { x = ATLAS_START_X; y = ATLAS_START_Y; }
            else if (i+1 == 5) { x += ATLAS_ADD_X; y = ATLAS_START_Y; }
        }
    }
}
