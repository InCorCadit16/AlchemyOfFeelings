package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.Group;

public class GroupScreen implements Screen {
    private final MyGdxGame game;
    private Stage stage;
    private OrthographicCamera camera;
    private BitmapFont font;
    private StringBuilder title, text;

    GroupScreen(MyGdxGame gam, Feeling source,boolean fromGame, int index) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        font = MyGdxGame.setupHandWritingFont();

        Group target = source.getGroup();
        title = new StringBuilder("(");
        text = new StringBuilder();
        for (Feeling f:target.elements) {
            if (f.isOpened()) title.append(f.getName().replaceAll(" ","")).append(", ");
            else title.append("... ").append(", ");
            text.append(f.getDescription()).append("\n\n");
        }

        title.deleteCharAt(title.length() - 1);
        title.deleteCharAt(title.length() - 1);
        title.append(")");

        ImageButton back = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        back.setBounds(740,420,50,50);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new AtlasElementScreen(game,source,fromGame,index));
                dispose();
            }
        });
        stage.addActor(back);

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
                if (camera.position.y < -1600) {
                    camera.position.y = -1600;
                    back.setY(-1420);
                    return;
                }

               if (camera.position.y > 240) {
                    camera.position.y = 240;
                   back.setY(420);
                    return;
                }

                camera.translate(0,(lastY - v.y)*0.9f);
                back.setY(back.getY() + (lastY - v.y)*0.9f);


                if (camera.position.y < -1600) {
                    camera.position.y = -1600;
                    back.setY(-1420);
                    return;
                }

                if (camera.position.y > 240) {
                    camera.position.y = 240;
                    back.setY(420);
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
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(game.atlasElementBackground,0,-2200,800,2680);
        font.draw(game.batch,title,100,470,600f, Align.center,true);
        font.draw(game.batch,text,10,400,780f, Align.topLeft,true);
        game.batch.end();

        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) { stage.getViewport().update(width,height,false); }

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
