package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.mygdx.game.logic.Feeling;
import java.util.ArrayList;

public final class SaveUtils {
    private static Preferences prefs = Gdx.app.getPreferences("Elements");
    public static Texture atlas;

    public static void createProgress() {
    /*/
        // Запись элемента: "имя редкость доступность рецепт"
        // рецепт - номера составляюших элементов через нижнее подчёркивание
        // иконки чувств расположены в том же порядке в самом атласе
        //
        // Важные правила:
        // 1) номера элементов в формуле не могут быть больше чем номер самого элемента
        // 2) номера в формуле должны быть расположены строго в порядке возрастания
    /*/
        prefs.putString("1","test1 Legendary true 0");
        prefs.putString("2","test2 Rare true 0");
        prefs.putString("3","test3 Common true 0");
        prefs.putString("4","test4 Legendary true 0");
        prefs.putString("5","test5 Rare true 0");
        prefs.putString("6","test6 Common false 1_2");
        prefs.putString("7","test7 Legendary false 3_5");
        prefs.putString("8","test8 Rare false 4_6");
        prefs.putString("9","test1 Legendary false 0");
        prefs.putString("10","test2 Rare false 0");
        prefs.putString("11","test3 Common false 0");
        prefs.putString("12","test4 Legendary false 0");
        prefs.putString("13","test5 Rare false 0");
        prefs.putString("14","test1 Legendary false 0");
        prefs.putString("15","test2 Rare false 0");
        prefs.putString("16","test3 Common false 0");
        prefs.putString("17","test4 Legendary false 0");
        prefs.putString("18","test5 Rare false 0");
        prefs.putString("19","test1 Legendary false 0");



    /*/
        // Создание прогресса с уровнем игрока, количеством ячеек, фоном и
        // опытом нужным для следующего уровня
        // current experience - текущий опыт
    /*/
        prefs.putString("Level 1","1 2 first.bmp 50");
        prefs.putInteger("current level",1);
        prefs.putInteger("current experience", 0);
        prefs.flush();

        ProgressData.setCurrentLevel(1);
        ProgressData.setCurrentExperience(0);
        ProgressData.setBackgroundName("first.bmp");
        ProgressData.setGapsCount(2);
        ProgressData.setToReachNextLevel(50);
    }

    public static boolean isProgressCreated() {
        return prefs.contains("current level");
    }

    public static void open(Feeling feeling) {
        String key = String.valueOf(feeling.getNumber());
        String element = prefs.getString(key,"");
        prefs.remove(key);
        element = element.replaceFirst("false","true");
        prefs.putString(key,element);
        prefs.flush();
    }

    public static ArrayList<Feeling> loadLibrary() {
        TextureRegion[][] tmp;
        final int START_Y = 375, START_X = 30, ADD_Y = 105, ADD_X = 105;
        final int ATLAS_START_X = 50, ATLAS_START_Y = 330, ATLAS_ADD_X = 150, ATLAS_ADD_Y = 150;
        ArrayList<Feeling> feelings = new ArrayList<Feeling>();
        ArrayList<Feeling> formulaList;
        String[] massiveParts,formula;
        boolean isOpened;
        String element, key;
        int x = START_X,y = START_Y;
        int x1 = ATLAS_START_X, y1 = ATLAS_START_Y;

        tmp = TextureRegion.split(atlas,75, 75);
        feelings = new ArrayList<Feeling>();
        int a = 0, b = 0;
        for (int i = 1;i <= 20; i++) {
            formulaList = new ArrayList<Feeling>();
            key = String.valueOf(i);
            element = prefs.getString(key,"");
            if (!element.isEmpty()) {
                // разбиваем строку на элементы
                massiveParts = element.split(" ");
                // проверяем доступность элемента
                isOpened = Boolean.parseBoolean(massiveParts[2]);
                // достаём формулу по номерам
                formula = massiveParts[3].split("_");
                // заполняем формулу полноценными Feeling
                if(!formula[0].equals("0")) {
                    for (String s : formula) {
                        formulaList.add(feelings.get(Integer.parseInt(s) - 1));
                    }
                } else formulaList = null;
                feelings.add(new Feeling(Integer.parseInt(key),
                        massiveParts[0],
                        massiveParts[1],
                        isOpened,
                        formulaList,
                        tmp[a][b],
                        x,y,
                        x1,y1
                ));

                x1 += ATLAS_ADD_X;
                if (i%5 == 0) { y1 -= ATLAS_ADD_Y; x1 = ATLAS_START_X; }

                x += ADD_X;
                if(i%3 == 0) { y -= ADD_Y; x = START_X; }

                if (b == 2) { a++; b = -1; }
                // TODO: убрать только этот цикл после добавления всех элементов
                if (a == 3) { a = 0; b = -1; }
                b++;
            }
        }
        return feelings;
    }

    public static void loadProgress() {
        ProgressData.setCurrentLevel(prefs.getInteger("current level"));
        ProgressData.setCurrentExperience(prefs.getInteger("current experience"));
        ProgressData.setBackgroundName("first.bmp");
        ProgressData.setGapsCount(2);
        ProgressData.setToReachNextLevel(50);
    }
}
