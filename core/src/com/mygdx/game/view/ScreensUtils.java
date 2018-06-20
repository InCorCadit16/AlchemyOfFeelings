package com.mygdx.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mygdx.game.view.GameScreen.multiplexer;

public final class ScreensUtils {
    // Для реакции
    public static Texture a;
    public static Feeling newFeeling = null;
    private static float screenAlpha, elementsAlpha, backgroundAlpha, newElementAlpha, moveX, newElementDecrease;
    public static Texture elementBackground;
    public static boolean stageOne = false;
    private static boolean stageTwo, stageThree, stageFour, stageFive, stageSix;

    /* Map'ы, хранящие положение ячеек реакции */
    public static ArrayList<Integer> xPositions;
    public static ArrayList<Integer> yPositions;

    static int fromGameToMain(MyGdxGame game,  OrthographicCamera camera, int counter) {
        if (Gdx.input.getInputProcessor() == null) Gdx.input.setInputProcessor(null);

        game.batch.begin();
        game.batch.draw(game.mainBackground,800,0);
        game.batch.draw(game.play_texture,1190,370);
        game.batch.draw(game.atlas_texture,1190,310);
        game.batch.draw(game.settings_texture,1190,250);
        game.batch.draw(game.write_texture,1190,190);
        game.batch.draw(game.write_texture,1190,130);
        game.batch.end();

        camera.translate(+8f,0);
        return ++counter;
    }

    static int fromMainToGame(MyGdxGame game, int counter) {
        if (Gdx.input.getInputProcessor() == null) Gdx.input.setInputProcessor(null);

        game.batch.begin();
        game.batch.draw(game.mainBackground,counter,0);
        game.batch.draw(game.gap,-260 + counter * 8,255,Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.gap,-345 + counter * 8,255,Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.scrollBar, -485 + counter * 8, 400, 5, 80);
        game.batch.draw(game.expBar, -420 + counter * 8,445,355,16.2f);
        game.batch.draw(game.left_arrow,-55 + counter * 8,425,50,50);
        game.batch.draw(game.goals_button,-305 + counter * 8,360,60,60);
        game.batch.draw(game.atlas_button,-75 + counter * 8, 15,50,50);
        game.batch.draw(game.blure, -800 + counter * 8, 0, 315,480);
        game.batch.end();

        for(int i = 0; i < 15; i++) {
            game.batch.begin();
            game.batch.draw(game.locked,
                    MyGdxGame.feelings.get(i).ABSOLUTE_START_X - 800  + counter * 8,
                       MyGdxGame.feelings.get(i).ABSOLUTE_START_Y,
                       Feeling.WIDTH,
                       Feeling.HEIGHT);
            game.batch.end();
            if (MyGdxGame.feelings.get(i).isOpened()) {
                game.batch.begin();
                game.batch.draw(MyGdxGame.feelings.get(i).getPicture(),
                        MyGdxGame.feelings.get(i).getCurrentPosition().x - 800 + counter * 8,
                           MyGdxGame.feelings.get(i).getCurrentPosition().y,
                           Feeling.WIDTH,
                           Feeling.HEIGHT);
                game.batch.end();
            }
        }

        return ++counter;
    }

    static void mainMenuRender(MyGdxGame game, OrthographicCamera camera) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.mainBackground,0,0);
        game.batch.end();
    }

    static void gameScreenRender(MyGdxGame game, OrthographicCamera camera, float scrollBarY) {
        // выставляем цвет экрана
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.playBackground,0,0);
        game.batch.draw(game.gap,540,255,Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.gap,455,255,Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.scrollBar, 315, scrollBarY, 5, 80);
        game.batch.draw(game.expBar, 380,445,355,16.2f);

        for(Feeling feeling: MyGdxGame.feelings) {
            game.batch.draw(game.locked, feeling.lockX, feeling.lockY,Feeling.WIDTH,Feeling.HEIGHT);
            if (feeling.isOpened()) {
                if (feeling.isMoving()) {
                    feeling.update();
                }
            }
        }
        game.batch.end();
    }

    static void settingsScreenRender(MyGdxGame game,  OrthographicCamera camera) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.setColor(1, 1, 1, (float) 0.7);
        game.batch.draw(game.mainBackground, 0, 0);
        game.batch.draw(game.play_texture, 390, 370);
        game.batch.draw(game.atlas_texture, 390, 310);
        game.batch.draw(game.settings_texture, 390, 250);
        game.batch.draw(game.write_texture, 390, 190);
        game.batch.draw(game.write_texture, 390, 130);
        game.batch.setColor(1, 1, 1, 1);
        game.batch.draw(game.settingsBackground, 300, 190, 200,100);
        game.batch.end();
    }

    static float newElementRender(MyGdxGame game, Stage stage, float scrollBarY, ImageButton ...buttons) {
        if (Gdx.input.getInputProcessor() != null) {
            screenAlpha = elementsAlpha = backgroundAlpha = 1;
            newElementAlpha = newElementDecrease = 0;
            moveX = 0.122f;
            stage.clear();
            for (Feeling feeling : MyGdxGame.startProducts.values()) {
                feeling.hide();
            }
            for (Feeling feeling : MyGdxGame.feelings) {
                if (feeling.isOpened()) {
                    stage.addActor(feeling);
                }
            }

            newFeeling.open();
            newFeeling.hide();
            Gdx.input.setInputProcessor(null);
        }

        game.batch.begin();
        scrollBarY = creatingNewElement(game, stage, scrollBarY, buttons);
        game.batch.end();

        if (multiplexer == Gdx.input.getInputProcessor()) {
            // Отправка нового чувства к своей позиции в списке
            newFeeling.setMoving(true);
            newFeeling.setBufferVector(new Vector2(
                    Math.abs(newFeeling.getCurrentPosition().x - newFeeling.getPosition().x),
                    Math.abs(newFeeling.getCurrentPosition().x - newFeeling.getPosition().x)));
            newFeeling = null;
        }
        return scrollBarY;
    }

    private static float creatingNewElement(MyGdxGame game, Stage stage, float scrollBarY, ImageButton ...buttons) {

        //Прорисовка отдельных частей интерфейса
        game.batch.draw(game.left_arrow,745,425,50,50);
        game.batch.draw(game.goals_button,495,360,60,60);
        game.batch.draw(game.atlas_button,725, 15,50,50);

        if (stageOne) { // Элементы приближаются и светлеют, а экран темнеет (контрастность снижается)
            if (screenAlpha <= 0.5) {
                // Переход к второй стадии
                stageOne = false;
                stageTwo = true;

                for (Feeling feeling : MyGdxGame.startProducts.values()) {
                    // Прорисовка кадра
                    game.batch.setColor(1,1,1, backgroundAlpha);
                    game.batch.draw(elementBackground,feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    game.batch.setColor(1,1,1, elementsAlpha);
                    game.batch.draw(feeling.getPicture(),feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    // Движение
                    if (feeling.getBenchPosition() == 1)
                        feeling.getCurrentPosition().x += moveX;
                    else if (feeling.getBenchPosition() == 2)
                        feeling.getCurrentPosition().x -= moveX;
                }
                // Присвоение прозрачности экрана
                game.batch.setColor(1,1,1,screenAlpha);
            } else {
                for (Feeling feeling: MyGdxGame.startProducts.values()) {
                    // Прорисовка кадра
                    game.batch.setColor(1,1,1, backgroundAlpha);
                    game.batch.draw(elementBackground,feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    game.batch.setColor(1,1,1, elementsAlpha);
                    game.batch.draw(feeling.getPicture(),feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    // Движение
                    //TODO: заменить систему с каждого элемента на уровни
                    if (feeling.getBenchPosition() == 1)
                        feeling.getCurrentPosition().x += moveX;
                    else if (feeling.getBenchPosition() == 2)
                        feeling.getCurrentPosition().x -= moveX;
                }
                // Присвоение прозрачности экрана
                game.batch.setColor(1,1,1,screenAlpha);
                // Уменьшаем контрастность
                screenAlpha -= 0.002;
                elementsAlpha -= 0.0022;
            }
        }
        else if (stageTwo) { // Экран начинает светлеть (контрастность растёт). Элементы продолжают сближаться и светлеть
            if (screenAlpha >= 1) {
                // Переход к третей стадии
                stageTwo = false;
                stageThree = true;
                // Выравнивание прозрачности экрана
                screenAlpha = 1;
                for (Feeling feeling : MyGdxGame.startProducts.values()) {
                    // Прорисовка кадра
                    game.batch.setColor(1,1,1, backgroundAlpha);
                    game.batch.draw(elementBackground,feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    game.batch.setColor(1,1,1, elementsAlpha);
                    game.batch.draw(feeling.getPicture(),feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    // Движение
                    if (feeling.getBenchPosition() == 1)
                        feeling.getCurrentPosition().x += moveX;
                    else if (feeling.getBenchPosition() == 2)
                        feeling.getCurrentPosition().x -= moveX;
                }
                // Обнуление контрастности
                backgroundAlpha = 0;
                // Присвоение прозрачности экрана
                game.batch.setColor(1,1,1,screenAlpha);
            } else {
                for (Feeling feeling : MyGdxGame.startProducts.values()) {
                    // Прорисовка
                    game.batch.setColor(1,1,1, backgroundAlpha);
                    game.batch.draw(elementBackground,feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    game.batch.setColor(1,1,1, elementsAlpha);
                    game.batch.draw(feeling.getPicture(),feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    // Движение
                    if (feeling.getBenchPosition() == 1)
                        feeling.getCurrentPosition().x += moveX;
                    else if (feeling.getBenchPosition() == 2)
                        feeling.getCurrentPosition().x -= moveX;
                }
                // Присвоение прозрачности экрана
                game.batch.setColor(1,1,1,screenAlpha);
                // Изменение контрастности
                screenAlpha += 0.008f;
                elementsAlpha -= 0.0022f;
            }
        }
        else if (stageThree) { // Контрастность экрана снижается на белом фоне. Элементы приближаются и светлеют
            if (backgroundAlpha >= 1) {
                // Переход к четвёртой стадии
                stageThree = false;
                stageFour = true;
                // Выравнивание значений контрастности
                backgroundAlpha = 1;
                screenAlpha = 0;
                elementsAlpha = 0;
                // Прорисовка
                game.batch.setColor(1,1,1,backgroundAlpha);
                game.batch.draw(elementBackground,0,0,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
                // Присвоение прозрачности экрана
                game.batch.setColor(1,1,1,screenAlpha);
            } else {
                // Прорисовка белого фона
                game.batch.setColor(1,1,1,backgroundAlpha);
                game.batch.draw(elementBackground,0,0, MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
                for (Feeling feeling : MyGdxGame.startProducts.values()) {
                    // Прорисовка элементов
                    game.batch.setColor(1,1,1, 1);
                    game.batch.draw(elementBackground,feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    game.batch.setColor(1,1,1, elementsAlpha);
                    game.batch.draw(feeling.getPicture(),feeling.getCurrentPosition().x,
                            feeling.getCurrentPosition().y,Feeling.WIDTH,Feeling.HEIGHT);
                    // Движение
                    if (feeling.getBenchPosition() == 1)
                        feeling.getCurrentPosition().x += moveX;
                    else if (feeling.getBenchPosition() == 2)
                        feeling.getCurrentPosition().x -= moveX;
                }
                // Присваивание прозрачности
                game.batch.setColor(1,1,1,screenAlpha);
                // Иэменение значений контрастности
                backgroundAlpha += 0.008f;
                screenAlpha -= 0.008f;
                elementsAlpha -= 0.0022f;
            }
        }
        else if (stageFour) { // Белый экран. На нём проявляется новый элемент и происхожит пауза на 2 секунды
            if (newElementAlpha >= 1) {
                // Переход к пятой стадии
                stageFive = true;
                stageFour = false;
                // Выравнивание значения контрастности
                newElementAlpha = 1;
                // Отрисовка
                game.batch.setColor(1, 1, 1, backgroundAlpha);
                game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                // Присвоение прозрачности экрана
                game.batch.setColor(1, 1, 1, screenAlpha);

                // Возврат участников реакции на места
                stage.clear();

                for (Feeling feeling : MyGdxGame.startProducts.values()) {
                    feeling.unhide();
                    feeling.setBenchPosition(0);
                }
                MyGdxGame.startProducts.clear();

                // Возврат списка в изначальное положение
                for (Feeling feeling : MyGdxGame.feelings) {
                    if (feeling.isOpened()) {
                        stage.addActor(feeling);
                    }
                    feeling.getCurrentPosition().x = feeling.getPosition().x = feeling.lockX = feeling.ABSOLUTE_START_X;
                    feeling.getCurrentPosition().y = feeling.getPosition().y = feeling.lockY = feeling.ABSOLUTE_START_Y;
                }
                scrollBarY = (float) 400;

                // Ожидание 2 секунды
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Прорисовка
                game.batch.setColor(1, 1, 1, backgroundAlpha);
                game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                game.batch.setColor(1, 1, 1, screenAlpha);
                // Изменение значений
                newElementAlpha += 0.008f;
            }
        }
        else if (stageFive) {  // Белый фон сменяется приглушённым стандартым интерфейсом приложения. Список был поднят наверх
            if (backgroundAlpha <= 0) {
                // Выравнивание значения контрастности
                backgroundAlpha = 0;
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                // Присвоение прозрачности экрана
                game.batch.setColor(1, 1, 1, screenAlpha);
                // Переход к шестой стадии, завершающей
                if (Gdx.input.justTouched()) {
                    stageFive = false;
                    stageSix = true;
                }
            } else {
                // Прорисковка
                game.batch.setColor(1, 1, 1, backgroundAlpha);
                game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                // Присвоение прозрачности экрана
                game.batch.setColor(1, 1, 1, screenAlpha);
                // Изменение значений
                screenAlpha += 0.002788f;
                backgroundAlpha -= 0.00558f;
            }
        }
        else if (stageSix) { // Экран ждёт касания после которого иконка нового эдемента уменьшится,
            // экран станет более контрастным и элемент станет частью списка актёров сцены
            if (newElementDecrease >= 60) {
                // Выравнивание значений контрастности
                newElementDecrease = 60;
                screenAlpha = 1;
                // Последняя прорисовка в методе
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340 + newElementDecrease/2, 255 + newElementDecrease/2,
                        120 - newElementDecrease, 120 - newElementDecrease);
                // Добавление нового элемента в список
                newFeeling.getCurrentPosition().x = 340 + newElementDecrease/2;
                newFeeling.getCurrentPosition().y = 255 + newElementDecrease/2;
                newFeeling.unhide();
                stage.clear();
                for (Feeling feeling : MyGdxGame.feelings) {
                    if (feeling.isOpened()) {
                        stage.addActor(feeling);
                    }
                }

                for (ImageButton img_btn : buttons) {
                    stage.addActor(img_btn);
                }
                // Возвращение чувствительности экрана
                Gdx.input.setInputProcessor(multiplexer);
                // Исходное значение stageSix
                stageSix = false;
                // Присвоение прозрачности экрана
                game.batch.setColor(1, 1, 1, screenAlpha);
            } else {
                game.batch.setColor(1, 1, 1, newElementAlpha);
                game.batch.draw(newFeeling.getPicture(), 340 + newElementDecrease/2, 255 + newElementDecrease/2,
                        120 - newElementDecrease, 120 - newElementDecrease);
                // Присвоение прозрачности экрана
                game.batch.setColor(1, 1, 1, screenAlpha);
                // Изменение значений контрастности
                screenAlpha += 0.003333f;
                newElementDecrease += 0.5f;
            }
        }
        return scrollBarY;
    }
}
