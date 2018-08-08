package com.mygdx.game.logic;

import java.util.ArrayList;
import java.util.Collections;

public class Group {
    public ArrayList<Feeling> elements = new ArrayList<>();
    public int number;

    Group(int num,Feeling ...given) {
        number = num;
        Collections.addAll(elements,given);
    }

    public String getWholeText() {
        StringBuilder result = new StringBuilder();
        for (Feeling f: elements) {
            result.append(f.getDescription());
        }
        return result.toString();
    }

    void linkElements() {
        for (Feeling f: elements) {
            if (f == null) continue;
            f.setGroup(this);
        }
    }

    private Feeling get(int i) {
        return elements.get(i);
    }
}
