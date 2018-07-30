package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.AtlasPage;
import com.mygdx.game.logic.Feeling;

public class AtlasScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private InputMultiplexer multiplexer;
    private boolean isFromGame;
    private BitmapFont font;
    private int index;
    private Button prev,next,left_arrow;
    private AtlasPage[] pages = new AtlasPage[13];
    private Button btn;

    public AtlasScreen(MyGdxGame gam, boolean isFromGame) {
        setupFont();

        font.usesIntegerPositions();
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        this.isFromGame = isFromGame;

        for (int i = 0; i < pages.length;i++) {
            pages[i] = new AtlasPage();
        }
        pages[0].title = 1;
        pages[3].title = 2;
        pages[7].title = 3;
        pages[9].title = 4;
        pages[11].title = 5;
        
        index = 1;

        for (Feeling feeling : MyGdxGame.feelings) {
            if (feeling.isOpened()) {
                btn = new Button(new TextureRegionDrawable(new TextureRegion(feeling.getPicture())));
                btn.setSize(Feeling.WIDTH, Feeling. HEIGHT);
                btn.setName(feeling.getName());
                btn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        game.setScreen(new AtlasElementScreen(game,Feeling.findFeeling(feeling.getName()),isFromGame));
                        dispose();
                    }
                });

                sortFeeling(feeling, btn);
            }
        }

        for (AtlasPage page:pages) {
            page.sort();
        }

        setPage(pages[index]);

        setupButtons();

        multiplexer = new InputMultiplexer(stage);

        setupPageFlinger();

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ScreensUtils.atlasScreenRender(game,camera,isFromGame);


        game.batch.begin();
        for (Button btn:pages[index].elements) {
            drawInfo(btn);
        }
        if (pages[index].title != 0) {
            drawTitle();
        }
        game.batch.end();

        stage.act();
        stage.draw();
    }

    private void drawInfo(Button btn) {
        font.getData().setScale(0.5f);
        font.draw(game.batch, btn.getName().replaceAll(" ",""), btn.getX()+ 75, btn.getY() + 65);
        font.getData().setScale(0.5f);
        font.setUseIntegerPositions(true);
        font.draw(game.batch, Feeling.findBreathDescription(btn.getName()), btn.getX()+ 75, btn.getY() + 45);
        font.setUseIntegerPositions(false);
        font.getData().setScale(4);
    }

    private void drawTitle() {
        font.getData().setScale(2);
        font.draw(game.batch,"Глава " + pages[index].title, 120,330);
        font.getData().setScale(0.5f);
    }

    private void setupButtons() {
        left_arrow = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        left_arrow.setBounds(745, 425, 50, 50);
        left_arrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(isFromGame? new GameScreen(game) : new MainMenuScreen(game, false));
                dispose();
            }
        });
        stage.addActor(left_arrow);

        prev = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        prev.setName("prev");
        prev.setBounds(35,30,30,30);
        prev.setTransform(true);
        prev.setOrigin(Align.center);
        prev.rotateBy(180);

        prev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);

                index = prevTitle();
                setPage(pages[index]);

                if (index == 0)
                    prev.setVisible(false);
                if (index < pages.length - 1)
                    next.setVisible(true);


            }
        });
        stage.addActor(prev);


        next = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        next.setBounds(735,30,30,30);

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);

                index = nextTitle();
                setPage(pages[index]);

                if (index == pages.length - 1)
                    next.setVisible(false);
                if (index > 0)
                    prev.setVisible(true);



            }
        });
        stage.addActor(next);

    }

    private void sortFeeling(Feeling feeling, Button btn) {
        int n = feeling.getNumber();
        if (n <= 10) pages[1].add(btn);
        else if (n <= 15) pages[2].add(btn);
        else if (n <= 25) pages[4].add(btn);
        else if (n <= 35) pages[5].add(btn);
        else if (n <= 44) pages[6].add(btn);
        else if (n <= 49) pages[8].add(btn);
        else if (n <= 54) pages[10].add(btn);
        else if (n <= 59) pages[12].add(btn);
    }

    private void setPage(AtlasPage page) {
        stage.clear();

        setupButtons();

        for (Button b: page.elements) {
            stage.addActor(b);
        }
    }

    private int nextTitle() {
        if (index == pages.length - 1) return index;
        for (int i = ++index;i < pages.length - 1;i++) {
            if (pages[i].title != 0) return i;
        }
        return index;
    }

    private int prevTitle() {
        if (index == 0) return index;
        for (int i = --index;i >= 0;i--) {
            if (pages[i].title != 0) return i;
        }
        return index;
    }

    private void setupPageFlinger() {
        GestureDetector flingDetector = new GestureDetector(20f, 0.7f, 0.11f, 0.15f, new GestureDetector.GestureAdapter() {
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (velocityX < -50) {
                    if (index == pages.length - 1) return true;

                    setPage(pages[++index]);

                    if (index == pages.length - 1)
                        next.setVisible(false);
                    if (index > 0)
                        prev.setVisible(true);

                    return true;
                } else if (velocityX > 50) {
                    if (index == 0) return true;

                    setPage(pages[--index]);

                    if (index == 0)
                        prev.setVisible(false);
                    if (index < pages.length - 1)
                        next.setVisible(true);

                    return true;
                }
                return super.fling(velocityX, velocityY, button);
            }
        });

        multiplexer.addProcessor(flingDetector);
    }

    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/handwriting.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        parameter.color = Color.BLACK;
        parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
