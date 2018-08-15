package com.mygdx.game.logic;

import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Collections;

public class Group {
    public ArrayList<Feeling> elements = new ArrayList<>();
    public int number;
    public boolean isOpened;

    public Group() {
        number = 0;
        isOpened = true;
    }

    Group(int num,Feeling ...given) {
        number = num;
        isOpened = false;
        for (Feeling f:given) {
            if (f.isOpened()) isOpened = true;
        }
        Collections.addAll(elements,given);
    }

    public boolean isFinished() {
        for (Feeling f:elements) {
            if (!f.isOpened()) return false;
        }
        return true;
    }

    void linkElements() {
        for (Feeling f: elements) {
            if (f == null) continue;
            f.setGroup(this);
        }
    }

    public boolean contains(Feeling feeling) {
        for (Feeling f:elements) {
            if (f == feeling) return true;
        }
        return false;
    }

    public static void setTargetGroup() {
        for (Feeling f: MyGdxGame.feelings) {
            if (!f.isOpened()) {
                ReactionUtils.target = f.getGroup();
                break;
            }
        }
    };


}
