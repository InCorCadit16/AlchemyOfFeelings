package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;

public class AtlasElementScreen implements Screen {
    private final MyGdxGame game;
    private InputMultiplexer multiplexer;
    private OrthographicCamera camera;
    private Stage stage;
    private Feeling target;
    private boolean isFromGame;
    private BitmapFont font;
    private int index;

    AtlasElementScreen(MyGdxGame gam, Feeling f,boolean fromGame, int index1) {
        game = gam;
        target = f;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        isFromGame = fromGame;

        index = index1;

        multiplexer = new InputMultiplexer(stage);

        setupImageButtons();
        setupFont();
        setupDragListener();


        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        ScreensUtils.atlasElementScreenRender(game,camera,target);

        drawInfo();

        stage.draw();
        stage.act();
    }

    private void drawInfo() {
        game.batch.begin();
        font.draw(game.batch, target.getName().replaceAll(" ",""), 250, 470);
        font.getData().setScale(0.85f);
        font.draw(game.batch,target.getDescription(),250,420,550f, Align.left,true);
        font.getData().setScale(1.17f);
        game.batch.end();
    }

    private void setupImageButtons() {
        ImageButton buy, story, structure, left_arrow;
        left_arrow = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        left_arrow.setBounds(745, 425, 50, 50);
        left_arrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AtlasScreen(game,isFromGame,index));
                dispose();
            }
        });

        stage.addActor(left_arrow);

        buy = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.coin)));
        buy.setBounds(10, 160 , 70, 70);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new StoreScreen(game,target));
            }
        });

        stage.addActor(buy);

        story = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.glasses)));
        story.setBounds(90, 160 , 70, 70);
        story.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        stage.addActor(story);

        structure = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.structure)));
        structure.setBounds(170, 160 , 70, 70);
        structure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        stage.addActor(structure);
    }

    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/handwriting.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = Color.BLACK;
        parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    private void setupDragListener() {
        GestureDetector dragDetector = new GestureDetector(20f, 0.7f, 0.11f, 0.15f, new GestureDetector.GestureAdapter()) {
            float lastY = 0;

            @Override
            public boolean touchDown(float x, float y, int pointer, int button) {
                if (pointer > 0) return false;
                Vector3 v = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
                lastY = v.y;
                return true;
            }

            @Override
            public boolean touchDragged(float x, float y, int pointer) {
                Vector3 v3 = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
                Vector2 v = new Vector2(v3.x, v3.y);
                if (camera.position.y < 0) {
                    camera.position.y = 0;
                    return true;
                }

                if (camera.position.y > 240) {
                    camera.position.y = 240;
                    return true;
                }

                camera.translate(0,(lastY - v.y)/2);

                if (camera.position.y < 0) {
                    camera.position.y = 0;
                    return true;
                }

                if (camera.position.y > 240) {
                    camera.position.y = 240;
                    return true;
                }

                lastY = v.y;

                return false;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                lastY = 0;
                return true;
            }
        };

        multiplexer.addProcessor(dragDetector);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    @Override
    public void show() {

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

    }
}
