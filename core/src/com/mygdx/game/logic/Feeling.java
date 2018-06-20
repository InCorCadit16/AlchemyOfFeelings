package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.view.GameScreen;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.ScreensUtils;

import java.util.ArrayList;
import java.util.Arrays;


public class Feeling extends Actor {
    public static final int WIDTH = 60, HEIGHT = 60, ATLAS_WIDTH = 100, ATLAS_HEIGHT = 100;
    public final int ABSOLUTE_START_X, ABSOLUTE_START_Y, ATLAS_X, ATLAS_Y;
    public float lockX, lockY;
    private String name, rare;
    private int number, benchPosition = 0;
    private Boolean isOpened, isMoving = false;
    private ArrayList<Feeling> formula;
    private TextureRegion picture;
    private Vector2 position, currentPosition, direction, bufferVector;

    Feeling(int number, String name, String rare,  boolean isOpened,
                   ArrayList<Feeling> formula, TextureRegion picture,int x,int y, int atals_x, int atlas_y) {
        this.number = number;
        this.name = name;
        this.rare = rare;
        this.isOpened = isOpened;
        this.formula = formula;
        this.picture = picture;
        lockX = ABSOLUTE_START_X = x;
        lockY = ABSOLUTE_START_Y = y;
        ATLAS_X = atals_x;
        ATLAS_Y = atlas_y;
        position = new Vector2(x,y);
        currentPosition = position.cpy();
        setListeners();
    }

    private void setListeners() {
        setBounds(currentPosition.x, currentPosition.y,
                picture.getRegionWidth(), picture.getRegionHeight());
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (getTapCount() > 1) {
                    // Переходим в библиотеку
                } else {
                    // Открываем дополнительную инфу
                }
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer > 0) return false;
                Gdx.input.vibrate(50);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                // Переводим в графическую систему координат и двумерный вектор
                Vector3 camera = new Vector3(getStage().getCamera().
                        unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
                Vector2 v = new Vector2(camera.x, camera.y);

                position.set(lockX, lockY);
                if (!(MyGdxGame.startProducts.remove(Feeling.this.benchPosition) == null)) benchPosition = 0;

                toFront();

                v.x -= 37.5;
                v.y -= 37.5;

                if (v.x + 75 > MyGdxGame.VIRTUAL_WIDTH) v.x = MyGdxGame.VIRTUAL_WIDTH - 75;
                if (v.y + 75 > MyGdxGame.VIRTUAL_HEIGHT) v.y = MyGdxGame.VIRTUAL_HEIGHT - 75;
                if (v.x < 0) v.x = 0;
                if (v.y < 0) v.y = 0;

                // ТОЛЬКО SET
                currentPosition.set(v);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (currentPosition.x != position.x && currentPosition.y != position.y) {
                    isMoving = true;

                    ReactionUtils.checkGaps(Feeling.this);

                    direction = position.cpy().sub(currentPosition.cpy()).nor();
                    bufferVector = new Vector2(Math.abs(currentPosition.x - position.x), Math.abs(currentPosition.y - position.y));
                    Feeling.this.setTouchable(Touchable.disabled);
                    Gdx.input.setInputProcessor(GameScreen.multiplexer);
                }
            }

        });

    }

    public String getName() {
        return name;
    }

    public String getRare() {
        return rare;
    }

    public int getNumber() {
        return number;
    }

    public Boolean isOpened() {
        return isOpened;
    }

    public ArrayList<Feeling> getFormula() {
        return formula;
    }

    public TextureRegion getPicture() {
        return picture;
    }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setBufferVector(Vector2 bufferVector) {
        direction = position.cpy().sub(currentPosition.cpy()).nor();
        this.bufferVector = new Vector2(bufferVector);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(Boolean moving) {
        isMoving = moving;
    }

    public int getBenchPosition() {
        return benchPosition;
    }

    public void setBenchPosition(int benchPosition) {
        this.benchPosition = benchPosition;
    }



    // Метод движения
    public void update() {
        final float SPEED = 4f;
        Vector2 speed = direction.cpy();

        if ((position.x > currentPosition.x - 3 && position.x <  currentPosition.x + 3) &&
                (position.y > currentPosition.y - 3 && position.y <  currentPosition.y + 3)) {
            currentPosition.set(position);
            direction.setZero();
            bufferVector.setZero();
            isMoving = false;
            clearListeners();
            setListeners();
            ReactionUtils.checkForReaction();
        } else if ((position.x > currentPosition.x - 3 && position.x <  currentPosition.x + 3)) {
            direction.x = 0;
            currentPosition.add(speed.scl(SPEED));
        } else if (position.y > currentPosition.y - 3 && position.y <  currentPosition.y + 3) {
            direction.y = 0;
            currentPosition.add(speed.scl(SPEED));
        } else {
            currentPosition.add(speed.scl(SPEED));
        }
    }

    // Открыть чувство
    public void open() {
        isOpened = true;
        SaveUtils.open(this);
    }

    // Скрыть для выполнения реакции
    public void hide() {
        isOpened = false;
    }

    public void unhide() {
        isOpened = true;
    }

    // Методы класса Actor
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch,parentAlpha);
        batch.draw(picture,currentPosition.x,currentPosition.y,WIDTH,HEIGHT);
    }
}

