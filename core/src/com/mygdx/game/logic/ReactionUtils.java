package com.mygdx.game.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.GameScreen;
import com.mygdx.game.view.ScreensUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class ReactionUtils {
    public static Feeling target;

    public static void checkGaps (Feeling feeling) {

        for (int i = 1;i <= ProgressData.getGapsCount(); i++) {
            if (!MyGdxGame.startProducts.containsKey(i)) {
                if (isNearGap(feeling,ScreensUtils.xPositions.get(i-1),ScreensUtils.yPositions.get(i-1))) {
                    feeling.getPosition().set(ScreensUtils.xPositions.get(i-1),ScreensUtils.yPositions.get(i-1));
                    MyGdxGame.startProducts.put(i, feeling);
                    feeling.setBenchPosition(i);
                    return;
                }
            }
        }
    }

    private static boolean isNearGap(Feeling feeling, int x, int y) {
        Vector2 place = feeling.getCurrentPosition();
        return place.x >= x - 60 && place.x <= x + 40 && place.y <= y + 60 && place.y >= y - 40;
    }

    public static boolean checkForReaction() {
        if (MyGdxGame.startProducts.size() == ProgressData.getGapsCount()) {
            String[] givenF, targetF;
            ArrayList<Feeling> feelingsArray = new ArrayList<>(MyGdxGame.startProducts.values());
            Collections.sort(feelingsArray, (feeling, t1) -> {
                return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });

            givenF = new String[ProgressData.getGapsCount()];
            targetF = new String[ProgressData.getGapsCount()];
            for (int i = 0; i < givenF.length; i++) {
                givenF[i] = feelingsArray.get(i).getName();
                if (target != null) targetF[i] =  target.getFormula().get(i).getName();
            }

            if (ProgressData.getCurrentLevel() < 3) {
                // Low level Check (до 3 уровня, где работают группы)
                return Arrays.equals(givenF,targetF);
            } else {
                // High level Check (от 3 уровня и выше)
                return highLevelCheck(feelingsArray, givenF);
            }
        }
        return true;
    }

    private static boolean highLevelCheck(ArrayList<Feeling> feelings, String[] givenF) {
        for (int n = feelings.get(0).getNumber(); n < MyGdxGame.feelings.size(); n++) {
            if (MyGdxGame.feelings.get(n).isOpened()) continue;

            if (isFormulaRight(givenF, MyGdxGame.feelings.get(n))) {
                checkGroup(MyGdxGame.feelings.get(n));
                ScreensUtils.newFeeling = MyGdxGame.feelings.get(n);
                ScreensUtils.stageNumber = 1;
                return true;
            }
        }
        return false;
    }

    private static boolean isFormulaRight(String[] givenFormula, Feeling feeling) {
        if (feeling.getFormula() == null) return false;
        if (feeling.getFormula().size() < 2) return false;
        if (feeling.getFormula().size() != givenFormula.length) return false;
        else {
            String[] realFormula = new String[feeling.getFormula().size()];
            for (int i = 0; i < feeling.getFormula().size(); i++) {
                realFormula[i] = feeling.getFormula().get(i).getName();
            }
            return Arrays.equals(givenFormula,realFormula);
        }
    }

    private static void checkGroup(Feeling feeling) {
        for (Feeling f: feeling.getGroup().elements) {
            if (f.isOpened()) return;
        }
        MyGdxGame.new_group = feeling.getGroup();
    }
    
    private static void sort() {
        ArrayList<Feeling> toSort = new ArrayList<>(MyGdxGame.feelings);
        Collections.sort(toSort, (feeling, t1) -> feeling.getNumber() > t1.getNumber()? 1:-1);
        MyGdxGame.feelings = toSort;
    }
}
