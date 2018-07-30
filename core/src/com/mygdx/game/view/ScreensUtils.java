package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.ProgressData;

import java.util.ArrayList;

import static com.mygdx.game.view.GameScreen.showedLevel;
import static com.mygdx.game.view.GameScreen.standartMultiplexer;

public final class ScreensUtils {
    // Для реакции
    public static Feeling newFeeling = null;
    private static float screenAlpha, elementsAlpha, backgroundAlpha, newElementAlpha, move, newElementDecrease;
    public static Texture elementBackground;
    public static int stageNumber = 0;
    private static BitmapFont new_element_font = MyGdxGame.setupMinecraftFont();
    static boolean new_lvl = false;

    // Для переходов
    private static float backgroundAddition = 0;

    /* Map'ы, хранящие положение ячеек реакции */
    public static ArrayList<Integer> xPositions;
    public static ArrayList<Integer> yPositions;

    static int fromGameToMain(MyGdxGame game,  OrthographicCamera camera, int counter) {
        if (Gdx.input.getInputProcessor() != null) Gdx.input.setInputProcessor(null);

        game.batch.begin();
        // фон
        game.batch.draw(game.playBackground,counter < 100? (float) (counter * 6.8): 680,0);

        // игровой экран
        game.batch.draw(game.blure,0,0);
        for (int i = 0; i < ProgressData.getGapsCount(); i++)
            game.batch.draw(game.gap,xPositions.get(i),yPositions.get(i),Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.expBar, 405,445,242,20);
        game.batch.draw(game.scrollBar, 315, 400, 5, 80);

        for(int i = 0; i < 15; i++) {
            game.batch.draw(game.locked,
                    MyGdxGame.feelings.get(i).ABSOLUTE_START_X,
                    MyGdxGame.feelings.get(i).ABSOLUTE_START_Y,
                    Feeling.WIDTH,
                    Feeling.HEIGHT);
        }
        game.batch.end();

        if (counter < 100) camera.translate(+8f,0);
        return ++counter;
    }

    static int fromMainToGame(MyGdxGame game, int counter) {
        if (Gdx.input.getInputProcessor() == null) Gdx.input.setInputProcessor(null);
        // Установить позиции ячеек реакции
        if (xPositions == null) setGapsPositions();


        float addition = counter > 20 ? (counter - 20) * 8 : 0;

        backgroundAddition += counter > 20?(float) 1.2 :0;

        game.batch.begin();
        game.batch.draw(game.mainBackground,-120 + backgroundAddition,0);
        for (int i = 0; i < ProgressData.getGapsCount(); i++)
            game.batch.draw(game.gap,xPositions.get(i) - 800 + addition,yPositions.get(i),Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.expBar, -395 + addition,445,242,20);
        game.batch.draw(game.left_arrow,-55 + addition,430,50,40);
        game.batch.draw(game.goals_button,-305 + addition,360,60,60);
        game.batch.draw(game.atlas_button,-75 + addition, 15,50,50);
        game.batch.draw(game.blure, -800 + addition, 0, 315,480);
        game.batch.draw(game.scrollBar, -485 + addition, 400, 5, 80);
        game.batch.end();

        for(int i = 0; i < 15; i++) {
            game.batch.begin();
            game.batch.draw(game.locked,
                    MyGdxGame.feelings.get(i).ABSOLUTE_START_X - 800  + addition,
                       MyGdxGame.feelings.get(i).ABSOLUTE_START_Y,
                       Feeling.WIDTH,
                       Feeling.HEIGHT);
            game.batch.end();
            if (MyGdxGame.feelings.get(i).isOpened()) {
                game.batch.begin();
                game.batch.draw(MyGdxGame.feelings.get(i).getPicture(),
                        MyGdxGame.feelings.get(i).getCurrentPosition().x - 800 + addition,
                           MyGdxGame.feelings.get(i).getCurrentPosition().y,
                           Feeling.WIDTH,
                           Feeling.HEIGHT);
                game.batch.end();
            }
        }

        if (counter >= 119) backgroundAddition = 0;
        return ++counter;
    }

    static void mainMenuRender(MyGdxGame game, OrthographicCamera camera) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.mainBackground,-120,0);
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
        game.batch.draw(game.blure,0,0);
        for (int i = 0; i < ProgressData.getGapsCount(); i++)
            game.batch.draw(game.gap,xPositions.get(i),yPositions.get(i),Feeling.WIDTH,Feeling.HEIGHT);
        game.batch.draw(game.expBar, 405,445,242,20);
        game.batch.draw(game.scrollBar, 315, scrollBarY, 5, 80);

        Feeling feeling;
        for(int i = ProgressData.getStartNumber(); i < ProgressData.getElementsCount();i++) {
            feeling = MyGdxGame.feelings.get(i);
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

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.setColor(1, 1, 1, (float) 0.7);
        game.batch.draw(game.mainBackground, -120, 0);
        int y = 370, x1, y1;
        String text;
        game.font.getData().setScale((float) 0.3,(float) 0.5);
        for (int i = 1; i <= 5; i++) {
            switch (i) {
                case 1: text = "Играть"; x1 = 385; y1 = y + 30; break;
                case 2: text = "Атлас"; x1 = 389; y1 = y + 30; break;
                case 3: text = "Настройки"; x1 = 380; y1 = y + 30; break;
                case 4: text = "Напиши нам"; x1 = 380; y1 = y + 30; break;
                default: text = "Магазин"; x1 = 380; y1 = y + 30; break;
            }

            game.batch.draw(game.main_button, 365, y);
            game.font.draw(game.batch, text, x1,y1);
            y -= 60;

        }
        game.batch.setColor(1, 1, 1, 1);
        game.batch.draw(game.settingsBackground, 300, 190, 200,100);
        game.batch.end();
    }

    static void atlasScreenRender(MyGdxGame game, OrthographicCamera camera, boolean isFromGame) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        if (isFromGame) {
            game.batch.draw(game.playBackground,0,0);
            for (int i = 0; i < ProgressData.getGapsCount(); i++)
                game.batch.draw(game.gap,xPositions.get(i),yPositions.get(i),Feeling.WIDTH,Feeling.HEIGHT);
            game.batch.draw(game.expBar, 405,445,242,20);
            game.batch.draw(game.goals_button,495,360,60,60);
            game.batch.draw(game.atlas_button,725, 15,50,50);
            game.batch.draw(game.blure, 0, 0, 315,480);
            game.batch.draw(game.scrollBar, 315, 400, 5, 80);

            for(int i = 0; i < 15; i++) {
                game.batch.draw(game.locked,
                        MyGdxGame.feelings.get(i).ABSOLUTE_START_X,
                        MyGdxGame.feelings.get(i).ABSOLUTE_START_Y,
                        Feeling.WIDTH,
                        Feeling.HEIGHT);
                if (MyGdxGame.feelings.get(i).isOpened()) {
                    game.batch.draw(MyGdxGame.feelings.get(i).getPicture(),
                            MyGdxGame.feelings.get(i).getCurrentPosition().x,
                            MyGdxGame.feelings.get(i).getCurrentPosition().y,
                            Feeling.WIDTH,
                            Feeling.HEIGHT);
                }
            }
        } else {
            game.batch.draw(game.mainBackground,-120,0);
        }

        game.batch.draw(game.atlasBackground,0,0);
        game.batch.end();
    }

    static void atlasElementScreenRender(MyGdxGame game, OrthographicCamera camera,Feeling f) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.atlasElementBackground,0,0);
        game.batch.draw(f.getPicture(),0,280,200,200);

        game.batch.end();
    }

    static float newElementRender(MyGdxGame game, Stage stage, float scrollBarY, Stage buttonsStage) {
        if (Gdx.input.getInputProcessor() != null) {
            screenAlpha = elementsAlpha = backgroundAlpha = 1;
            newElementAlpha = newElementDecrease = 0;
            move = 0.08f;
            stage.clear();
            buttonsStage.clear();
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

            int addition = newFeeling.getRare().equals("Common")?1:3;
            ProgressData.addToCurrentExperience(addition);
            if (ProgressData.getCurrentExperience() == ProgressData.getToReachNextLevel()) {
                ProgressData.levelUp();
                new_lvl = true;
            }

            Gdx.input.setInputProcessor(null);
        }

        game.batch.begin();
        scrollBarY = creatingNewElement(game, stage, scrollBarY);
        game.batch.end();

        if (standartMultiplexer == Gdx.input.getInputProcessor()) {
            // Отправка нового чувства к своей позиции в списке
            newFeeling.setMoving(true);
            newFeeling.setBufferVector(new Vector2(
                    Math.abs(newFeeling.getCurrentPosition().x - newFeeling.getPosition().x),
                    Math.abs(newFeeling.getCurrentPosition().x - newFeeling.getPosition().x)));
            newFeeling = null;
        }
        return scrollBarY;
    }

    private static float creatingNewElement(MyGdxGame game, Stage stage, float scrollBarY) {

        //Прорисовка отдельных частей интерфейса
        game.batch.draw(game.left_arrow,745,430,50,40);
        game.batch.draw(game.goals_button,495,360,60,60);
        if (GameScreen.chosen_goal != null) {
            game.batch.begin();
            GameScreen.goals_font.draw(game.batch, GameScreen.chosen_goal.getName(),455,350);
            game.batch.draw(GameScreen.chosen_goal.getGoal_picture(),500,365, 50,50);
            game.batch.end();
        }
        game.batch.draw(game.atlas_button,725, 15,50,50);
        new_element_font.getData().setScale((float)0.5);
        if (stageNumber < 5)
            new_element_font.draw(game.batch,"Нажмите на экран чтобы пропустить", 20,20);
        if (Gdx.input.isTouched() && stageNumber < 5) {
            stageNumber = 7;
        }

        switch (stageNumber) {
            case 1: { // Элементы приближаются и светлеют, а экран темнеет (контрастность снижается)
                if (screenAlpha <= 0.5) {
                    // Переход к второй стадии
                    stageNumber++;

                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка кадра
                        game.batch.setColor(1, 1, 1, backgroundAlpha);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;

                        //Смена шкалы опыта
                        game.expBar = new Texture(Gdx.files.internal(
                                "interface/experience_bar/"+ ProgressData.getCurrentExperience() +".bmp"));
                    }
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                } else {
                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка кадра
                        game.batch.setColor(1, 1, 1, backgroundAlpha);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;
                    }
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Уменьшаем контрастность
                    screenAlpha -= 0.002;
                    elementsAlpha -= 0.0022;
                }
            } break;
            case 2: { // Экран начинает светлеть (контрастность растёт). Элементы продолжают сближаться и светлеть
                if (screenAlpha >= 1) {
                    // Переход к третей стадии
                    stageNumber++;
                    // Выравнивание прозрачности экрана
                    screenAlpha = 1;
                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка кадра
                        game.batch.setColor(1, 1, 1, backgroundAlpha);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;
                    }
                    // Обнуление контрастности
                    backgroundAlpha = 0;
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                } else {
                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка
                        game.batch.setColor(1, 1, 1, backgroundAlpha);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;
                    }
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Изменение контрастности
                    screenAlpha += 0.008f;
                    elementsAlpha -= 0.0022f;
                }
            } break;
            case 3: { // Контрастность экрана снижается на белом фоне. Элементы приближаются и светлеют
                if (backgroundAlpha >= 1) {
                    // Переход к четвёртой стадии
                    stageNumber++;
                    // Выравнивание значений контрастности
                    backgroundAlpha = 1;
                    screenAlpha = 0;
                    elementsAlpha = 0;
                    // Прорисовка
                    game.batch.setColor(1, 1, 1, backgroundAlpha);
                    game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                } else {
                    // Прорисовка белого фона
                    game.batch.setColor(1, 1, 1, backgroundAlpha);
                    game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка элементов
                        game.batch.setColor(1, 1, 1, 1);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;
                    }
                    // Присваивание прозрачности
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Иэменение значений контрастности
                    backgroundAlpha += 0.008f;
                    screenAlpha -= 0.008f;
                    elementsAlpha -= 0.0022f;
                }
            } break;
            case 4: { // Белый экран. На нём проявляется новый элемент и происхожит пауза на 2 секунды
                if (newElementAlpha >= 1) {
                    // Переход к пятой стадии
                    stageNumber++;
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
            } break;
            case 5: {  // Белый фон сменяется приглушённым стандартым интерфейсом приложения. Список был поднят наверх
                if (backgroundAlpha <= 0) {
                    // Выравнивание значения контрастности
                    backgroundAlpha = 0;
                    new_element_font.draw(game.batch,"Нажмите на экран чтобы продолжить", 20,20);
                    game.batch.setColor(1, 1, 1, newElementAlpha);
                    game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Переход к шестой стадии, завершающей
                    if (Gdx.input.justTouched()) {
                        stageNumber++;
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
                    screenAlpha += 0.005576f;
                    backgroundAlpha -= 0.01116f;
                }
            } break;
            case 6: { // Экран ждёт касания после которого иконка нового эдемента уменьшится,
                // экран станет более контрастным и элемент станет частью списка актёров сцены
                if (newElementDecrease >= 60) {
                    // Выравнивание значений контрастности
                    newElementDecrease = 60;
                    screenAlpha = 1;
                    // Последняя прорисовка в методе
                    game.batch.setColor(1, 1, 1, newElementAlpha);
                    game.batch.draw(newFeeling.getPicture(), 340 + newElementDecrease / 2, 255 + newElementDecrease / 2,
                            120 - newElementDecrease, 120 - newElementDecrease);
                    // Добавление нового элемента в список
                    newFeeling.getCurrentPosition().x = 340 + newElementDecrease / 2;
                    newFeeling.getCurrentPosition().y = 255 + newElementDecrease / 2;
                    newFeeling.unhide();
                    stage.clear();
                    for (Feeling feeling : MyGdxGame.feelings) {
                        if (feeling.isOpened()) {
                            stage.addActor(feeling);
                        }
                    }
                    // Настройка всех слушателей
                    GameScreen screen = (GameScreen) game.getScreen();
                    screen.setupStandartMultiplexer();
                    screen.setupButtons();
                    screen.setupGoalsMultiplexer();
                    // Возвращение чувствительности экрана
                    Gdx.input.setInputProcessor(standartMultiplexer);
                    // Исходное значение stageNumber
                    stageNumber = 0;
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                } else {
                    game.batch.setColor(1, 1, 1, newElementAlpha);
                    game.batch.draw(newFeeling.getPicture(), 340 + newElementDecrease / 2, 255 + newElementDecrease / 2,
                            120 - newElementDecrease, 120 - newElementDecrease);
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Изменение значений контрастности
                    screenAlpha += 0.008f;
                    newElementDecrease += 1f;
                }
            } break;
            case 7: { // Контрастность экрана снижается на белом фоне. Элементы приближаются и светлеют
                if (backgroundAlpha >= 1) {
                    // Переход к четвёртой стадии
                    stageNumber++;
                    // Выравнивание значений контрастности
                    backgroundAlpha = 1;
                    screenAlpha = 0;
                    elementsAlpha = 0;
                    // Прорисовка
                    game.batch.setColor(1, 1, 1, backgroundAlpha);
                    game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                    // Присвоение прозрачности экрана
                    game.batch.setColor(1, 1, 1, screenAlpha);
                } else {
                    // Прорисовка белого фона
                    game.batch.setColor(1, 1, 1, backgroundAlpha);
                    game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                    for (Feeling feeling : MyGdxGame.startProducts.values()) {
                        // Прорисовка элементов
                        game.batch.setColor(1, 1, 1, 1);
                        game.batch.draw(elementBackground, feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        game.batch.setColor(1, 1, 1, elementsAlpha);
                        game.batch.draw(feeling.getPicture(), feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y, Feeling.WIDTH, Feeling.HEIGHT);
                        // Движение
                        int b = feeling.getBenchPosition();
                        if (b == 3 | b == 5) feeling.getCurrentPosition().y += move;
                        else if (b%2==1) feeling.getCurrentPosition().x += move;
                        else feeling.getCurrentPosition().x -= move;
                    }
                    // Присваивание прозрачности
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Иэменение значений контрастности
                    backgroundAlpha += 0.016f;
                    screenAlpha -= 0.016f;
                    elementsAlpha -= 0.0044f;
                }
            } break;
            case 8: { // Белый экран. На нём проявляется новый элемент
                if (newElementAlpha >= 1) {
                    // Переход к пятой стадии
                    stageNumber = 5;
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

                    //Смена шкалы опыта
                    game.expBar = new Texture(Gdx.files.internal(
                            "interface/experience_bar/"+ ProgressData.getCurrentExperience() +".bmp"));

                    // Возврат списка в изначальное положение
                    for (Feeling feeling : MyGdxGame.feelings) {
                        if (feeling.isOpened()) {
                            stage.addActor(feeling);
                        }
                        feeling.getCurrentPosition().x = feeling.getPosition().x = feeling.lockX = feeling.ABSOLUTE_START_X;
                        feeling.getCurrentPosition().y = feeling.getPosition().y = feeling.lockY = feeling.ABSOLUTE_START_Y;
                    }
                    scrollBarY = (float) 400;
                } else {
                    // Прорисовка
                    game.batch.setColor(1, 1, 1, backgroundAlpha);
                    game.batch.draw(elementBackground, 0, 0, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
                    game.batch.setColor(1, 1, 1, newElementAlpha);
                    game.batch.draw(newFeeling.getPicture(), 340, 255, 120, 120);
                    game.batch.setColor(1, 1, 1, screenAlpha);
                    // Изменение значений
                    newElementAlpha += 0.024f;
                }
            }
        }
        return scrollBarY;
    }

    // Данный метод устанавливает позиции ячеек для реакции
    // Вызывается в OnCreate()
    private static void setGapsPositions() {
        ScreensUtils.xPositions = new ArrayList<>();
        ScreensUtils.yPositions = new ArrayList<>();
        switch (ProgressData.getGapsCount()) {
            case 2:
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(255);
                break;
            case 3:
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(495);
                ScreensUtils.yPositions.add(170);
                break;
            case 4:
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(170);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(170);
                break;
            case 5:
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(495);
                ScreensUtils.yPositions.add(120);
                break;
            case 6:
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(450);
                ScreensUtils.yPositions.add(120);
                ScreensUtils.xPositions.add(535);
                ScreensUtils.yPositions.add(120);
        }

    }
}
