package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.ReactionUtils;

public class StoreScreen implements Screen {
    private final MyGdxGame game;
    private Stage stage;
    private OrthographicCamera camera;
    private Feeling target;

    StoreScreen(MyGdxGame gam, Feeling targ) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT,camera));

        if (targ != null) target = targ;
        else target = ReactionUtils.target;

        setupButtons();

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        ScreensUtils.storeScreenRender(game,camera,target);

        stage.draw();
        stage.act();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    private void setupButtons() {
        ImageButton back;
        Button element, structure, universal, sin, save, ques_pack, two_pack, three_pack, four_pack;

        back = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        back.setBounds(740, 430, 50, 50);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game, false));
                dispose();
            }
        });

        stage.addActor(back);

        element = new Button(new TextureRegionDrawable(new TextureRegion(target.getPicture())));
        element.setBounds(220,340,70,70);
        element.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // Покупка элемента
            }
        });

        stage.addActor(element);

        structure = new Button(new TextureRegionDrawable(new TextureRegion(game.structure)));
        structure.setBounds(500,340,70,70);
        structure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка структуры
            }
        });

        stage.addActor(structure);

        universal = new Button(new TextureRegionDrawable(new TextureRegion(game.universal)));
        universal.setBounds(140,235,50,50);
        universal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка универсального элемента
            }
        });

        stage.addActor(universal);

        sin = new Button(new TextureRegionDrawable(new TextureRegion(game.sin)));
        sin.setBounds(375,235,50,50);
        sin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка греха
            }
        });

        stage.addActor(sin);

        save = new Button(new TextureRegionDrawable(new TextureRegion(game.save)));
        save.setBounds(610,235,50,50);
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка задержки элементов
            }
        });

        stage.addActor(save);

        ques_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.ques_pack)));
        ques_pack.setBounds(110,135,50,50);
        ques_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 3 подсказок
            }
        });

        stage.addActor(ques_pack);

        two_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.two_pack)));
        two_pack.setBounds(270,135,50,50);
        two_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 2 чувств
            }
        });

        stage.addActor(two_pack);

        three_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.three_pack)));
        three_pack.setBounds(430,135,60,60);
        three_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 3 чувств
            }
        });

        stage.addActor(three_pack);


        four_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.four_pack)));
        four_pack.setBounds(590,135,120,60);
        four_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 4 чувств
            }
        });

        stage.addActor(four_pack);
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
