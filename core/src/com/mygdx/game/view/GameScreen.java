package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Group;
import com.mygdx.game.logic.ProgressData;
import com.mygdx.game.logic.ReactionUtils;
import com.mygdx.game.logic.SaveUtils;


public class GameScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage, buttonsStage;
    private boolean toMainScreen, toAtlasScreen;
    private Vector3 vector;
    private ImageButton goals_button;

    private int counter = 0;

    // Перелистывание уровней
    public static int showedLevel = 1;
    private boolean showNext = false, showPrev = false;

    // Для меню целей
    static Feeling chosen_goal;
    private ImageButton[] goals;
    private int goalsMenuStage = 0;
    private Actor goals_background;
    private Stage goalsStage;
    static BitmapFont goals_font = MyGdxGame.setupMinecraftFont();

    // Всё что связано с передвижением списка по горизонтали
    private float velocityY = 0, scrollBarY = 400;
    private int yStartPoint;
    private int coff = 0;
    private boolean isDragInArea;

    // Принимают любые касания экрана
    public static InputMultiplexer standartMultiplexer;
    private static InputMultiplexer goalsMultiplexer;

    // Отвечают за нажатие на элемент
    private static int independentCounter = 0;
    private static Feeling targetFeeling, doubleClickedFeeling;
    
    // Панель
    private boolean isPanelShown = false;
    private boolean movePanel = false;
    private ImageButton  sin, save, universal;

    GameScreen(final MyGdxGame gam) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        buttonsStage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        goalsStage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));

        game.font.setUseIntegerPositions(true);
        game.font.getData().setScale(0.7f);

        goals_font.setUseIntegerPositions(true);
        goals_font.getData().setScale(0.6f);

        // Для сохранения цветов элементов при изменении контрастности (производится при открытии нового элемента)
        game.batch = (SpriteBatch) stage.getBatch();

        // Белый прямоугольник. Стоит за элементами в ячейках реакции (при открытии нового элемента)
        Pixmap elementPix = new Pixmap(60, 60, Pixmap.Format.RGBA8888);
        elementPix.setColor(Color.WHITE);
        elementPix.fillRectangle(0, 0, 60, 60);
        ScreensUtils.elementBackground = new Texture(elementPix);

        toMainScreen = toAtlasScreen = false;

        for (Feeling feeling : MyGdxGame.feelings) {
            if (feeling.level == showedLevel & feeling.isOpened()) {
                stage.addActor(feeling);
            }
        }

        setupStandartMultiplexer();

        setupButtons();

        setupGoalsMultiplexer();
    }

    @Override
    public void render(float delta) {
        ScreensUtils.gameScreenRender(game, camera, scrollBarY);

        if (ScreensUtils.newFeeling != null) {
            scrollBarY = ScreensUtils.newElementRender(game, stage, scrollBarY, buttonsStage);
        } else if (toMainScreen) {
            counter = ScreensUtils.fromGameToMain(game, camera, counter);
            if (counter == 99) {
                game.batch = new SpriteBatch();
                game.setScreen(new MainMenuScreen(game, true));
                dispose();
            }
        } else if (toAtlasScreen) {
            game.batch = new SpriteBatch();
            game.setScreen(new AtlasScreen(game, true,0));
            dispose();
        } else if (goalsMenuStage > 0) {
            switch (goalsMenuStage) {
                case 1: goalsBackgroundDown();
                        break;
                case 2: goalsBackgroundUp();
                        break;
            }
        } else if (independentCounter == -1) {
            game.batch = new SpriteBatch();
            game.setScreen(new AtlasElementScreen(game, doubleClickedFeeling ,true,0));
            independentCounter = 0;
            dispose();
        } else if (ScreensUtils.new_lvl) {
            //new level animation
            SaveUtils.loadProgress();
            game.expBar = new Texture(Gdx.files.internal("interface/experience_bar/1.bmp"));
            ScreensUtils.new_lvl = false;
            newLevelAnimation();
        } else if (movePanel) {
            if (!isPanelShown) {
                counter = ScreensUtils.showPanel(game, counter);
                if (counter == 50) {
                    sin.setVisible(true); universal.setVisible(true); save.setVisible(true);
                    counter = 0; movePanel = false; isPanelShown = true;
                }
            } else {
                if (counter == 0) {
                    sin.setVisible(false); universal.setVisible(false); save.setVisible(false);
                }
                counter = ScreensUtils.hidePanel(game, counter);
                if (counter == 50) {
                    counter = 0; movePanel = false; isPanelShown = false;
                }
            }
        } else if (isPanelShown) {
            ScreensUtils.drawPanel(game);
        }

        if (independentCounter > 0) {
            drawName();
        }

        buttonsStage.draw();
        buttonsStage.act();

        if (chosen_goal != null) {
            drawGoal();
        }

        stage.draw();
        stage.act();


        game.batch.begin();
        game.batch.setColor(game.r, game.g, game.b, 1f);
        if (!toMainScreen & ScreensUtils.newFeeling == null) {
            game.batch.draw(game.name_background, 0, 430);
            game.font.draw(game.batch, "Уровень " + showedLevel, 85, 465);
            game.batch.setColor(1, 1, 1, 1f);
            game.batch.draw(game.goalsBackground, goals_background.getX(), goals_background.getY());
        }

        if (showNext) {
            if (counter == 0) setupStage();
            counter = ScreensUtils.showNextLevel(game, counter);
            if (counter > 315) {
                counter = 0;
                showNext = false;
            }
        } else if (showPrev) {
            if (counter == 0) {
                counter = 315;
                normalizeElementsTable();
            }
            counter = ScreensUtils.showPrevLevel(game,counter);
            if (counter < 10) {
                showedLevel--;
                setupStage();
                counter = 0;
                showPrev = false;
            }
        }
        game.batch.end();

        if (Gdx.input.getInputProcessor() == goalsMultiplexer) {
            goalsStage.draw();
            goalsStage.act();
            game.batch.begin();
            goals_font.draw(game.batch,goalsStage.getActors().get(goalsStage.getActors().size - 1).getName(),
                    270,190);
            game.batch.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
        buttonsStage.getViewport().update(width, height, false);
        goalsStage.getViewport().update(width, height, false);
    }

    private void normalizeElementsTable() {
        scrollBarY = 400;
        for (Feeling feeling : MyGdxGame.feelings) {
            feeling.lockY = feeling.ABSOLUTE_START_Y + yStartPoint;
            if (feeling.isOpened()) {
                if (feeling.getBenchPosition() == 0) {
                    feeling.getPosition().y = feeling.ABSOLUTE_START_Y + yStartPoint;
                    feeling.setBounds(feeling.getPosition().x, feeling.getPosition().y, 75, 75);
                    if (!feeling.isMoving())
                        feeling.getCurrentPosition().y = feeling.ABSOLUTE_START_Y + yStartPoint;
                    else feeling.setBufferVector(new Vector2(
                            Math.abs(feeling.getCurrentPosition().x - feeling.getPosition().x),
                            Math.abs(feeling.getCurrentPosition().y - feeling.getPosition().y)));
                }
            }
        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

    @Override
    public void dispose() {
        for (Feeling feeling : MyGdxGame.feelings) {
            feeling.lockX = feeling.ABSOLUTE_START_X;
            feeling.lockY = feeling.ABSOLUTE_START_Y;
            if (feeling.isOpened()) {
                feeling.setBenchPosition(0);
                feeling.getPosition().set(feeling.ABSOLUTE_START_X, feeling.ABSOLUTE_START_Y);
                feeling.getCurrentPosition().set(feeling.getPosition().cpy());
            }
            MyGdxGame.startProducts.clear();
        }
        stage.dispose();
        buttonsStage.dispose();
        goalsStage.dispose();
        chosen_goal = null;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    private void newLevelAnimation() {
        game.batch = new SpriteBatch();
        switch (ProgressData.getCurrentLevel()) {
            case 2:
            for (int i = 15; i <= 19;i++) MyGdxGame.feelings.get(i).open();
            MyGdxGame.feelings.get(15).getGroup().isOpened = true;
            ProgressData.addToCurrentExperience(5);
            game.setScreen(new AtlasScreen(game,true,3));
            break;
            // case 3: game.setScreen(new AtlasScreen(game,true,7)); break;
            // case 4: game.setScreen(new AtlasScreen(game,true,9)); break;
            // case 5: game.setScreen(new AtlasScreen(game,true,11)); break;
            default: game.setScreen(new FinalScreen(game));
        }
        dispose();
    }

    private void drawGoal() {
        game.batch.begin();
        if (toMainScreen) {
            goals_font.draw(game.batch, chosen_goal.getName(), 445, 350);
            game.batch.draw(chosen_goal.getGoal_picture(),495,370, 40,40);
        } else {
            goals_font.draw(game.batch, chosen_goal.getName(), 455, 350);
            game.batch.draw(chosen_goal.getGoal_picture(), 505, 370, 40, 40);
        }
        game.batch.end();
    }

    private void drawName() {
        if (independentCounter < 75) {
            game.batch.begin();
            game.font.draw(game.batch, targetFeeling.getName(), 385, 60);
            game.batch.end();
        }
        if (independentCounter == 1) {
            targetFeeling.setIsTaken(false);
        }
        --independentCounter;
    }

    private void setupStage() {
       normalizeElementsTable();

        stage.clear();
        stage.getRoot().clearChildren();
        for (Feeling feeling : MyGdxGame.feelings) {
            if ((feeling.level == showedLevel | feeling.getBenchPosition() != 0) & feeling.isOpened()) {
                stage.addActor(feeling);
            }
        }

        setupStandartMultiplexer();
    }

    // Данный метод проверяет если достигнут низ или верх таблицы в процессе скролинг
    // Вызывается 2 раза в GameScreen>create()>new GestureAdapter()>touchDragged()
    private boolean isOnLimit(Vector2 v, int yStartPoint) {
        if (((scrollBarY >= 400 && velocityY > v.y) || (scrollBarY <= 0 && velocityY < v.y)) && velocityY != 0) {
            boolean down = scrollBarY <= 0;
            scrollBarY = 400;
            if (down) {
                yStartPoint = coff * 105 - 450;
                scrollBarY = 0;
            }
            for (Feeling feeling : MyGdxGame.feelings) {
                feeling.lockY = feeling.ABSOLUTE_START_Y + yStartPoint;
                if (feeling.isOpened()) {
                    if (feeling.getBenchPosition() == 0) {
                        feeling.getPosition().y = feeling.ABSOLUTE_START_Y + yStartPoint;
                        feeling.setBounds(feeling.getPosition().x, feeling.getPosition().y, 75, 75);
                        if (!feeling.isMoving())
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
            scrollBarY += ((velocityY - v.y) / ((float) (coff * 105 - 450) / 400));
        }
    }

    protected void setupButtons() {
        ImageButton arrow_left, atlas_button;
        // Кнопки
        arrow_left = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        goals_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.goals_button)));
        atlas_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.atlas_button)));
        goals_background = new Image(game.goalsBackground);

        sin = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.sin)));
        universal = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.universal)));
        save = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.save)));

        // Настройки кнопок и меню
        arrow_left.setBounds(745, 425, 50, 50);
        buttonsStage.addActor(arrow_left);

        goals_button.setBounds(495, 360, 60, 60);
        buttonsStage.addActor(goals_button);

        atlas_button.setBounds(725, 15, 50, 50);
        buttonsStage.addActor(atlas_button);

        sin.setBounds(755, 330,40,40);
        buttonsStage.addActor(sin);
        universal.setBounds(755, 280,40,40);
        buttonsStage.addActor(universal);
        save.setBounds(755, 230,40,40);
        buttonsStage.addActor(save);

        goals_background.setBounds(0, 470, 800, 480);


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
                goalsMenuStage = 1;
            }
        });

        atlas_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                toAtlasScreen = true;
            }
        });

        sin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                game.setScreen(new StoreScreen(game,null));
            }
        });

        universal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                game.setScreen(new StoreScreen(game,null));
            }
        });

        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (event.getPointer() > 0) return;
                game.setScreen(new StoreScreen(game,null));
            }
        });

        sin.setVisible(false);
        universal.setVisible(false);
        save.setVisible(false);
    }

    protected void setupStandartMultiplexer() {
        GestureDetector scrollMoveDetector = null, elementsDetector;

        if (showedLevel < 3)
        scrollMoveDetector = new GestureDetector(20f, 0.7f, 0.11f, 0.15f, new GestureDetector.GestureAdapter()) {
                @Override
                public boolean touchDown(int x, int y, int pointer, int button) {
                    if (pointer > 0) return false;
                    for (Feeling feeling : MyGdxGame.feelings) {
                        if (feeling.isOpened() & feeling.isTaken()) {
                            feeling.setIsTaken(false);
                            return false;
                        }
                    }
                    Vector3 camera = stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    Vector2 touch = new Vector2(camera.x, camera.y);

                    isDragInArea = touch.x < 315;

                    int i = 0;
                    for (Feeling f: MyGdxGame.feelings) {
                        if (f.level == showedLevel) i++;
                    }

                    coff = i / 3;
                    coff = i % 3 != 0 ? ++coff : coff;
                    return true;
                }

                @Override
                public boolean touchDragged(int screenX, int screenY, int pointer) {
                    if (isDragInArea) {
                        // Переводим в графическую систему координат и двумерный вектор
                        Vector3 camera = stage.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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

        elementsDetector = new GestureDetector(20f, 0.7f, 0.15f, 0.15f, new GestureDetector.GestureAdapter() {
            @Override
            public boolean longPress(float x, float y) {
                if (!stage.touchDown((int) x, (int) y, 0, 0)) return false;
                Gdx.input.setInputProcessor(stage);
                isDragInArea = false;
                velocityY = 0;
                return super.longPress(x, y);
            }

            @Override
            public boolean tap(float x, float y, int count, int button) {
                Vector3 vector = camera.unproject(new Vector3(x,y,0));
                float x1,y1;
                for (Feeling feeling: MyGdxGame.feelings) {
                    if (feeling.isOpened()) {
                        x1 = feeling.getCurrentPosition().x;
                        y1 = feeling.getCurrentPosition().y;
                        if (!feeling.isMoving() & feeling.level == showedLevel &
                                vector.x > x1 & vector.x < x1+65 &
                                vector.y > y1 & vector.y < y1+65 ) {
                            if (count > 1) {
                                independentCounter = -1;
                                doubleClickedFeeling = feeling;
                                return true;
                            }

                            feeling.setIsTaken(true);
                            GameScreen.targetFeeling = feeling;
                            GameScreen.independentCounter = 100;
                            feeling.getCurrentPosition().set(feeling.getPosition().cpy());
                            return true;
                        }
                    }
                }
                return super.tap(x,y,count,button);
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                Vector3 v = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
                if (v.x < 315) {
                    for (Feeling feeling : MyGdxGame.feelings) {
                        if (feeling.isOpened() & feeling.isTaken()) {
                            feeling.setIsTaken(false);
                            return false;
                        }
                    }

                    if (velocityX < -400 & velocityX < velocityY) {
                        if (showedLevel == 5 | showedLevel == ProgressData.getCurrentLevel()) return false;
                        showedLevel++;
                        showNext = true;
                        return true;
                    }
                    if (velocityX > 400 & velocityX > velocityY) {
                        if (showedLevel == 1) return false;
                        showPrev = true;
                        return true;
                    }
                }
                if (v.x > 600) {
                    if (velocityX < -400 & !isPanelShown) {
                        movePanel = true;
                        return true;
                    }
                    if (velocityX > 400 & isPanelShown) {
                        movePanel = true;
                        return true;
                    }
                }
                return false;
            }
        });

        standartMultiplexer = new InputMultiplexer(elementsDetector);

        standartMultiplexer.addProcessor(buttonsStage);

        if (scrollMoveDetector != null)
        standartMultiplexer.addProcessor(scrollMoveDetector);

        Gdx.input.setInputProcessor(standartMultiplexer);
    }

    protected void setupGoalsMultiplexer() {
        setupGoalsStage();
        goalsMultiplexer = new InputMultiplexer(goalsStage);

        GestureDetector moveGoalsMenu = new GestureDetector(20f, 0.2f, 0.4f, 0.15f, new GestureDetector.GestureAdapter()) {
            float velocity;

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                velocity = 0;
                return pointer <= 0;
            }

            @Override
            public boolean touchDragged(int x, int y, int pointer) {
                if (pointer > 0) return false;
                vector = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0));

                if (goals_background.getY() < 0) {
                    goals_background.setY(0);
                    for (int i = 0; i < goals.length; i++) {
                        if (goals[i] == null) continue;
                        if (i < 5) goals[i].setY(335);
                        else goals[i].setY(200);
                         goals[i].toFront();
                    }
                    return false; }

                if (velocity != 0) {
                    goals_background.setY(goals_background.getY() - velocity + vector.y);
                    for (ImageButton btn : goals) {
                        if (btn == null) continue;
                        btn.setY(btn.getY() - velocity + vector.y);
                    }
                }

                if (goals_background.getY() < 0) {
                    goals_background.setY(0);
                    for (int i = 0; i < goals.length; i++) {
                        if (goals[i] == null) continue;
                        if (i < 5) goals[i].setY(335);
                        else goals[i].setY(200);
                        goals[i].toFront();
                    }
                    return false; }

                velocity = vector.y;
                return true;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                velocity = 0;
                if (goals_background.getY() < 130) {
                    goalsMenuStage = 1;
                } else {
                    goalsMenuStage = 2;
                }
                return super.touchUp(x, y, pointer, button);
            }
        };

        goalsMultiplexer.addProcessor(moveGoalsMenu);
    }

    private void setupGoalsStage() {
        ImageButton button;
        goalsStage.addActor(goals_background);
        int x = 65,y = 815;
        goals = new ImageButton[6];
        int counter = 0;
        Group.setTargetGroup();
        if (ReactionUtils.target != null)
        for (Feeling feeling : ReactionUtils.target.elements) {
            if (!feeling.isOpened()) {
                if (counter == 5) break;
                button = new ImageButton(new TextureRegionDrawable(new TextureRegion(feeling.getPicture())));
                button.setBounds(x,y,80,80);
                button.setName(feeling.getName());

                goalsStage.addActor(button);
                goals[counter] = button;

                //TODO:Убрать комментарии, когда все иконки будут готовы
                //setClickListener(button);

                x+=145;
            }
            counter++;
        }

        Feeling buf = null;
        for (Feeling f: MyGdxGame.feelings) {
            if (f.level == ProgressData.getCurrentLevel()) buf = f;
        }
        button = new ImageButton(new TextureRegionDrawable(new TextureRegion(buf.getPicture())));
        button.setBounds(355,715,80,80);
        button.setName(buf.getName());
        goalsStage.addActor(button);
        goals[5] = button;

        //setClickListener(button);
    }

    private void setClickListener(ImageButton button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (event.getPointer() > 0) return;
                chosen_goal = Feeling.findFeeling(button.getName());
                goalsMenuStage = 2;
                super.clicked(event, x, y);
            }
        });
    }

    private void goalsBackgroundDown() {
        goals_background.toFront();
        if (goals_background.getY() <= 30) {
            goals_background.setY(0);
            for (int i = 0; i < goals.length; i++) {
                if (goals[i] == null) continue;
                 if (i < 5) goals[i].setY(335);
                 else goals[i].setY(200);
                 goals[i].toFront();
            }
            Gdx.input.setInputProcessor(goalsMultiplexer);
            goalsMenuStage = 3;
        } else {
            goals_background.setY(goals_background.getY() - 35);
            for (ImageButton btn : goals) {
                if (btn == null) continue;
                btn.setY(btn.getY() - 35);
                btn.toFront();
            }
        }
    }

    private void goalsBackgroundUp() {
        if (goals_background.getY() >= 480) {
            goals_background.setY(480);
            goals_background.toFront();
            for (int i = 0; i < goals.length; i++) {
                if (goals[i] == null) continue;
                if (i < 5) goals[i].setY(815);
                else goals[i].setY(680);
                goals[i].toFront();
            }
            Gdx.input.setInputProcessor(standartMultiplexer);
            goalsMenuStage = 0;
        } else {
            goals_background.setY(goals_background.getY() + 35);
            for (ImageButton btn: goals) {
                if (btn == null) continue;
                btn.setY(btn.getY() + 35);
                btn.toFront();
            }
        }
    }
}

