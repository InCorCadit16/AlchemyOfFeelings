package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;

public class FinalScreen implements Screen {
    final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private BitmapFont font = MyGdxGame.setupMinecraftFont();

    FinalScreen(MyGdxGame gam) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(game.atlasElementBackground,0,0,800,480);
        font.draw(game.batch,"Поздравляем! Вы прошли бета-версию \"Алхимии чувсвтв\". За дальнейшее поведение игры разработчики ответственности не несут. Благодарим за внимание. Ждите новых версий в ближайшем будущем.", 50,400,700f, Align.topLeft,true);
        game.batch.end();

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, false);
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
