package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.view.GameScreen;
import com.mygdx.game.MyGdxGame;

import java.util.ArrayList;
import java.util.Map;


public class Feeling extends Actor  {
    public static final int WIDTH = 65, HEIGHT = 65;
    public int ABSOLUTE_START_X, ABSOLUTE_START_Y;
    public float lockX, lockY;
    private String name, rare, description, breathDescription;
    private int number, benchPosition = 0;
    private int specialNumber;
    private Feeling linkedFeeling, choiceFeeling;
    private Boolean isOpened, isMoving = false, isTaken = false, positivePolarity;
    private ArrayList<Feeling> formula;
    private Texture picture;
    private Texture goal_picture;
    private Vector2 position, currentPosition, direction, bufferVector;
    private ShapeRenderer shapeRenderer;
    private Group group;
    public int level;

    Feeling(int number, String name, String rare,  boolean isOpened, Texture picture, Texture goal_picture,int x,int y) {
        this.number = number;
        this.name = name;
        this.rare = rare;
        this.isOpened = isOpened;
        this.picture = picture;
        this.goal_picture = goal_picture;
        lockX = ABSOLUTE_START_X = x;
        lockY = ABSOLUTE_START_Y = y;
        position = new Vector2(x,y);
        currentPosition = position.cpy();
        setListeners();
    }



    // Сеттеры
    private void setListeners() {
        setBounds(currentPosition.x, currentPosition.y,
                picture.getWidth(), picture.getHeight());
        setTouchable(Touchable.enabled);

        addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer > 0) return false;
                isTaken = true;
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
                    isTaken = false;
                    ReactionUtils.checkGaps(Feeling.this);

                    direction = position.cpy().sub(currentPosition.cpy()).nor();
                    bufferVector = new Vector2(Math.abs(currentPosition.x - position.x), Math.abs(currentPosition.y - position.y));
                    Feeling.this.setTouchable(Touchable.disabled);
                }
                Gdx.input.setInputProcessor(GameScreen.standartMultiplexer);
            }

        });

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public void setNumber(int number) {this.number = number; }

    public void setDescription(String desc) {description = desc;}

    public void setBreathDescription(String desc) {breathDescription = desc;}

    public void setFormula(ArrayList<Feeling> formula) {
        this.formula = formula;
    }

    public void setBufferVector(Vector2 bufferVector) {
        direction = position.cpy().sub(currentPosition.cpy()).nor();
        this.bufferVector = new Vector2(bufferVector);
    }

    public void setMoving(Boolean moving) {
        isMoving = moving;
    }

    public void setIsTaken(boolean isTaken1) { isTaken = isTaken1;}

    public void setSpecialNumber(int specialNumber) {
        this.specialNumber = specialNumber;
    }

    public void setBenchPosition(int benchPosition) {
        this.benchPosition = benchPosition;
    }

    public void setLinkedFeeling(Feeling feeling) { linkedFeeling = feeling; }

    public void setChoiceFeeling(Feeling feeling) {choiceFeeling = feeling; }

    public void setPositivePolarity(boolean polarity) {positivePolarity = polarity;}

    public void setGroup(Group g) { group = g; }


    // Геттеры
    public String getRare() {
        return rare;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription() { return description; }

    public String getBreathDescription() { return breathDescription; }

    public ArrayList<Feeling> getFormula() {
        return formula;
    }

    public Texture getPicture() {
        return picture;
    }

    public Texture getGoal_picture() {return goal_picture; }

    public Vector2 getCurrentPosition() {
        return currentPosition;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getBenchPosition() {
        return benchPosition;
    }

    public Integer getSpecialNumber() {
        return specialNumber;
    }

    public Feeling getLinkedFeeling() {return linkedFeeling;}

    public Feeling getChoiceFeeling() {return choiceFeeling;}

    public Group getGroup() { return group; }

    // Методы поиска
    public static String findBreathDescription(String name) {
        String breathDescription = null;
        for (Feeling f: MyGdxGame.feelings) {
            if (name.equals(f.getName()))
                breathDescription = f.getBreathDescription();
        }
        return breathDescription;
    }

    public static Feeling findFeeling(String name) {
        for (Feeling f: MyGdxGame.feelings) {
            if (name.equals(f.getName()))
                return f;
        }
        return null;
    }

    // is-методы
    public boolean isMoving() {
        return isMoving;
    }

    public Boolean isOpened() {
        return isOpened;
    }

    public boolean isTaken() {return isTaken;}

    public boolean isPositivePolarity() {return positivePolarity;}


    // Метод движения
    public void update() {
        final float SPEED = 5f;
        Vector2 speed = direction.cpy();

        if ((position.x > currentPosition.x - 3 && position.x <  currentPosition.x + 3) &&
                (position.y > currentPosition.y - 3 && position.y <  currentPosition.y + 3)) {
            currentPosition.set(position);
            direction.setZero();
            bufferVector.setZero();
            isMoving = false;
            clearListeners();
            setListeners();
            if (!ReactionUtils.checkForReaction()) notMatch();
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

    private void notMatch() {
        Gdx.input.vibrate(200);
        Feeling f;
        for (Map.Entry<Integer,Feeling> pair: MyGdxGame.startProducts.entrySet()) {
            f = pair.getValue();
            f.benchPosition = 0;
            f.isMoving = true;
            f.isTaken = false;
            f.position.x = f.lockX;
            f.position.y = f.lockY;
            f.direction = f.position.cpy().sub(f.currentPosition.cpy()).nor();
            f.bufferVector = new Vector2(Math.abs(f.currentPosition.x - f.position.x), Math.abs(f.currentPosition.y - f.position.y));
            f.setTouchable(Touchable.disabled);
            if (f.level != GameScreen.showedLevel) {
                f.currentPosition.x = f.position.x;
                f.currentPosition.y = f.position.y;
                f.remove();
            }
        }
        MyGdxGame.startProducts.clear();
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
        batch.end();
        rectangleShadow(batch);
        batch.begin();
        if ((isTaken | isMoving | (currentPosition.x > 0 & currentPosition.x < 315) | MyGdxGame.startProducts.containsValue(this) | level == GameScreen.showedLevel) & isOpened)
            batch.draw(picture, currentPosition.x, currentPosition.y, WIDTH, HEIGHT);
    }

    private void drawShadow(Camera camera) {
        if (isTaken | isMoving) {
            float x1 = currentPosition.x,
                    y1 = currentPosition.y,
                    x2 = currentPosition.x,
                    y2 = currentPosition.y,
                    x3 = currentPosition.x,
                    y3 = currentPosition.y,
                    x4 = currentPosition.x,
                    y4 = currentPosition.y,
                    x5 = currentPosition.x,
                    y5 = currentPosition.y,
                    x6 = currentPosition.x,
                    y6 = currentPosition.y;
            Color c1 = new Color(Color.BLACK);
            Color c2 = new Color(Color.RED);
            Color c3 = new Color(Color.BLUE);

            if (currentPosition.x <= 525) {
                if (currentPosition.y > 235) {
                    x4 += WIDTH;

                    x2 = x1 + 50;
                    y2 = y1 + (currentPosition.y - 275);
                    x3 = x1 - (565 - currentPosition.x);
                    y3 = y1 + (currentPosition.y - 275);

                    x5 = x4 + 20;
                    y5 = y4 + (currentPosition.y - 275);
                    x6 = x4 - (525 - currentPosition.x);
                    y6 = y4 + (currentPosition.y - 275);
                } else {
                    x1 += WIDTH;
                    y4 += HEIGHT;
                }

            } else {
                if (currentPosition.y > 235) {
                    y1 += HEIGHT;
                    x4 += WIDTH;
                } else {
                    x4 += WIDTH;
                    y4 += HEIGHT;
                }
            }

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer = new ShapeRenderer();
            camera.update();
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.triangle(x1,y1,x2,y2,x3,y3,c1,c2,c3);
            shapeRenderer.triangle(x4,y4,x5,y5,x6,y6,c1,c2,c3);
            shapeRenderer.end();
        }
    }

    private void rectangleShadow(Batch batch) {
        if (isTaken | isMoving) {
            float x = currentPosition.x,
                    y = currentPosition.y,
                    width = WIDTH,
                    height = HEIGHT;
            Color c1 = new Color(Color.BLACK);
            Color c2 = new Color(Color.BLACK);
            Color c3 = new Color(Color.BLACK);
            Color c4 = new Color(Color.BLACK);
            Color buff;

            c1.a = 0f;
            c2.a = 0f;
            c3.a = 0f;
            c4.a = 0f;

            if (currentPosition.x <= 525) {
                x = currentPosition.x - (525 - currentPosition.x);
                width += (525 - currentPosition.x);
            }
            else {
                width = 65 + (currentPosition.x - 525);
            }

            if (currentPosition.y < 235) {
                y = currentPosition.y - (235 - currentPosition.y);
                height += (235 - currentPosition.y);
            }
            else {
                height = 65 + (currentPosition.y - 235);
            }

            if (x > 525)
                buff = y > 235? c1:c4;
            else
                buff = y > 235?c2:c3;
            buff.set(Color.BLACK);
            buff.a = 1f;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(x, y, width, height,c1,c2, c3,c4);
            shapeRenderer.end();
        }
    }
}

