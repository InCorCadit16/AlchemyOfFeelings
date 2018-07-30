package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;

public class MainMenuScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private int toStage;
    private int counter = 0;
    private ImageTextButton[] buttons;
    private boolean fromGame;


    public MainMenuScreen(MyGdxGame gam,boolean fromGam) {
        fromGame = fromGam;
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT, camera));

        toStage = -1;

        setupButtons();
        if (fromGame) counter = 20;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreensUtils.mainMenuRender(game, camera);

        if (fromGame) {
            buttonsAppear();
            if (counter == 0) {
                fromGame = false;
                stage.clear();
                setupButtons();
            }
        }

        switch (toStage) {
            case -1: break;
            case 0: counter = ScreensUtils.fromMainToGame(game, counter);
                    buttonsFade();
                    if (counter == 120) {
                        game.setScreen(new GameScreen(game));
                        this.dispose();
                    }
                    break;
            case 1: game.setScreen(new AtlasScreen(game, false));
                    this.dispose();
                    break;
            case 2: game.setScreen(new SettingsScreen(game));
                    this.dispose();
                    break;
            case 3: game.sendEmail.sendIntent();
                    toStage = -1;
                    break;
            case 4: game.setScreen(new StoreScreen(game));
                    this.dispose();
                    break;
        }

        stage.draw();
        stage.act();

    }

    private void buttonsAppear() {
        ImageTextButton cur, prev;
        int y = 130;
        for (int i = 4; i >= 0; i--) {
            cur = buttons[i];
            if (i == 4) {
                if (cur.getWidth() >= 80) {
                    cur.setBounds(365, y, 80, 46);
                } else {
                    cur.setBounds(cur.getX() - 10,
                            cur.getY() - 6,
                            cur.getWidth() + 20,
                            cur.getHeight() + 12);
                }
            } else {
                prev = buttons[i + 1];
                if (prev.getWidth() > 40) {
                    if (cur.getWidth() >= 80) {
                        cur.setBounds(365, y, 80, 46);
                    } else {
                        cur.setBounds(cur.getX() - 10,
                                cur.getY() - 6,
                                cur.getWidth() + 20,
                                cur.getHeight() + 12);
                    }
                }
            }
            y += 60;
        }
        --counter;
    }

    private void buttonsFade() {
        ImageTextButton cur, prev;
        for (int i = 0; i < 5; i++) {
            cur = buttons[i];
            if (i == 0) {
                if (cur.getWidth() <= 5)
                    cur.setVisible(false);
                else {
                    cur.setBounds(cur.getX() + 10,
                            cur.getY() + 6,
                            cur.getWidth() - 20,
                            cur.getHeight() - 12);
                    cur.getStyle().font.getData().setScale(
                            (float) (cur.getStyle().font.getScaleX() - 0.03),
                            (float) (cur.getStyle().font.getScaleY() - 0.03));
                }
            } else {
                prev = buttons[i - 1];
                if (prev.getWidth() < 40) {
                    if (cur.getWidth() <= 5)
                        cur.setVisible(false);
                    else {
                    cur.setBounds(cur.getX() + 10,
                            cur.getY() + 6,
                            cur.getWidth() - 20,
                            cur.getHeight() - 12);
                    cur.getStyle().font.getData().setScale(
                            (float) (cur.getStyle().font.getScaleX() - 0.03),
                            (float) (cur.getStyle().font.getScaleY() - 0.03));
                    }
                }
            }
        }
    }

    private void setupButtons() {
        ImageTextButton play, atlas, settings, write, store;
        ImageTextButton.ImageTextButtonStyle buttonStyle = new ImageTextButton.ImageTextButtonStyle();
        buttonStyle.font = game.font;
        buttonStyle.font.getData().setScale((float) 0.3, (float) 0.5);

        // Сами кнопки
        play = atlas = settings = write = store = new ImageTextButton("platform", buttonStyle);
        buttons = new ImageTextButton[]{play,atlas,settings,write,store};

        String text;
        int y = 370;
        for(int i = 0;i < 5; i++) {
            buttonStyle = new ImageTextButton.ImageTextButtonStyle();
            final int curI = i;
            switch (i) {
                case 0:text = "Играть";
                    break;
                case 1:text = "Атлас";
                    break;
                case 2:text = "Настройки";
                    break;
                case 3:text = "Напиши нам";
                    break;
                default:text = "Магазин";
            }

            buttonStyle.font = game.font;
            buttonStyle.down = new TextureRegionDrawable(new TextureRegion(game.main_button_pressed));
            buttonStyle.up = new TextureRegionDrawable(new TextureRegion(game.main_button));
            buttons[i] = new ImageTextButton(text,buttonStyle);

            buttons[i].setBounds(365,y,80,46);
            if (fromGame) buttons[i].setBounds(400,y + 26,1,1);
            y -= 60;



            stage.addActor(buttons[i]);
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (event.getPointer() > 0) return;
                    super.clicked(event, x, y);
                    toStage = curI;
                }
            });
        }
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
