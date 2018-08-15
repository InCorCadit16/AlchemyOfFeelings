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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;

public class AtlasElementScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private Feeling target;
    private boolean isFromGame;
    private BitmapFont font;
    private int index;
    private ImageButton left_arrow;

    AtlasElementScreen(MyGdxGame gam, Feeling f,boolean fromGame, int index1) {
        game = gam;
        target = f;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        isFromGame = fromGame;

        index = index1;

        setupImageButtons();
        font = MyGdxGame.setupHandWritingFont();
        stage.addListener(new InputListener() {
            float lastY = 0;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (pointer > 0) return false;
                Vector3 v = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
                lastY = v.y;
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Vector3 v3 = camera.unproject(new Vector3(Gdx.input.getX(),Gdx.input.getY(),0));
                Vector2 v = new Vector2(v3.x, v3.y);
                if (camera.position.y < -750) {
                    camera.position.y = -750;
                    left_arrow.setY(-570);
                    return;
                }

                if (camera.position.y > 240) {
                    camera.position.y = 240;
                    left_arrow.setY(420);
                    return;
                }

                camera.translate(0,(lastY - v.y)*0.9f);
                left_arrow.setY(left_arrow.getY() + ( lastY - v.y)*0.9f);

                if (camera.position.y < -750) {
                    camera.position.y = -750;
                    left_arrow.setY(-570);
                    return;
                }

                if (camera.position.y > 240) {
                    camera.position.y = 240;
                    left_arrow.setY(420);
                    return;
                }

                lastY = v.y;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { lastY = 0; }
        });


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        ScreensUtils.atlasElementScreenRender(game,camera,target);

        if (target.isOpened()) drawName();
        drawInfo();

        stage.draw();
        stage.act();
    }

    private void drawName() {
        game.batch.begin();
        font.draw(game.batch, target.getName().replaceAll(" ",""), 250, 470);
        game.batch.end();
    }

    private void drawInfo() {
        game.batch.begin();
        font.getData().setScale(0.8f);
        font.draw(game.batch,target.getDescription(),250,420,550f, Align.left,true);
        font.getData().setScale(1.25f);
        game.batch.end();
    }

    private void setupImageButtons() {
        ImageButton buy, story, structure;
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

        if(!target.isOpened())
            stage.addActor(buy);

        story = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.glasses)));
        story.setBounds(90, 160 , 70, 70);
        story.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GroupScreen(game,target,isFromGame,index));
                dispose();
            }
        });

        if (target.getGroup() != null) stage.addActor(story);

        structure = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.structure)));
        structure.setBounds(170, 160 , 70, 70);
        structure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.buy.open("Структура","Покупка структуры элемента.");
            }
        });

        stage.addActor(structure);
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
