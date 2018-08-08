package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.SaveUtils;

public class StoryTellingScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private String[] text;
    private BitmapFont font;
    int align = Align.topLeft;

    // Для переходов
    private boolean toNext = false, toText = false;
    private Texture background, nextBackground;
    private String nextText;
    private float nextAlpha = 0, curAlpha = 1f;
    private int index = 1;

    public StoryTellingScreen(MyGdxGame gam) {
        game = gam;
        camera = new OrthographicCamera(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT);
        stage = new Stage(new StretchViewport(MyGdxGame.VIRTUAL_WIDTH, MyGdxGame.VIRTUAL_HEIGHT, camera));

        font = MyGdxGame.setupMinecraftFont();
        font.getData().setScale(0.5f);
        font.getData().setLineHeight(40f);

        text = new String[13];
        text[0] = first;
        text[1] = second;
        text[2] = third;
        text[3] = fourth;
        text[4] = fifth;
        text[5] = sixth;
        text[6] = seventh;
        text[7] = eighth;
        text[8] = ninth;
        text[9] = tenth;
        text[10] = eleventh;
        text[11] = twelveth;
        text[12] = thirteenth;

        background = new Texture(Gdx.files.internal("backgrounds/story_" + index + ".bmp"));
        

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (index == 13) {
                    SaveUtils.createProgress();
                    game.loadGameData();
                }
                if (index < 7 | index == 9) toNext = true;
                else toText = true;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (toNext)
            nextBackground();
        else if (toText)
            nextText();
        else {
            game.batch.draw(background, 0, 0);
            font.draw(game.batch,text[index - 1],20,text[index - 1].length()/3 + 30,700f, align,true);
        }

        game.batch.end();

        stage.act();
        stage.draw();
    }

    private void nextBackground() {
        if (nextAlpha == 0) {
            index++;
            nextBackground = new Texture(Gdx.files.internal("backgrounds/story_" + index + ".bmp"));
            nextText = text[index - 1];
            Gdx.input.setInputProcessor(null);
        }

        game.batch.setColor(1,1,1,curAlpha);
        game.batch.draw(background,0,0);
        game.batch.setColor(1,1,1,nextAlpha);
        game.batch.draw(nextBackground,0,0);
        game.batch.setColor(1,1,1,1);

        font.setColor(1,1,1,curAlpha);
        font.draw(game.batch,text[index - 2],20,text[index - 2].length()/3 + 30,700f, align,true);
        font.setColor(1,1,1,nextAlpha);
        font.draw(game.batch,nextText,20,nextText.length()/3 + 30,700f, align,true);
        font.setColor(1,1,1,1);


        nextAlpha += 0.01;
        curAlpha -= 0.01;

        if (nextAlpha >= 1) {
            background = nextBackground;
            curAlpha = 1;
            nextAlpha = 0;
            toNext = false;
            Gdx.input.setInputProcessor(stage);
        }
    }

    private void nextText() {
        if (nextAlpha == 0) {
            index++;
            nextText = text[index - 1];
            Gdx.input.setInputProcessor(null);
        }

        game.batch.draw(background,0,0);
        font.setColor(1,1,1,curAlpha);
        font.draw(game.batch,text[index - 2],20,text[index - 2].length()/3 + 30,700f, align,true);
        font.setColor(1,1,1,nextAlpha);
        font.draw(game.batch,nextText,20,nextText.length()/3 + 30,700f, align,true);
        font.setColor(1,1,1,1);


        nextAlpha += 0.01;
        curAlpha -= 0.01;

        if (nextAlpha >= 1) {
            curAlpha = 1;
            nextAlpha = 0;
            toText = false;
            Gdx.input.setInputProcessor(stage);
        }
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

    private final String first = "Что случается с людьми, которые не ценят свою душу? Не ценят чувства, которые они " +
            "ежедневно испытывают, считая, что это пустое.",
    second = "Те которые постоянно стремятся к " +
            "лучшему,когда и так достигли чего хотели.Что если жить не по чести,забыть о совести,не " +
            "уважать любовь? Есть ли кто-то кто ответит на все эти вопросы? Тот кто пронес сквозь века " +
            "тайну Сотворения жизни, души",
    third = "Как получить все из ничего…? Преодолеть путь из ниоткуда, в никуда.",
    fourth = "",
    fifth = "Я спекся. Сгорел изнутри. Я больше ничего не могу почувствовать, моя " +
            "искалеченная душа будто бы вовсе испарилась. Все, что я могу сейчас чувстовать это отголоски " +
            "былых чувств и постоянную всепоглощающую и невероятно глубокую пустоту. Тоска… я бы " +
            "отдал все ради одного мгновения способности чувствовать вновь. Я бродил по миру как " +
            "призрак и в глухом лесу набрел на старинный замок, о котором никогда не слышал. Терять мне " +
            "нечего - я направился прямо туда",
    sixth = "Cor Meum - это всe ,что было написано над большими деревянными дверями, когда я постучал.",
    seventh = "Двери распахнулись и " +
            "пусота,что была во мне как будто сьежилась. В длинном коридоре, в самом его конце стоял " +
            "старец. Он был высокого роста, в темной мантии, на которую контрастным сиянием свисала седая борода.",
    eighth = "-Твоя душа здесь.-Заговорил он и голос его эхом отразился от стен. Я давно жду тебя.\n" +
            "-Я могу ее вернуть? - спросил я.",
    ninth = "-Получить заново. -и с этими словами он двинулся прочь, скрывшись за ближайшим " +
            "поворотом. Я пустился за ним.Долго поднимались по винтовой лестнице. Прошли мимо " +
            "бессчетного количества комнат.Их двери были во мхе,некоторые покрыты плюющем.Казалось " +
            "они ведут в другие миры.Наконец мы пришли в небольшую комнату, с длинным столом. На " +
            "нем была алая скатерть. Напротив стола зияло огромное окно.Меня мучил вопрос,куда я " +
            "попал, кто этот монах и как буду получать душу, как это возможно?",
    tenth = "-Это место твоих медитаций. Получить душу совсем не просто,но ты " +
            "справишься. Все справлялись. Я тот кто ведает тайнами мироздания. Тайной вечной жизни. Ты " +
            "мог подумать будто я знаю тебя. Это не так, ты не первый и не последний, я помогаю всем " +
            "пройти через это. ",
    eleventh = "Главное, что ты должен знать сейчас- это с чего начать. Я вручаю тебе эту " +
            "книгу, Атлас чувств. Из пёстрого набора воспоминаний из твоей жизни она поможет отобрать " +
            "самые важные. ",
    twelveth = "Тебе прийдётся постепенно восстанавливать все чувства, которые тебе " +
            "довелось пережить. Делать это можноиспользуя память о поступках, которые их " +
            "пробудили. Вспоминая эмоции, впечатления, которые оно вызывало. Просто обмаж корешок " +
            "каплей твоей крови и можешь начинать.",
    thirteenth = "Я на миг перевёл взгляд на книгу лежавшую на алой скатерти и старец исчез.Что ж думаю " +
            "какие-то сторонние вопросы будут лишними. Сделаю как он сказал.";

}
