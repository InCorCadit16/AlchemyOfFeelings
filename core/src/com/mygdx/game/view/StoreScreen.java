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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Feeling;

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
        else {
            for (Feeling f: MyGdxGame.feelings) {
                if (!f.isOpened()) target = f;
            }
        }

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
                game.buy.open("Чувство","Покупка нового элемента.");
            }
        });

        stage.addActor(element);

        structure = new Button(new TextureRegionDrawable(new TextureRegion(game.structure)));
        structure.setBounds(500,340,70,70);
        structure.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка структуры
                game.buy.open("Структура","Покупка структуры элемента.");
            }
        });

        stage.addActor(structure);

        universal = new Button(new TextureRegionDrawable(new TextureRegion(game.universal)));
        universal.setBounds(140,235,50,50);
        universal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка универсального элемента
                game.buy.open("Универсальное чувство","Универсльный элемент может быть использован в любом рецепте, что заменить недостающее звено.");
            }
        });

        stage.addActor(universal);

        sin = new Button(new TextureRegionDrawable(new TextureRegion(game.sin)));
        sin.setBounds(375,235,50,50);
        sin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка греха
                game.buy.open("Грех","Грех поможет изменить полярность элемента, что повлияет на финал игры. Работает только на легендарные элементы.");
            }
        });

        stage.addActor(sin);

        save = new Button(new TextureRegionDrawable(new TextureRegion(game.save)));
        save.setBounds(610,235,50,50);
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка задержки элементов
                game.buy.open("Задержка элементов","Элементы, подходящие к искомому чувсвту будут оставаться в ячейке рабочей зоны. Дайствует пока не будут отрыты 2 новых чувсвтва");
            }
        });

        stage.addActor(save);

        ques_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.ques_pack)));
        ques_pack.setBounds(110,135,50,50);
        ques_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 3 подсказок
                game.buy.open("3 подсказки","Покупка сразу всех трёх подсказок");
            }
        });

        stage.addActor(ques_pack);

        two_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.two_pack)));
        two_pack.setBounds(270,135,50,50);
        two_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 2 чувств
                game.buy.open("2 чувства","Покупка двух чувств с вашего уровня");
            }
        });

        stage.addActor(two_pack);

        three_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.three_pack)));
        three_pack.setBounds(430,135,60,60);
        three_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 3 чувств
                game.buy.open("3 чувства","Покупка трёх чувств с вашего уровня");
            }
        });

        stage.addActor(three_pack);


        four_pack = new Button(new TextureRegionDrawable(new TextureRegion(game.four_pack)));
        four_pack.setBounds(590,135,120,60);
        four_pack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Покупка 4 чувств
                game.buy.open("4 чувства","Покупка четырёх чувств с вашего уровня");
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
