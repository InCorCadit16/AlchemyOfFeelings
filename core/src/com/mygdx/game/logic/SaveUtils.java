package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.game.MyGdxGame;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;


public final class SaveUtils {
    private static Preferences prefs = Gdx.app.getPreferences("Elements");


    public static void createProgress() {
    /*/
        // Запись элемента: "имя редкость доступность рецепт"
        // рецепт - номера составляюших элементов через нижнее подчеркивание
        // иконки чувств расположены в том же порядке в самом атласе
    /*/

    String sympathy = "Симпатия Common false 0",
            faith = "Вера Common false Преданность_Умиротворение",
            sadness = "Грусть Common false 0",
            affection = "Привязанность Common false Преданность_Убежденность",
            calmness = "Спокойствие Common false Осторожность_Умиротворение",
            willingness = "Желание Common false Привязанность_Симпатия",
            passion = "Страсть Common false Желание_Симпатия",
            nostalgia = "Ностальгия Common false Симпатия_Грусть",
            confidence = "Уверенность Common false Вера_Убежденность",
            peacefulness = "Умиротворение Common false Спокойствие_Уверенность",
            kindness = "Доброта Common false Вера_Умиротворение",
            satisfaction = "Удовлетворение Common false Привязанность_Желание",
            trustfulness = "Доверие Common false Вера_Доброта",
            happiness = "Радость Common false Вера_Удовлетворение",
            sincerity = "Искренность Common false Доброта_Доверие",
            luck = "Удача Common false Осторожность_Уверенность",
            fear = "Страх Common false Неуверенность_Ярость",
            braveness = "Смелость Common false 0",
            harm = "Обида Common false Злость_Разочарование",
            sorrow = "Скорбь Common false Преданность_Самопожертвование",
            oppression = "Угнетение Common false Обида_Скорбь",
            anger = "Злость Common false Неуверенность_Отчаяние",
            afraid = "Волнение Common false Угнетение_Отчаяние",
            carefulness = "Осторожность Common false Удача_Волнение",
            desperation = "Отчаяние Common false Обида_Угнетение",
            uncertainty = "Неуверенность Common false Волнение_Страх",
            rage = "Ярость Common false Угнетение_Злость",
            courage = "Отвага Common false Страх_Смелость",
            doubt = "Сомнение Common false Страх_Неуверенность",
            hazard = "Азарт Common false Отчаяние_Глупость",
            humiliation = "Унижение Common false Глупость_Неуверенность",
            cowardice = "Трусость Common false Неуверенность_Угнетение",
            apathy = "Аппатия Common false Угнетение_Унижение",
            envy = "Зависть Common false Отчаяние_Желание",
            betrayal = "Предательство Common false Отчаяние_Зависть",
            lie = "Обман Common false Зависть_Страх",
            disappointment = "Разочарование Common false Отчаяние_Обман",
            faithfulness = "Преданность Common false Убежденность_Привязанность",
            platitude = "Пошлость Common false Разочарование_Глупость",
            certainty = "Убежденность Common false Преданность_Вера",
            self_sacrifice = "Самопожертвование Common false Умиротворение_Убежденность",
            depravity = "Развращенность Common false Пошлость_Убежденность",
            stupidness = "Глупость Common false Зависть_Страсть",
            depression = "Депрессия Common false Грусть_Аппатия";


        prefs.putString("1", sympathy);
        prefs.putString("2", faith);
        prefs.putString("3", sadness);
        prefs.putString("4", affection);
        prefs.putString("5", calmness);
        prefs.putString("6", willingness);
        prefs.putString("7", nostalgia);
        prefs.putString("8", confidence);
        prefs.putString("9", passion);
        prefs.putString("10", peacefulness);
        prefs.putString("11", happiness);
        prefs.putString("12", trustfulness);
        prefs.putString("13", sincerity);
        prefs.putString("14", satisfaction);
        prefs.putString("15", kindness);
        prefs.putString("16", harm);
        prefs.putString("17", carefulness);
        prefs.putString("18", disappointment);
        prefs.putString("19", faithfulness);
        prefs.putString("20", certainty);
        prefs.putString("21", self_sacrifice);
        prefs.putString("22", sorrow);
        prefs.putString("23", oppression);
        prefs.putString("24", luck);
        prefs.putString("25", braveness);
        prefs.putString("26", desperation);
        prefs.putString("27", afraid);
        prefs.putString("28", envy);
        prefs.putString("29", uncertainty);
        prefs.putString("30", betrayal);
        prefs.putString("31", anger);
        prefs.putString("32", rage);
        prefs.putString("33", fear);
        prefs.putString("34", stupidness);
        prefs.putString("35", humiliation);
        prefs.putString("36", courage);
        prefs.putString("37", platitude);
        prefs.putString("38", depravity);
        prefs.putString("39", hazard);
        prefs.putString("40", lie);
        prefs.putString("41", cowardice);
        prefs.putString("42", doubt);
        prefs.putString("43", apathy);
        prefs.putString("44", depression);

        // Группы
        prefs.putString("group 1","Желание Ностальгия Уверенность Страсть Умиротворение Удовлетворение");
        prefs.putString("group 2","Радость Умиротворенность");
        prefs.putString("group 3","Доверие Доброта");
        prefs.putString("group 4","Самопожертвование Скорбь Угнетение Удача Смелость Отчаяние");
        prefs.putString("group 5","Волнение Зависть Неуверенность Предательство Злость Ярость");
        prefs.putString("group 6","Страх Глупость Унижение Отвага Пошлость Развращенность");
        prefs.putString("group 7","Азарт Обман Трусость Сомнение Аппатия Депрессия");

        /*/
        // Начиная с 3 уровня появляется 3 допольнительных поля:
        // 1) специальный номер для логики работы элементов
        // 2) чувство пара, которое связано с ним
        // 3) полярность чувства
        /*/

        prefs.putString("45","Раскаянье Rare false Грусть_Угнетение_Аппатия 1 Милосердие +");
        prefs.putString("46","Терпение Rare false Развращенность_Пошлость_Умиротворение 2 Совесть +");
        prefs.putString("47","Понимание Rare false Доброта_Уверенность_Терпение 3 Гордость +");
        prefs.putString("48","Надежда Rare false Вера_Понимание_Самоотверженность 4 Уважение +");
        prefs.putString("49","Уважение Rare false Терпение_Убежденность_Осторожность 5 Надежда +");
        prefs.putString("50","Честь Rare false Отвага_Убежденность_Спокойствие_Уважение 6 Щедрость +");
        prefs.putString("51","Гордость Rare false Вера_Смелость_Преданность_Честь 7 Понимание +");
        prefs.putString("52","Щедрость Rare false Радость_Уверенность_Доброта_Понимание 8 Честь +");
        prefs.putString("53","Совесть Rare false Щедрость_Гордость_Угнетение_Преданность 9 Терпение +");
        prefs.putString("54","Милосердие Rare false Доброта_Симпатия_Доверие_Уверенность 10 Раскаянье +");

        prefs.putString("145","Печаль Rare false Грусть_Угнетение_Ностальгия 1 Бессердечность -");
        prefs.putString("146","Блуд Rare false Развращенность_Пошлость_Отчаяние 2 Лицемерие -");
        prefs.putString("147","Одержимость Rare false Желание_Азарт_Блуд 3 Гнев -");
        prefs.putString("148","Лицемерие Rare false Злость_Обман_Трусость 4 Блуд -");
        prefs.putString("149","Наглость Rare false Глупость_Уверенность_Хитрость 5 Корысть -");
        prefs.putString("150","Гнев Rare false Отвага_Ярость_Убежденность_Пучаль 6 Одержимость -");
        prefs.putString("151","Гордыня Rare false Вера_Смелость_Преданность_Наглость 7 Эгоизм -");
        prefs.putString("152","Корысть Rare false Предательство_Зависть_Страсть_Одержимость 8 Наглость -");
        prefs.putString("153","Эгоизм Rare false Корысть_Гордыня_Аппатия_Разочарование 9 Гордыня -");
        prefs.putString("154","Бессердечность Rare false Эгоизм_Ярость_Развращенность_Страх 10 Печаль -");

        prefs.putString("55","Любовь Legendary false Уважение_Надежда_Искренность_Симпатия_Страсть +");
        prefs.putString("56","Благодарность Legendary false Радость_Доброта_Осторожность_Терпение_Совесть +");
        prefs.putString("57","Благородность Legendary false Щедрость_Честь_Вера_Доброта_Умиротворение +");
        prefs.putString("58","Самоотверженность Legendary false Раскаянье_Милосердие_Уверенность_Отвага_Самопожертвование +");
        prefs.putString("59","Харизма Legendary false Гордость_Понимание_Симпатия_Удача_Спокойствие +");

        prefs.putString("155","Ненависть Legendary false Гнев_Одержимость_Депрессия_Обида_Угнетение -");
        prefs.putString("156","Жадность Legendary false Корысть_Наглость_Азарт_Зависимость_Злость -");
        prefs.putString("157","Порочность Legendary false Блуд_Лицемерие_Отчаяние_Желание_Зависимость -");
        prefs.putString("158","Бесчеловечность Legendary false Бессердечность_Печаль_Злость_Обида_Угнетение -");
        prefs.putString("159","Самоуверенность Legendary false Эгоизм_Гордыня_Искренность_Удовлетворение_Уверенность -");




        //Описание к каждому элементу
        prefs.putString("Желание",
                "Помню как в детстве я любил смотреть мультики. Они мне очень нравились и я с нетерпением ждал" +
                        " каждый вечер, что бы посмотреть еще одну серию");
        prefs.putString("Ностальгия",
                "Сейчас я с грустью вспоминаю те дни...");
        prefs.putString("Уверенность",
                "И все-таки я считаю что все это существует и никуда не делось ведь я помню об этом и пускай хоть" +
                        " и косвено, но могу вернуться туда. Какое это все же блаженство. И как я раньше не замечал этого?" +
                        " Как же я хочу все вернуть, как же мне не хватает своих чувств! Мне не хватает себя." +
                        "Я верю, что однажды у меня все получится.");
        prefs.putString("Страсть",
                "Когда мне было 10 к нам в поселок прихали новые люди и так вышло, что они " +
                                                "стали нашими новыми соседями. Это была небольшая семья и единственным в ней " +
                                                "ребенком был девочка, моя сверстница. Она была единственной, чей лик я ждал " +
                                                "увидеть каждый день, не считая моих мультиков.");
        prefs.putString("Самопожертвование",
                "Когда погибли мои родители мне было 18 лет.Все заботы по дому," +
                "воспитание младшего брата и сестры легли на мои плечи.Я должен был забыть о мечте уехать учится " +
                "в университете и оставаться как минимум на следующие 5 лет в этой дыре.Но я был убежден,что должен " +
                "поступить именно так.Иначе было просто нельзя,и я смирился с этим.");
        prefs.putString("Скорбь",
                "Мы скорбили,память о родителях не давала нам смирится с мыслью,что их нет.Жертвуя " +
                "своим сном каждую ночь я представлял,что они живы,что бы хоть на секунду почувствовать их " +
                "тепло рядом с собой.В голове не укладывалось,что их нет,но факты твердили об этом на " +
                "каждом шагу.Я устроился работать на ферме разнорабочим,денег едва хватало и мне часто " +
                "приходилось голодать,что бы младшим досталось больше.Селяне помогали мне как могли и " +
                "Хлоя не забывала обо мне.Она все же уехала в город,поступила на медицинский " +
                "факультет.Часто писала мне письма,подбадривала меня.");
        prefs.putString("Угнетение",
                "Скорбь продолжала поедать мое сердце,а обида на " +
                "судьбу отравляла мой разум.Я будто горел в неведомом огне,при этом угасая. Странное чувство.");
        prefs.putString("Удача",
                "Если бы не удачный случай думаю я бы не вынес этой ноши.Дело в том,что у меня была " +
                "тeтка,которая жила за границей. Когда умерли родители,она даже не приехала на " +
                "похороны,потому что узнала о их гибели слишком поздно.И все же через два месяца после " +
                "похорон она приехала.Она выявляла очень яркое сочувствие моей судьбе,мол молодой " +
                "парень вынужден наплевать на свою судьбу,прикован в этой дыре.Я понял,что пора " +
                "действовать.Стал ей рассказывать о своей мечте стать терапевтом,осторожно намекая,что не " +
                "могу терять время,ведь еще можно успеть поступить в этом году.Я видел как мамина сестра в " +
                "душе уже согласна взять племянников к себе на воспитание,поэтому пара финальных " +
                "аккордов моей речи убедили ее так поступить.Оформив все документы в кратчайшие сроки " +
                "она забрала моих брата и сестру к себе,а я остался свободен.");
        prefs.putString("Смелость",
                "Хлоя обещала помочь мне наверстать упущенное сдать вступительные" +
                " экзамены и зачеты,для поступления в университет после 1 четверти.Я отчаянно ухватился за эту возможность и " +
                "ринулся в распрастертые объятия судьбы.");
        prefs.putString("Отчаяние",
                "Оказалось 90% слов Хлои был обман,меня охватывало непонятное " +
                "сметение и обида. Все было не так как она говорила,и мне становилось ясно,что она и не ждала меня," +
                "не верила,что я приеду.");
        prefs.putString("Радость",
                "Так я верил,что однажды мы с хлоей все-таки станем хорошими друзьями,от этого становилось спокойно на сердце.");

    // Краткое описание
        prefs.putString("Самопожертвование s", "Я должен был забыть о мечте уехать учится в университете...");
        prefs.putString("Скорбь s", "Мы скорбили,память о родителях не давала нам смирится с мыслью,что их нет...");
        prefs.putString("Угнетение s", "Я будто горел в неведомом огне,при этом угасая. Странное чувство...");
        prefs.putString("Удача s", "Если бы не удачный случай думаю я бы не вынес этой ноши...");
        prefs.putString("Смелость s", "Я ринулся в распрастертые объятия судьбы...");
        prefs.putString("Отчаяние s", "...она и не ждала меня,не верила,что я приеду");


    // Таймер и смена времени
        prefs.putString("время","1 00:10:00");
        prefs.putFloat("r",1f);
        prefs.putFloat("g",1f);
        prefs.putFloat("b",1f);
        prefs.putFloat("alpha morning",0);
        prefs.putFloat("alpha day",1);
        prefs.putFloat("alpha evening",0);
        prefs.putFloat("alpha night",0);



    /*/
        // Создание прогресса c опытом, нужным для следующего уровня
    /*/
        prefs.putInteger("Exp 1",15);
        prefs.putInteger("Exp 2",29);
        prefs.putInteger("Exp 3",5);
        prefs.putInteger("Exp 4",5);
        prefs.putInteger("Exp 5",5);
        prefs.putInteger("current level",1);
        prefs.putInteger("current experience", 5);
        prefs.flush();
    }

    public static boolean isProgressCreated() {
        return prefs.contains("current level");
    }

    public static void open(Feeling feeling) {
        String key = String.valueOf(feeling.getNumber());
        String element = prefs.getString(key,"");
        prefs.remove(key);
        element = element.replaceFirst("false","true");
        prefs.putString(key,element);
        prefs.flush();
    }

    public static ArrayList<Feeling> loadLibrary() {
        Texture goals_texture = null, feeling_texture;
        final int ADD_Y = 95, ADD_X = 95;
        int START_Y = 335, START_X = 30;
        ArrayList<Feeling> feelings = new ArrayList<>();
        String[] massiveParts;
        ArrayList<String> formulas = new ArrayList<>();
        boolean isOpened;
        String element, key;
        int x = START_X,y = START_Y;
        Feeling f_buf;

        int i = 1;
        key = String.valueOf(i);
        element = prefs.getString(key,"");
        while (!element.isEmpty()) {
            // разбиваем строку на элементы
            massiveParts = element.split(" ");
            // достаем картинку с таким же названием
            feeling_texture = new Texture(Gdx.files.internal("elements_icons/" + massiveParts[0] + ".bmp"));
            //goals_texture = new Texture(Gdx.files.internal("goals_icons/" + massiveParts[0] + ".psd"));
            // проверяем доступность элемента
            isOpened = Boolean.parseBoolean(massiveParts[2]);
            // добавляем формулу в список формул
            formulas.add(massiveParts[3]);
            f_buf = new Feeling(Integer.parseInt(key),
                    massiveParts[0],
                    massiveParts[1],
                    isOpened,
                    feeling_texture,
                    goals_texture,
                    x,y);
            feelings.add(f_buf);

            // Устанавливаем координаты иконок
            x += ADD_X;
            if(((i%3 == 0) & i != 45 & i != 48 & i != 51) | i == 47 | i == 52) { y -= ADD_Y; x = START_X; }
            if (i == 15 | i == 44 | i == 49 | i == 54) { x = START_X; y = START_Y; }

            // Настройки только для Rare
            if (massiveParts.length == 7) {
                f_buf.setSpecialNumber(Integer.parseInt(massiveParts[4]));
                setLinks(f_buf,massiveParts[5],feelings);
                f_buf.setPositivePolarity(massiveParts[6].equals("+"));
            }

            //Уровень каждого чувства
                if (i <= 15) f_buf.level = 1;
            else if (i <= 44) f_buf.level = 2;
            else if (i <= 49) f_buf.level = 3;
            else if (i <= 54) f_buf.level = 4;
            else f_buf.level = 5;

            // Настройки только для Legendary
            if (massiveParts.length == 5)
                f_buf.setPositivePolarity(massiveParts[4].equals("+"));

            // Достаем следующий элемент
            key = String.valueOf(++i);
            element = prefs.getString(key,"");
        }

        loadNegative(feelings,formulas);

        setupDescriptions(feelings);

        setupGroups(feelings);

        setupFormulas(feelings,formulas);

        setupRareChoiceFeelings(feelings);

        setTarget(feelings);

        return feelings;
    }

    private static void loadNegative(ArrayList<Feeling> feelings, ArrayList<String> formulas) {
        Texture goals_texture = null, feeling_texture;
        String[] massiveParts;
        boolean isOpened;
        String element, key;
        Feeling f_buf;

        int i = 145;
        key = String.valueOf(i);
        element = prefs.getString(key,"");

        while (!element.isEmpty()) {
            if (element.equals("-1")) {
                // Достаем следующий элемент
                key = String.valueOf(++i);
                element = prefs.getString(key,"");
                continue;
            }
            // разбиваем строку на элементы
            massiveParts = element.split(" ");
            // достаем картинку с таким же названием
            feeling_texture = new Texture(Gdx.files.internal("elements_icons/" + massiveParts[0] + ".bmp"));
            //goals_texture = new Texture(Gdx.files.internal("goals_icons/" + massiveParts[0] + ".psd"));
            // проверяем доступность элемента
            isOpened = Boolean.parseBoolean(massiveParts[2]);
            // добавляем формулу в список формул
            formulas.add(massiveParts[3]);
            f_buf = new Feeling(Integer.parseInt(key),
                    massiveParts[0],
                    massiveParts[1],
                    isOpened,
                    feeling_texture,
                    goals_texture,
                    feelings.get(i - 101).ABSOLUTE_START_X,
                    feelings.get(i - 101).ABSOLUTE_START_Y);
            feelings.add(f_buf);

            // Настройки только для Rare
            if (massiveParts.length == 7) {
                f_buf.setSpecialNumber(Integer.parseInt(massiveParts[4]));
                setLinks(f_buf,massiveParts[5],feelings);
                f_buf.setPositivePolarity(massiveParts[6].equals("+"));
            }

            // Настройки только для Legendary
            if (massiveParts.length == 5)
                f_buf.setPositivePolarity(massiveParts[4].equals("+"));


            // Достаем следующий элемент
            key = String.valueOf(++i);
            element = prefs.getString(key,"");
        }
    }

    private static void setupDescriptions(ArrayList<Feeling> feelings) {
        String buf;
        for (Feeling f: feelings) {
            buf = prefs.getString(f.getName(),"");
            f.setDescription(buf);
            buf = prefs.getString(f.getName() + " s","");
            f.setBreathDescription(buf);
        }
    }

    private static void setupGroups(ArrayList<Feeling> feelings) {
        int counter = 1;
        String result;
        String[] names;
        Feeling[] elems;
        Group group;

        result = prefs.getString("group " + counter);
        while (!result.isEmpty()) {
            names = result.split(" ");
            elems = new Feeling[names.length];

            for (Feeling f:feelings) {
                for (int n = 0; n < names.length; n++) {
                    if (f.getName().equals(names[n])) {
                        elems[n] = f;
                    }
                }
            }

            group = new Group(counter, elems);
            group.linkElements();
            MyGdxGame.groups.add(group);

            result = prefs.getString("group " + ++counter, "");
        }
    }

    private static void setupFormulas(ArrayList<Feeling> feelings, ArrayList<String> formulas) {
        Feeling cur;
        String[] buf;
        ArrayList<Feeling> formulaList = new ArrayList<>();
        for (int n = 0; n < feelings.size(); n++) {
            cur = feelings.get(n);
            buf = formulas.get(n).split("_");
            for (String s : buf) {
                for (Feeling f: feelings) {
                    if (f.getName().equals(s))
                        formulaList.add(f);
                }
            }
            if (n < 30 & !cur.isOpened()) cur.open();

            Collections.sort(formulaList,
                    (feeling, t1) -> { return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });
            cur.setFormula(formulaList);
            formulaList = new ArrayList<>();
        }

        // Находится здесь чтобы не было проблем при создании формулы
        for (Feeling f : feelings) {
            //форматируем имя для написания
            f.setName(formatName(f.getName(),17));
        }
    }

    private static void setupRareChoiceFeelings(ArrayList<Feeling> feelings) {
        ArrayList<Feeling> seconds = new ArrayList<>();
        for (Feeling feeling: feelings) {
            if (feeling.getRare().equals("Rare")) {
                if (feeling.getSpecialNumber() == 2) seconds.add(feeling);
            }
        }

        findChoiceFeeling(seconds);
    }

    private static void findChoiceFeeling(ArrayList<Feeling> feelings) {
        Feeling f = feelings.get(0), t1 = feelings.get(1);
        for (Feeling elem:f.getFormula()) {
            for (Feeling elem1:f.getFormula())
            if (!elem.equals(elem1)) {
                f.setChoiceFeeling(elem);
                t1.setChoiceFeeling(elem);
                return;
            }
        }
    }

    private static void setLinks(Feeling feeling,String name,ArrayList<Feeling> feelings) {
        for (Feeling f:feelings) {
            if (f.getName().equals(name)) {
                feeling.setLinkedFeeling(f);
                f.setLinkedFeeling(feeling);
            }
        }
    }

    private static void setTarget(ArrayList<Feeling> feelings) {
        for (Feeling f : feelings) {
            if (!f.isOpened()) {
                ReactionUtils.target = f;
                return;
            }
        }
    }




    public static void loadProgress() {
        int level = prefs.getInteger("current level",1);
        ProgressData.setCurrentLevel(level);

        int needExp = prefs.getInteger("Exp " + level,1);
        ProgressData.setToReachNextLevel((needExp));

        int curExp = prefs.getInteger("current experience",0);
        ProgressData.setCurrentExperience(curExp);

        switch (level) {
            case 1:
            case 2:
                ProgressData.setGapsCount(2); break;
            case 3:
                ProgressData.setGapsCount(3); break;
            case 4:
                ProgressData.setGapsCount(4); break;
            case 5:
                ProgressData.setGapsCount(5); break;
            default:
                ProgressData.setGapsCount(6);
        }
    }

    static void loadNextLevel() {
        prefs.putInteger("current level",ProgressData.getCurrentLevel() + 1);
        prefs.putInteger("current experience",0);
    }

    public static String formatName(String name, int maxLength) {
        StringBuilder addition = new StringBuilder("");
        for(int i = 0; i < maxLength - name.length();i++)
            addition.append(" ");
        if (name.length() == maxLength) return "   " + name.substring(0,name.length() - 4) + ".";
        return addition + name;
    }

    public static void changePlaces(Feeling f, Feeling f1) {
        String f_info,f1_info;

        f_info = prefs.getString(String.valueOf(f.getNumber()));
        f1_info = prefs.getString(String.valueOf(f1.getNumber()));

        prefs.putString(String.valueOf(f.getNumber()), f1_info);
        prefs.putString(String.valueOf(f1.getNumber()), f_info);
    }

    public static void startTimer(MyGdxGame game) {
        String date_str = prefs.getString("время");

        try {
            MyGdxGame.currentTime = MyGdxGame.df.parse(date_str);
            getTimeOfDay(game);
            TimeThread timeThread = new TimeThread();
            timeThread.game = game;
            timeThread.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private static void getTimeOfDay(MyGdxGame game) {
        game.r = prefs.getFloat("r");
        game.g = prefs.getFloat("g");
        game.b = prefs.getFloat("b");

        game.alpha_morning = prefs.getFloat("alpha morning");
        game.alpha_day = prefs.getFloat("alpha day");
        game.alpha_evening = prefs.getFloat("alpha evening");
        game.alpha_night = prefs.getFloat("alpha night");
    }

    private static void setTimeOfDay(MyGdxGame game) {

        if (MyGdxGame.currentTime.getHours() == 1 & MyGdxGame.currentTime.getMinutes() == 20) {
            // Утро. Конец дня
            MyGdxGame.currentTime.setHours(0);
            MyGdxGame.currentTime.setMinutes(0);
            MyGdxGame.currentTime.setSeconds(0);
            MyGdxGame.currentTime.setTime(MyGdxGame.currentTime.getTime() + 86400000);

            game.alpha_night = 0;
            game.alpha_morning = 1;

            game.r = 1f;
            game.g = 0.9f;
            game.b = 0.9f;
        } else if (MyGdxGame.currentTime.getHours() == 1 & MyGdxGame.currentTime.getMinutes() >= 17) {
            // Переход к утру
            game.alpha_night -= 0.0055;
            game.alpha_morning += 0.0055;

            game.r += 0.00166;
            game.g += 0.00111;
            game.b -= 0.00055;
        } else if (MyGdxGame.currentTime.getMinutes() >= 50) {
            if (MyGdxGame.currentTime.getMinutes() == 50 & MyGdxGame.currentTime.getSeconds() == 0) {
                // Ночь
                game.alpha_evening = 0;
                game.alpha_night = 1;

                game.r = 0.7f;
                game.g = 0.7f;
            }
        } else if (MyGdxGame.currentTime.getMinutes() >= 47) {
            // Переход к ночи
            game.alpha_evening -= 0.0055;
            game.alpha_night += 0.0055;

            game.r -= 0.00083;
            game.g -= 0.00083;
        } else if (MyGdxGame.currentTime.getMinutes() >= 40) {
            if (MyGdxGame.currentTime.getMinutes() == 40 & MyGdxGame.currentTime.getSeconds() == 0) {
                // Вечер
                game.alpha_day = 0;
                game.alpha_evening = 1;

                game.r = 0.85f;
                game.g = 0.85f;
            }
        } else if (MyGdxGame.currentTime.getMinutes() >= 37) {
            // Переход к вечеру
            game.alpha_day -= 0.0055;
            game.alpha_evening += 0.0055;

            game.r -= 0.00083;
            game.g -= 0.00083;
        } else if (MyGdxGame.currentTime.getMinutes() >= 10) {
            if (MyGdxGame.currentTime.getMinutes() == 10 & MyGdxGame.currentTime.getSeconds() == 0) {
                // День
                game.alpha_morning = 0;
                game.alpha_day = 1;

                game.g = 1f;
                game.b = 1f;
            }
        } else if (MyGdxGame.currentTime.getMinutes() >= 7) {
            // Переход к дню
            game.alpha_morning -= 0.0055;
            game.alpha_day += 0.0055;

            game.g += 0.00055;
            game.b += 0.00055;
        }


        prefs.putFloat("r", game.r);
        prefs.putFloat("g", game.g);
        prefs.putFloat("b", game.b);

        prefs.putFloat("alpha morning", game.alpha_morning);
        prefs.putFloat("alpha day", game.alpha_day);
        prefs.putFloat("alpha evening", game.alpha_evening);
        prefs.putFloat("alpha night", game.alpha_night);
    }

    static class TimeThread extends Thread {
        MyGdxGame game;

        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    MyGdxGame.currentTime.setTime(MyGdxGame.currentTime.getTime()+1000);

                    setTimeOfDay(game);

                    prefs.putString("время",MyGdxGame.df.format(MyGdxGame.currentTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
