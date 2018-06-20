package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.ProgressData;

import java.util.ArrayList;


public class GameScreen implements Screen  {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private boolean toMainScreen, goalsMenu;
    private ImageButton arrow_left, goals_button, atlas_button;
    private int counter = 0;
    private Actor goals_background;
    Vector3 vector;

    // Всё что связано с передвижением списка по горизонтали
    private float velocityY = 0, scrollBarY = 400;
    private int yStartPoint;
    private int coff = 0;
    private boolean isDragInArea;
    public static InputMultiplexer multiplexer;


    GameScreen(final MyGdxGame gam) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT, camera));

        // Установить позиции ячеек реакции
        setGapsPositions();

        // Для сохранения цветов элементов при изменении контрастности (производится при открытии нового элемента)
        game.batch = (SpriteBatch) stage.getBatch();

        // Белый прямоугольник. Стоит за элементами в ячейках реакции (при открытии нового элемента)
        Pixmap elementPix = new Pixmap(60,60, Pixmap.Format.RGBA8888);
        elementPix.setColor(Color.WHITE);
        elementPix.fillRectangle(0,0,60,60);
        ScreensUtils.elementBackground = new Texture(elementPix);

        toMainScreen = false;

        // Кнопки
        arrow_left = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        goals_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.goals_button)));
        atlas_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.atlas_button)));
        goals_background = new Image(game.goalsBackground);

        // Настройки кнопок и меню
        arrow_left.setBounds(745,425,50,50);
        stage.addActor(arrow_left);

        goals_button.setBounds(495, 360,60,60);
        stage.addActor(goals_button);

        stage.addActor(atlas_button);
        atlas_button.setBounds(725, 15,50,50);


        goals_background.setBounds(0,0,800,480);
        stage.addActor(goals_background);
        goals_background.setVisible(false);

        // Listener'ы кнопок
        arrow_left.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                toMainScreen = true;
            }
        });

        goals_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                goalsMenu = true;
            }
        });

        atlas_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                game.setScreen(new AtlasScreen(game, true));
            }
        });


        for(Feeling feeling: MyGdxGame.feelings) {
            if (feeling.isOpened()) {
                stage.addActor(feeling);
            }
        }


        GestureDetector longPress = new GestureDetector(20f,0.2f,0.3f,0.15f, new GestureDetector.GestureAdapter() {
            @Override
            public boolean longPress(float x, float y) {
                if (!stage.touchDown((int) x, (int) y, 0, 0)) return false;
                isDragInArea = false;
                velocityY = 0;
                return super.longPress(x, y);
            }
        });


        multiplexer = new InputMultiplexer(longPress);

        multiplexer.addProcessor(stage);

        GestureDetector scrollMove = new GestureDetector(20f,0.2f,0.4f,0.15f, new GestureDetector.GestureAdapter()) {
            // Всё что связано с передвижением списка по горизонтали


            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                if (pointer > 0) return false;
                Vector3 camera = new Vector3(stage.getCamera().
                        unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
                Vector2 touch = new Vector2(camera.x, camera.y);

                isDragInArea = touch.x < 315;

                coff = MyGdxGame.feelings.size()/3;
                coff = MyGdxGame.feelings.size()%3 != 0 ? ++coff : coff;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragInArea) {
                    // Переводим в графическую систему координат и двумерный вектор
                    Vector3 camera = new Vector3(stage.getCamera().
                            unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
                    Vector2 v = new Vector2(camera.x, camera.y);

                    if (isOnLimit(v, yStartPoint)) return false;

                    moveIfNeed(v);

                    if (isOnLimit(v, yStartPoint)) return false;

                    velocityY = v.y;
                }
                return true;

            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                velocityY = yStartPoint = 0;
                isDragInArea = false;
                return true;
            }

        };

        multiplexer.addProcessor(scrollMove);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        ScreensUtils.gameScreenRender(game,camera,scrollBarY);

        if (ScreensUtils.newFeeling != null) {
            scrollBarY = ScreensUtils.newElementRender(game,stage,scrollBarY,arrow_left,goals_button,atlas_button);
        }

        if (toMainScreen) {
            counter = ScreensUtils.fromGameToMain(game,camera,counter);
            if (counter == 100) {
                game.batch = new SpriteBatch();
                game.setScreen(new MainMenuScreen(game));
                this.dispose();
            }
        }

        if (goalsMenu) {
            game.batch.begin();
            goals_background.setVisible(true);
            goals_background.toFront();
            game.batch.end();

            if (Gdx.input.isTouched()) {
                vector = new Vector3(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
                if (vector.y < 135) {
                    goals_background.toBack();
                    goals_background.setVisible(false);
                    goalsMenu = false;
                }
            }
        }


        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        for (Feeling feeling: MyGdxGame.feelings) {
            feeling.lockX = feeling.ABSOLUTE_START_X;
            feeling.lockY = feeling.ABSOLUTE_START_Y;
            if (!MyGdxGame.startProducts.containsValue(feeling) && feeling.isOpened()) {
                feeling.getPosition().set(feeling.ABSOLUTE_START_X, feeling.ABSOLUTE_START_Y);
                feeling.getCurrentPosition().set(feeling.getPosition().cpy());
            }
        }
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    // Данный метод проверяет если достигнут низ или верх таблицы в процессе скролинг
    // Вызывается 2 раза в GameScreen>create()>new GestureAdapter()>touchDragged()
    private boolean isOnLimit(Vector2 v, int yStartPoint) {
        if (((scrollBarY >= 400 && velocityY > v.y) || (scrollBarY <= 0 && velocityY < v.y)) && velocityY != 0 ) {
            boolean down = scrollBarY <= 0;
            scrollBarY = 400;
            if (down) { yStartPoint = coff * 105 - 450; scrollBarY = 0;}
            for (Feeling feeling : MyGdxGame.feelings) {
                feeling.lockY = feeling.ABSOLUTE_START_Y + yStartPoint;
                if (feeling.isOpened()) {
                    if (feeling.getBenchPosition() == 0) {
                        feeling.getPosition().y = feeling.ABSOLUTE_START_Y + yStartPoint;
                        feeling.setBounds(feeling.getPosition().x, feeling.getPosition().y, 75, 75);
                        if(!feeling.isMoving())
                            feeling.getCurrentPosition().y = feeling.ABSOLUTE_START_Y + yStartPoint;
                        else feeling.setBufferVector(new Vector2(
                                Math.abs(feeling.getCurrentPosition().x - feeling.getPosition().x),
                                Math.abs(feeling.getCurrentPosition().y - feeling.getPosition().y)));
                    }
                }
            }
            return true;
        } else return false;
    }

    // Данный метод высчитывает расстояние и производит движение элементов списка, при необходимости
    // Вызывается 1 раз в GameScreen>create()>new GestureAdapter()>touchDragged()
    private void moveIfNeed(Vector2 v) {
        if (velocityY != 0) {
            for (Feeling feeling : MyGdxGame.feelings) {
                feeling.lockY -= velocityY - v.y;
                if (feeling.isOpened()) {
                    if (feeling.getBenchPosition() == 0) {
                        feeling.getCurrentPosition().y -= velocityY - v.y;
                        feeling.getPosition().y -= velocityY - v.y;
                        feeling.setBounds(feeling.getCurrentPosition().x,
                                feeling.getCurrentPosition().y,
                                Feeling.WIDTH,
                                Feeling.HEIGHT);
                    }
                }
            }
            scrollBarY += ((velocityY - v.y)/((float) (coff * 105 - 450)/400)) ;
        }
    }

    // Данный метод устанавливает позиции ячеек для реакции
    // Вызывается в OnCreate()
    private void setGapsPositions() {
        ScreensUtils.xPositions = new ArrayList<>();
        ScreensUtils.yPositions = new ArrayList<>();
        switch (ProgressData.getGapsCount()) {
            case 2:
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(255);
                break;
            case 3:
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(495);
                ScreensUtils.yPositions.add(170);
                break;
            case 4:
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(255);
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(170);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(170);
                break;
            case 5:
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(495);
                ScreensUtils.yPositions.add(120);
                break;
            case 6:
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(280);
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(200);
                ScreensUtils.xPositions.add(455);
                ScreensUtils.yPositions.add(120);
                ScreensUtils.xPositions.add(540);
                ScreensUtils.yPositions.add(120);
        }

    }
}