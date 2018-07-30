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

    public static void checkGaps (Feeling feeling) {

        for (int i = 1;i <= ProgressData.getGapsCount(); i++) {
            if (!MyGdxGame.startProducts.containsKey(i)) {
                if (isNearGap(feeling,ScreensUtils.xPositions.get(i-1),ScreensUtils.yPositions.get(i-1))) {
                    feeling.getPosition().set(ScreensUtils.xPositions.get(i-1),ScreensUtils.yPositions.get(i-1));
                    MyGdxGame.startProducts.put(i, feeling);
                    feeling.setBenchPosition(i);
                    if (ProgressData.getCurrentLevel() > 2 & ProgressData.getCurrentLevel() < 5 &
                            feeling.getBenchPosition() == ProgressData.getGapsCount() - 1)
                        choiceRareElementCheck();
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
            String[] stringMassive = new String[ProgressData.getGapsCount()];
            ArrayList<Feeling> feelingsArray = new ArrayList<>(MyGdxGame.startProducts.values());
            Collections.sort(feelingsArray, (feeling, t1) -> {
                return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });
            for (int i = 0; i < stringMassive.length; i++) {
                stringMassive[i] = feelingsArray.get(i).getName();
            }

            for (int n = feelingsArray.get(0).getNumber(); n < MyGdxGame.feelings.size(); n++) {
                if (MyGdxGame.feelings.get(n).isOpened()) continue;
                
                if (isFormulaRight(stringMassive, MyGdxGame.feelings.get(n))) {
                    ScreensUtils.newFeeling = MyGdxGame.feelings.get(n);
                    rareElementCheck(ScreensUtils.newFeeling);
                    setRealPosition(ScreensUtils.newFeeling);
                    ScreensUtils.stageNumber = 1;
                    return true;
                }
            }
            return false;
        }
        return true;
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

    private static void rareElementCheck(Feeling f) {
        if (!f.getRare().equals("Rare")) return;
        SaveUtils.deleteOddPolarity(f);
    }

    private static void choiceRareElementCheck() {
            List<Feeling> choiceFeelings = new ArrayList<>();
            for (Feeling f:MyGdxGame.feelings) {
                if (f.getChoiceFeeling() != null) {
                    choiceFeelings.add(f);
                }
            }

        String[] m1 = new String[ProgressData.getGapsCount()];
        ArrayList<Feeling> feelingsArray = new ArrayList<>(MyGdxGame.startProducts.values());
        Collections.sort(feelingsArray, (feeling, t1) -> {
            return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });
        for (int i = 0; i < m1.length; i++) {
            m1[i] = feelingsArray.get(i).getName();
        }

        String[] m2 = new String[ProgressData.getGapsCount()];
        feelingsArray = new ArrayList<>(choiceFeelings.get(0).getFormula());
        feelingsArray.remove(choiceFeelings.get(0).getChoiceFeeling());
        Collections.sort(feelingsArray, (feeling, t1) -> {
            return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });
        for (int i = 0; i < m2.length; i++) {
            m2[i] = feelingsArray.get(i).getName();
        }

        if (Arrays.equals(m1,m2)) {
            GameScreen.choiceMenu = true;
        }

    }

    private static void setRealPosition(Feeling newF) {
        int buffer;
        float buffer2;
        for (Feeling feeling : MyGdxGame.feelings) {
            if (!feeling.isOpened()) {
                if (feeling == newF) return;
                buffer = feeling.getNumber();
                feeling.setNumber(newF.getNumber());
                newF.setNumber(buffer);

                SaveUtils.changePlaces(newF,feeling);

                buffer = feeling.ABSOLUTE_START_X;
                feeling.ABSOLUTE_START_X = newF.ABSOLUTE_START_X;
                newF.ABSOLUTE_START_X = buffer;

                buffer = feeling.ABSOLUTE_START_Y;
                feeling.ABSOLUTE_START_Y = newF.ABSOLUTE_START_Y;
                newF.ABSOLUTE_START_Y = buffer;

                buffer2 = feeling.lockX;
                feeling.lockX = newF.lockX;
                newF.lockX= buffer2;

                buffer2 = feeling.lockY;
                feeling.lockY= newF.lockY;
                newF.lockY = buffer2;
                break;
            }
        }

        sort();
    }
    
    private static void sort() {
        ArrayList<Feeling> toSort = new ArrayList<>(MyGdxGame.feelings);
        Collections.sort(toSort, (feeling, t1) -> feeling.getNumber() > t1.getNumber()? 1:-1);
        MyGdxGame.feelings = toSort;
    }
}
