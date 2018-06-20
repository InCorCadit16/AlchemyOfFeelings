package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;

public class AtlasScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private boolean toGame;

    public AtlasScreen(MyGdxGame gam, boolean isFromGame) {
        game = gam;
        toGame = isFromGame;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));

        Button btn;
        for (Feeling feeling : MyGdxGame.feelings) {
            if (feeling.isOpened()) {
                btn = new Button(new TextureRegionDrawable(new TextureRegion(feeling.getPicture())));
                btn.setBounds(feeling.ATLAS_X,
                        feeling.ATLAS_Y,
                        Feeling.ATLAS_WIDTH,
                        Feeling.ATLAS_HEIGHT);
                stage.addActor(btn);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.atlasBackground,0,0);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            if (toGame) game.setScreen(new GameScreen(game));
            else game.setScreen(new MainMenuScreen(game));
            this.dispose();
        }

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
