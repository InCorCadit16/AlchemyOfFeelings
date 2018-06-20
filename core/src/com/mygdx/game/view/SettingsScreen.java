package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;

public class SettingsScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private ImageButton sound_button, music_button, notification_button, language_button;

    // Переменные настроек
    boolean sound, music, notification, language;

    SettingsScreen(MyGdxGame gam) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT, camera));

        sound = music = notification = language = true;

        initButtons();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreensUtils.settingsScreenRender(game, camera);

        stage.draw();
        stage.act();

        if (Gdx.input.isTouched()) {
            Vector3 v = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (v.x < 300 | v.x > 500 | v.y < 190 | v.y > 290) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }
    }

    private void initButtons() {
        sound_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.sound_on)));
        music_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.music_on)));
        language_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.language_ru)));
        notification_button = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.notification_on)));


        // SOUND
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(game.sound_on));
        sound_button.setStyle(style);

        sound_button.setBounds(308, 220, 40,40);
        stage.addActor(sound_button);

        sound_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (sound) {
                    sound_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.sound_off));
                    sound = false;
                } else {
                    sound_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.sound_on));
                    sound = true;
                }
            }
        });

        // MUSIC
        style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(game.music_on));
        music_button.setStyle(style);

        music_button.setBounds(352, 220, 40,40);

        stage.addActor(music_button);
        music_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (music) {
                    music_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.music_off));
                    music = false;
                } else {
                    music_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.music_on));
                    music = true;
                }
            }
        });

        // LANGUAGE
        style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(game.language_ru));
        language_button.setStyle(style);

        language_button.setBounds(400, 220, 40,40);
        stage.addActor(language_button);

        language_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (language) {
                    language_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.language_en));
                    language = false;
                } else {
                    language_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.language_ru));
                    language = true;
                }
            }
        });

        // NOTIFICATION
        style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(new TextureRegion(game.notification_on));
        notification_button.setStyle(style);

        notification_button.setBounds(448, 220, 40,40);
        stage.addActor(notification_button);

        notification_button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (notification) {
                    notification_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.notification_off));
                    notification = false;
                } else {
                    notification_button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(game.notification_on));
                    notification = true;
                }
            }
        });

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, false);
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
