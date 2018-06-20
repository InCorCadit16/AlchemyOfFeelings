package com.mygdx.game.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.ScreensUtils;

import java.util.Arrays;

public final class ReactionUtils {

    // TODO: исправить баг с первой ячейкой
    public static void checkGaps (Feeling feeling) {
        if (!MyGdxGame.startProducts.containsKey(1)) {
            if (isNearLeftTop(feeling,ScreensUtils.xPositions.get(0),ScreensUtils.yPositions.get(0))) {
                feeling.getPosition().set(ScreensUtils.xPositions.get(0),ScreensUtils.yPositions.get(0));
                MyGdxGame.startProducts.put(1, feeling);
                feeling.setBenchPosition(1);
                return;
            }
        }

        if (!MyGdxGame.startProducts.containsKey(2)) {
            if (isNearRightTop(feeling,ScreensUtils.xPositions.get(1),ScreensUtils.yPositions.get(1))) {
                feeling.getPosition().set(ScreensUtils.xPositions.get(1),ScreensUtils.yPositions.get(1));
                MyGdxGame.startProducts.put(2,feeling);
                feeling.setBenchPosition(2);
            }
        }
    }

    private static boolean isNearLeftTop(Feeling feeling, int x, int y) {
        Vector2 place = feeling.getCurrentPosition();
        return place.x >= x - 60 && place.x <= x + 40 && place.y <= y + 60 && place.y >= y - 60;
    }

    private static boolean isNearRightTop(Feeling feeling, int x, int y) {
        Vector2 place = feeling.getCurrentPosition();
        return place.x >= x - 45 && place.x <= x + 60 && place.y <= y + 60 && place.y >= y - 60;
    }

    public static void checkForReaction() {
        if (MyGdxGame.startProducts.size() == ProgressData.getGapsCount()) {
            int[] indexMassive = new int[ProgressData.getGapsCount()];
            Feeling[] feelingMassive = new Feeling[ProgressData.getGapsCount()];
            MyGdxGame.startProducts.values().toArray(feelingMassive);
            for (int i = 0; i < indexMassive.length; i++) {
                indexMassive[i] = feelingMassive[i].getNumber();
            }
            Arrays.sort(indexMassive);
            for (int n = indexMassive[indexMassive.length - 1]; n < MyGdxGame.feelings.size(); n++) {
                if (!MyGdxGame.feelings.get(n).isOpened())
                    if (isFormulaRight(indexMassive, MyGdxGame.feelings.get(n))) {
                        ScreensUtils.newFeeling = MyGdxGame.feelings.get(n);
                        ScreensUtils.stageOne = true;
                        return;
                    }
            }
            //TODO:написать что реакция невозможна
        }
    }

    private static boolean isFormulaRight(int[] givenFormula, Feeling feeling) {
        if (feeling.getFormula() == null) return false;
        else {
            int[] realFormula = new int[givenFormula.length];
            for (int i = 0; i < realFormula.length; i++) {
                realFormula[i] = feeling.getFormula().get(i).getNumber();
            }
            return Arrays.equals(givenFormula,realFormula);
        }
    }
}
