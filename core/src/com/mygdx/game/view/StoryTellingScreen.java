package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.CharArray;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.SaveUtils;

public class StoryTellingScreen implements Screen {
    private final MyGdxGame game;
    private OrthographicCamera camera;
    private Stage stage;
    private String[] text;
    private BitmapFont font;
    private int align = Align.topLeft;

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

        font = setupWhiteFont();
        font.getData().setScale(0.55f);
        font.getData().setLineHeight(40f);

        text = new String[19];
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
        text[13] = fourteenth;
        text[14] = fiveteenth;
        text[15] = sixteenth;
        text[16] = seventeenth;
        text[17] = eighteenth;
        text[18] = nineteenth;

        background = new Texture(Gdx.files.internal("backgrounds/story_" + index + ".bmp"));
        
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (index == 19) {
                    SaveUtils.createProgress();
                    game.loadGameData();
                }
                if (index == 13) {
                    font = MyGdxGame.setupHandWritingFont();
                    font.getData().setScale(0.55f);
                    font.getData().setLineHeight(40f);
                }

                toNext = true;
                return super.touchDown(event, x, y, pointer, button);
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
            if (index == 1)
                font.draw(game.batch,"Нажми на экран чтобы продолжить", 240, 460);
            font.draw(game.batch,text[index - 1],20,y(text[index - 1].length()),700f, align,true);
        }

        game.batch.end();

        stage.draw();
        stage.act();
    }

    private void nextBackground() {
        if (nextAlpha == 0) {
            index++;
            try {
                nextBackground = new Texture(Gdx.files.internal("backgrounds/story_" + index + ".bmp"));
            } catch (Exception e) {
                toNext = false;
                toText = true;
                game.batch.draw(background, 0, 0);
                font.draw(game.batch,text[index - 2],20,y(text[index - 2].length()),700f, align,true);
                return;
            }
            nextText = text[index - 1];
            Gdx.input.setInputProcessor(null);
        }

        game.batch.setColor(1,1,1,curAlpha);
        game.batch.draw(background,0,0);
        game.batch.setColor(1,1,1,nextAlpha);
        game.batch.draw(nextBackground,0,0);
        game.batch.setColor(1,1,1,1);

        font.setColor(1,1,1,curAlpha);
        font.draw(game.batch,text[index - 2],20,y(text[index - 2].length()),700f, align,true);
        font.setColor(1,1,1,nextAlpha);
        font.draw(game.batch,nextText,20,y(nextText.length()),700f, align,true);
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
            nextText = text[index - 1];
            Gdx.input.setInputProcessor(null);
        }

        game.batch.draw(background,0,0);
        font.setColor(1,1,1,curAlpha);
        font.draw(game.batch,text[index - 2],20,y(text[index - 2].length()),700f, align,true);
        font.setColor(1,1,1,nextAlpha);
        font.draw(game.batch,nextText,20,y(nextText.length()),700f, align,true);
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

    private BitmapFont setupWhiteFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/handwriting.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = Color.WHITE;
        parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }

    private float y (int length) {
        return length/2.8f;
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
    second = "Те которые постоянно стремятся к лучшему, когда и так достигли чего хотели. Что если жить не по чести," +
            "забыть о совести, не уважать любовь? Есть ли кто-то кто ответит на все эти вопросы? Тот кто пронес" +
            " сквозь века тайну cотворения жизни, души.",
    third = "Как получить все из ничего…? Преодолеть путь из ниоткуда, в никуда.",
    fourth = "",
    fifth = "Я спекся. Сгорел изнутри. Я больше ничего не могу почувствовать, моя " +
            "искалеченная душа будто бы вовсе испарилась. Все, что я могу сейчас чувстовать это отголоски " +
            "былых чувств и постоянную всепоглощающую и невероятно глубокую пустоту. Тоска… я бы " +
            "отдал все ради одного мгновения способности чувствовать вновь. Я бродил по миру как " +
            "призрак и в глухом лесу набрел на старинный замок, о котором никогда не слышал. Терять мне " +
            "нечего - я направился прямо туда.",
    sixth = "Cor Meum - это всe ,что было написано над большими деревянными дверями, когда я постучал.                           ",
    seventh = "Двери распахнулись и " +
            "пусота,что была во мне как будто сьежилась. В длинном коридоре, в самом его конце стоял " +
            "старец. Он был высокого роста, в темной мантии, на которую контрастным сиянием свисала седая борода.                            ",
    eighth = "-Твоя душа здесь. -Заговорил он и голос его эхом отразился от стен. Я давно жду тебя.\n" +
            "-Я могу ее вернуть? - спросил я.                                                                     ",
    ninth = "-Получить заново. -и с этими словами он двинулся прочь, скрывшись за ближайшим " +
            "поворотом. Я пустился за ним.Долго поднимались по винтовой лестнице. Прошли мимо " +
            "бессчетного количества комнат.Их двери были во мхе,некоторые покрыты плюющем.Казалось " +
            "они ведут в другие миры.Наконец мы пришли в небольшую комнату, с длинным столом. На " +
            "нем была алая скатерть. Напротив стола зияло огромное окно.Меня мучил вопрос,куда я " +
            "попал, кто этот монах и как буду получать душу, как это возможно?                  ",
    tenth = "-Это место твоих медитаций. Получить душу совсем не просто, но ты " +
            "справишься. Все справлялись. Я тот кто ведает тайнами мироздания. Тайной вечной жизни. Ты " +
            "мог подумать будто я знаю тебя. Это не так, ты не первый и не последний, я помогаю всем " +
            "пройти через это .                                          ",
    eleventh = "Главное, что ты должен знать сейчас- это с чего начать. Я вручаю тебе эту " +
            "книгу, Атлас чувств. Из пестрого набора воспоминаний из твоей жизни она поможет отобрать " +
            "самые важные .                                                                      ",
    twelveth = "Тебе прийдется постепенно восстанавливать все чувства, которые тебе " +
            "довелось пережить. Делать это можноиспользуя память о поступках, которые их " +
            "пробудили. Вспоминая эмоции, впечатления, которые оно вызывало. Просто обмаж корешок " +
            "каплей твоей крови и можешь начинать .                                                  ",
    thirteenth = "Я на миг перевел взгляд на книгу лежавшую на алой скатерти и старец исчез.Что ж думаю " +
            "какие-то сторонние вопросы будут лишними. Сделаю как он сказал.                                                                 ",
    fourteenth = "Цель игры - открыть как можно больше чувств, которые помогут в финале собрать твою душу." +
            "На рабочем столе вы будете перетаскивать доступные чувства из зоны чувств (слева) в зону реакций (справа). Как только все ячейки в зоне реакций будут заполнены, игра проверить, подходит ли данный рецепт под одну из формул. Если подходит, то вы получите новое чувство, в противном случае, чувства полетят на свои места. Список чувств можно пролистывать вверх и вниз. Когда вы перейдете на новый уровень, то с помощью свайпа влево и вправо вы сможете просматривать чувства с других уровней и использовать их в своих реакциях.",
    fiveteenth = "Чтобы перенести чувство, зажмите свой полец на нем на 0.2 секунды и далее перемещайте. При одинарном нажатии на чувство временно высветится его название, а при двойном - откроется его полное описание в атласе. Все чувсвтва (кроме первых пяти) разделены на группы, так как представляют собой определенный отрезок из жизни главного героя. Открыв одну группу, вы получаете доступ к следующей.",
    sixteenth = "Золотая кнопка в зоне реакций открывает меню целей. Там находятся все неоткрытые элементы из текущей группы, а так же последнее чувство уровня - целевое чувство. Так же, при необходимости, можно воспользоваться подсказками. Чтобы открыть меню подсказок, просто сделайте небольшой свайп (с права на лево) в правом краю экрана.",
    seventeenth = "Это атлас. В нем вы можете просмотреть полный список чувств, а нажав на иконку получить полное описание чувства. С помощью свайпов можно листать атлас, а с помощью стрелок перемещаться от одной главы к другой. Как только вы открываете все чувства из одной группы, вы можете прочиать описание элементов из следуюшей. Настоятельно советую читать описание, там могут проскакивать подсказки.",
    eighteenth = "Это подробное описание элемента. Тут можно прочитать историю главного героя. Под иконкой чувсвтва находится 3 кнопки: первая позволяет купить чувство, вторая открывает полное описание группы (полноценную историю из жизни героя), а третья позволяет купить структуру (рецепт) чувства или просмотреть ее, если она уже была куплена.",
    nineteenth = "В главном меню так же есть кнопки \"Напиши нам\" и \"Магазин\". Первая позволяет поделиться игрой с друзьями или написать разработчикам в случае если вы заметили баг или у вас есть советы по улучшению игры. Вторая открывает магазин, в котором можно купить подсказки, чувсвтва или струкуру чувсвтва, а так же пэки.";

}
