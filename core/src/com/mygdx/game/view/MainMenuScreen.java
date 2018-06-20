package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.ProgressData;

import java.util.ArrayList;


public class MainMenuScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private boolean toGameScreen, toAtlasScreen, toSettingScreen, writeIntent, toStore;
    private int counter = 0;
    private Actor cur, prev;
    private Array<Actor> buttons;


    public MainMenuScreen(MyGdxGame gam) {
        ImageButton play, atlas, settings, write, store;
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT, camera));

        toGameScreen = toAtlasScreen = toSettingScreen = writeIntent = false;

        // Сами кнопки
        play = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.play_texture)));
        atlas = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.atlas_texture)));
        settings = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.settings_texture)));
        write = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.write_texture)));
        store = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.write_texture)));

        // Добавление стилей
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();

        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.play_texture_pressed));
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.play_texture));
        play.setStyle(buttonStyle);

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.atlas_texture_pressed));
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.atlas_texture));
        atlas.setStyle(buttonStyle);

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.settings_texture_pressed));
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.settings_texture));
        settings.setStyle(buttonStyle);

        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.write_texture_pressed));
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.write_texture));
        write.setStyle(buttonStyle);
        
        buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.write_texture_pressed));
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.write_texture));
        store.setStyle(buttonStyle);

        // Настройка кнопок
        play.setBounds(390,370,80,46);
        atlas.setBounds(390,310,80,46);
        settings.setBounds(390,250,80,46);
        write.setBounds(390,190,80,46);
        store.setBounds(390, 130,80,46);

        stage.addActor(play);
        stage.addActor(atlas);
        stage.addActor(settings);
        stage.addActor(write);
        stage.addActor(store);

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                toGameScreen = true;
            }
        });

        atlas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                toAtlasScreen = true;
            }
        });

        settings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                toSettingScreen = true;
            }
        });

        write.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                writeIntent = true;
            }
        });
        
        store.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                toStore = true;
            }
        });

        buttons = stage.getActors();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreensUtils.mainMenuRender(game, camera);

        if (toGameScreen) {
            counter = ScreensUtils.fromMainToGame(game, counter);
            for (int i = 0; i < 5; i++) {
                cur = buttons.get(i);
                if (i == 0) {
                    if (cur.getWidth() <= 6)
                        cur.setSize(0,0);
                    else
                        cur.setBounds(cur.getX() + 3,
                            cur.getY() + 12,
                            cur.getWidth() - 6,
                            cur.getHeight() - 6);
                } else {
                    prev = buttons.get(i - 1);
                    if (prev.getWidth() < 40)
                        if (cur.getWidth() <= 6)
                            cur.setSize(0,0);
                        else
                            cur.setBounds(cur.getX() + 3,
                                cur.getY() + 12,
                                cur.getWidth() - 6,
                                cur.getHeight() - 6);
                }
            }
            if (counter == 100) {
                game.setScreen(new GameScreen(game));
                this.dispose();
            }
        } else if (toAtlasScreen) {
            game.setScreen(new AtlasScreen(game, false));
            this.dispose();
        } else if (toSettingScreen) {
            game.setScreen(new SettingsScreen(game));
            this.dispose();
        } else if (toStore) {
            game.setScreen(new StoreScreen(game));
            this.dispose();
        }

        stage.draw();
        stage.act();

    }

    @Override
    public void show() {
        
    }

    @Override
    public void hide() {
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
