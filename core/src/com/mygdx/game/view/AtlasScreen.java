package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.AtlasPage;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.Group;
import com.mygdx.game.logic.ProgressData;

public class AtlasScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage, buttonsStage;
    private InputMultiplexer multiplexer;
    private boolean isFromGame;
    private BitmapFont font;
    private int index;
    private Button prev,next,left_arrow;
    private AtlasPage[] pages = new AtlasPage[13];
    private ImageButton btn;
    private Group lastGroup;
    private Feeling buf;

    AtlasScreen(MyGdxGame gam, boolean fromGame, int index1) {
        font = MyGdxGame.setupHandWritingFont();

        font.usesIntegerPositions();
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false,MyGdxGame.VIRTUAL_WIDTH,MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        buttonsStage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));
        isFromGame = fromGame;

        int i = 0;
        for (int n = 0;n < pages.length; n++) {
            pages[n] = new AtlasPage();
            pages[n].left = ++i;
            pages[n].right = ++i;
        }

        pages[0].title = 1;
        pages[0].titleText_f = one_f;
        pages[0].titleText_s = one_s;
        pages[3].title = 2;
        pages[3].titleText_f = two_f;
        pages[3].titleText_s = two_s;
        pages[7].title = 3;
        pages[7].titleText_f = three_f;
        pages[7].titleText_s = three_s;
        pages[9].title = 4;
        pages[9].titleText_f = four_f;
        pages[9].titleText_s = four_s;
        pages[11].title = 5;
        pages[11].titleText_f = five_f;
        pages[11].titleText_s = five_s;

        pages[0].left = 0;
        pages[3].left = 0;
        pages[7].left = 0;
        pages[9].left = 0;
        pages[11].left = 0;

        lastGroup = MyGdxGame.groups.get(0);
        for (Group group: MyGdxGame.groups) {
            if (group.isOpened) {
                lastGroup = group;
                if (group.isFinished()) lastGroup = MyGdxGame.groups.get(group.number);
            }
        }

        for (Feeling feeling : MyGdxGame.feelings) {
            btn = new ImageButton(new TextureRegionDrawable(new TextureRegion(feeling.getPicture())));
            btn.setSize(Feeling.WIDTH, Feeling.HEIGHT);
            btn.setName(feeling.getName());
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new AtlasElementScreen(game,Feeling.findFeeling(feeling.getName()),isFromGame, index));
                    dispose();
                }
            });

            if (!lastGroup.contains(feeling) & !feeling.isOpened()) {
                btn.setTouchable(Touchable.disabled);
                btn.getImage().setColor(0.1f,0.2f,0.2f,1);
            }
            sortFeeling(feeling, btn);
        }

        for (AtlasPage page:pages) {
            page.sort();
        }

        index = index1;
        setPage(pages[index]);

        setupButtons();

        multiplexer = new InputMultiplexer(stage);

        multiplexer.addProcessor(buttonsStage);

        setupPageFlinger();

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {

        ScreensUtils.atlasScreenRender(game,camera,isFromGame);


        game.batch.begin();
        for (ImageButton btn:pages[index].elements) {
            buf = Feeling.findFeeling(btn.getName());
            if (buf.isOpened()) { drawName(btn); drawInfo(btn); }
            else if (!buf.isOpened() &  lastGroup.contains(buf)) { drawInfo(btn); }
            else if (!buf.isOpened() &  !lastGroup.contains(buf)) {
            }
        }


        if (pages[index].title != 0) {
            drawTitle();
        }

        drawNumbers();

        game.batch.end();

        stage.act();
        stage.draw();
        buttonsStage.draw();
        buttonsStage.act();
    }

    private void drawName(Button button) {
        font.getData().setScale(0.6f);
        font.draw(game.batch, button.getName().replaceAll(" ",""), button.getX()+ 70, button.getY() + 65);
        font.getData().setScale(1.66f);
    }

    private void drawInfo(Button button) {
        font.getData().setScale(0.45f);
        font.draw(game.batch, Feeling.findBreathDescription(button.getName()), button.getX()+ 70, button.getY() + 40,255f,Align.left,true);
        font.getData().setScale(2.2f);
    }

    private void drawTitle() {
        font.getData().setScale(2);
        font.draw(game.batch,"Глава " + pages[index].title, 120,370);
        font.getData().setScale(0.5f);
        if (!pages[index].titleText_f.isEmpty()) {
            font.draw(game.batch, pages[index].titleText_f,
                    60, 300, 320f, Align.topLeft, true);
            font.draw(game.batch, pages[index].titleText_s,
                    430, 440, 320f, Align.topLeft, true);
        }
    }

    private void drawNumbers() {
        font.getData().setScale(0.5f);
        if (pages[index].left != 0)
            font.draw(game.batch, String.valueOf(pages[index].left), 210, 60);

        if (pages[index].right != 0)
            font.draw(game.batch, String.valueOf(pages[index].right), 590, 60);
        font.getData().setScale(4f);
    }

    private void setupButtons() {
        left_arrow = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        left_arrow.setBounds(745, 425, 50, 50);
        left_arrow.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(isFromGame? new GameScreen(game) : new MainMenuScreen(game, false));
                dispose();
            }
        });
        buttonsStage.addActor(left_arrow);

        prev = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        prev.setBounds(35,30,30,30);
        prev.setTransform(true);
        prev.setOrigin(Align.center);
        prev.rotateBy(180);

        prev.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);

                index = prevTitle();
                setPage(pages[index]);

                if (index == 0)
                    prev.setVisible(false);
                if (index < pages.length - 1)
                    next.setVisible(true);


            }
        });
        buttonsStage.addActor(prev);


        next = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.left_arrow)));
        next.setBounds(735,30,30,30);

        next.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);

                index = nextTitle();
                setPage(pages[index]);

                if (index == pages.length - 1)
                    next.setVisible(false);
                if (index > 0)
                    prev.setVisible(true);



            }
        });
        buttonsStage.addActor(next);

    }

    private void sortFeeling(Feeling feeling, ImageButton btn) {
        int n = feeling.getNumber();
        if (n <= 10) pages[1].add(btn);
        else if (n <= 15) pages[2].add(btn);
        else if (n <= 25) pages[4].add(btn);
        else if (n <= 35) pages[5].add(btn);
        else if (n <= 44) pages[6].add(btn);
        else if (n <= 49) pages[8].add(btn);
        else if (n <= 54) pages[10].add(btn);
        else if (n <= 59) pages[12].add(btn);
    }

    private int nextTitle() {
        if (pages[index].title == ProgressData.getCurrentLevel()) return index;
        if (index == pages.length - 1) return index;
        for (int i = ++index;i < pages.length - 1;i++) {
            if (pages[i].title != 0) return i;
        }
        return index;
    }

    private int prevTitle() {
        if (index == 0) return index;
        for (int i = --index;i >= 0;i--) {
            if (pages[i].title != 0) return i;
        }
        return index;
    }

    private void setPage(AtlasPage page) {
        stage.clear();

        setupButtons();

        for (Button b: page.elements) {
            stage.addActor(b);
        }
    }

    private void setupPageFlinger() {
        GestureDetector flingDetector = new GestureDetector(20f, 0.7f, 0.11f, 0.15f, new GestureDetector.GestureAdapter() {
            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if (velocityX < -100) {
                    if (index == pages.length - 1) return true;
                    if (pages[index + 1].title == ProgressData.getCurrentLevel() + 1) return true;

                    setPage(pages[++index]);

                    if (index == pages.length - 1)
                        next.setVisible(false);
                    if (index > 0)
                        prev.setVisible(true);

                    return true;
                } else if (velocityX > 100) {
                    if (index == 0) return true;

                    setPage(pages[--index]);

                    if (index == 0)
                        prev.setVisible(false);
                    if (index < pages.length - 1)
                        next.setVisible(true);

                    return true;
                }
                return super.fling(velocityX, velocityY, button);
            }
        });

        multiplexer.addProcessor(flingDetector);
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,false);
        buttonsStage.getViewport().update(width,height,false);
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

    private final String one_f = "",
    one_s = "",
    two_f = "Шли годы. Мы взрослели. Хлоя день ото дня превращалась в очаровательную девушку,которая " +
            "рвалась вылететь из клетки. Я понимал, что частью той клетки был и я. Было очень больно это " +
            "осознавать. Моя симпатия к ней перерастала в нечто более серьезное. " +
            "Я был убежден,что Хлоя моя вторая половина и был абсолютно предан ей.",
    two_s = " Пусть она не считала так, я не собирался предавать " +
            "ее, ведь однажды она должна была посмотреть на меня другими глазами. И все же я не " +
            "навязывался, был очень осторжен и возможно она даже и не дагадывалась о моих глубоких " +
            "чувствах. Теперь я понимаю ошибочность своей позиции отмалчивания, ведь она приводила лишь " +
            "к разочарованию и волнам накатывающейся обиды.",
    three_f = "",
    three_s = "",
    four_f = "",
    four_s = "",
    five_f = "",
    five_s = "";
}
