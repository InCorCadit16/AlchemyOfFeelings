package com.mygdx.game.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.game.MyGdxGame;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public final class SaveUtils {
    private static Preferences prefs = Gdx.app.getPreferences("Elements");


    public static void createProgress() {
    /*/
        // Запись элемента: "имя редкость доступность рецепт"
        // рецепт - номера составляюших элементов через нижнее подчеркивание
        // иконки чувств расположены в том же порядке в самом атласе
        //
        // Важные правила:
        // 1) имена элементов в формуле должны идти в алфавитном порядке
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
            anger = "Злость Common false Страх_Неуверенность",
            afraid = "Волнение Common false Страх_Угнетение",
            carefulness = "Осторожность Common false Удача_Волнение",
            desperation = "Отчаяние Common false Обида_Волнение",
            uncertainty = "Неуверенность Common false Волнение_Отчаяние",
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

        int plot = new Random().nextInt(2) + 1;
        switch (plot) {
            case 1:
                prefs.putString("1", sympathy);
                prefs.putString("2", faith);
                prefs.putString("3", sadness);
                prefs.putString("4", affection);
                prefs.putString("5", calmness);
                prefs.putString("6", willingness);
                prefs.putString("7", passion);
                prefs.putString("8", nostalgia);
                prefs.putString("9", confidence);
                prefs.putString("10", peacefulness);
                prefs.putString("11", kindness);
                prefs.putString("12", satisfaction);
                prefs.putString("13", trustfulness);
                prefs.putString("14", happiness);
                prefs.putString("15", sincerity);
                prefs.putString("16", faithfulness);
                prefs.putString("17", certainty);
                prefs.putString("18", disappointment);
                prefs.putString("19", harm);
                prefs.putString("20", carefulness);
                prefs.putString("21", sorrow);
                prefs.putString("22", self_sacrifice);
                prefs.putString("23", oppression);
                prefs.putString("24", luck);
                prefs.putString("25", braveness);
                prefs.putString("26", desperation);
                prefs.putString("27", betrayal);
                prefs.putString("28", afraid);
                prefs.putString("29", fear);
                prefs.putString("30", uncertainty);
                prefs.putString("31", rage);
                prefs.putString("32", anger);
                prefs.putString("33", envy);
                prefs.putString("34", apathy);
                prefs.putString("35", depression);
                prefs.putString("36", courage);
                prefs.putString("37", platitude);
                prefs.putString("38", depravity);
                prefs.putString("39", hazard);
                prefs.putString("40", cowardice);
                prefs.putString("41", humiliation);
                prefs.putString("42", lie);
                prefs.putString("43", doubt);
                prefs.putString("44", stupidness);
                break;
            case 2:
                prefs.putString("1", luck);
                prefs.putString("2", fear);
                prefs.putString("3", braveness);
                prefs.putString("4", harm);
                prefs.putString("5", sorrow);
                prefs.putString("6", oppression);
                prefs.putString("7", courage);
                prefs.putString("8", anger);
                prefs.putString("9", afraid);
                prefs.putString("10", carefulness);
                prefs.putString("11", desperation);
                prefs.putString("12", uncertainty);
                prefs.putString("13", disappointment);
                prefs.putString("14", rage);
                prefs.putString("15", doubt);
                prefs.putString("16", confidence);
                prefs.putString("17", passion);
                prefs.putString("18", betrayal);
                prefs.putString("19", self_sacrifice);
                prefs.putString("20", sympathy);
                prefs.putString("21", faith);
                prefs.putString("22", sadness);
                prefs.putString("23", affection);
                prefs.putString("24", "Спокойствие Common false Умитворение_Доброта");
                prefs.putString("25", willingness);
                prefs.putString("26", nostalgia);
                prefs.putString("27", depravity);
                prefs.putString("28", peacefulness);
                prefs.putString("29", happiness);
                prefs.putString("30", trustfulness);
                prefs.putString("31", sincerity);
                prefs.putString("32", satisfaction);
                prefs.putString("33", kindness);
                prefs.putString("34", faithfulness);
                prefs.putString("35", certainty);
                prefs.putString("36", envy);
                prefs.putString("37", apathy);
                prefs.putString("38", depression);
                prefs.putString("39", platitude);
                prefs.putString("40", hazard);
                prefs.putString("41", cowardice);
                prefs.putString("42", humiliation);
                prefs.putString("43", lie);
                prefs.putString("44",stupidness);
                break;
        }


        /*/
        // Начиная с 3 уровня появляется 2 допольнительных поля:
        // 1) специальный номер для логики работы элементов
        // 2) чувство пара, которое связано с ним
        //
        //
        //
        /*/

        if (MyGdxGame.startChoice)
            prefs.putString("45","Раскаянье Rare false Грусть_Угнетение_Аппатия 1 Милосердие +");
        else
            prefs.putString("45","Печаль Rare false Грусть_Угнетение_Ностальгия 1 Бессердечность -");

        prefs.putString("46","Терпение Rare false Развращенность_Пошлость_Умиротворение 2 Совесть +");
        prefs.putString("47","Понимание Rare false Доброта_Уверенность_Терпение 3 Гордость +");
        prefs.putString("48","Надежда Rare false Вера_Понимание_Самоотверженность 4 Уважение +");
        prefs.putString("49","Уважение Rare false Терпение_Убежденность_Осторожность 5 Надежда +");
        prefs.putString("50","Честь Rare false Отвага_Убежденность_Спокойствие_Уважение 6 Щедрость +");
        prefs.putString("51","Гордость Rare false Вера_Смелость_Преданность_Честь 7 Понимание +");
        prefs.putString("52","Щедрость Rare false Радость_Уверенность_Доброта_Понимание 8 Честь +");
        prefs.putString("53","Совесть Rare false Щедрость_Гордость_Угнетение_Преданность 9 Терпение +");
        prefs.putString("54","Милосердие Rare false Доброта_Симпатия_Доверие_Уверенность 10 Раскаянье +");


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
        prefs.putString("Желание",    "Помню как в детстве я любил смотреть мультики. Они мне очень нравились " +
                                                "и я с нетерпением ждал каждый вечер, что бы посмотреть еще одну серию");
        prefs.putString("Ностальгия", "Сейчас я с грустью вспоминаю те дни...");
        prefs.putString("Уверенность","И все-таки я считаю что все это существует и никуда не делось " +
                                                "ведь я помню об этом и пускай хоть и косвено, но могу вернуться туда." +
                                                " Какое это все же блаженство. И как я раньше не замечал этого? Как же я " +
                                                "хочу все вернуть, как же мне не хватает своих чувств! Мне не хватает себя." +
                                                "Я верю, что однажды у меня все получится.");
        prefs.putString("Страсть",     "Когда мне было 10 к нам в поселок прихали новые люди и так вышло, что они " +
                                                "стали нашими новыми соседями. Это была небольшая семья и единственным в ней " +
                                                "ребенком был девочка, моя сверстница. Она была единственной, чей лик я ждал " +
                                                "увидеть каждый день, не считая моих мультиков.");


    // Таймер
        prefs.putString("time","1 00:00:00");



    /*/
        // Создание прогресса количеством ячеек, опытом
        // нужным для следующего уровня и количеством отображаемых элементов
        // current experience - текущий опыт
    /*/
        prefs.putString("Level 1","2 15 15 0");
        prefs.putString("Level 2","2 15 29 15");
        prefs.putString("Level 3","3 15 5 44");
        prefs.putString("Level 4","4 15 5 49");
        prefs.putString("Level 5","5 15 5 54");
        prefs.putString("Level 6","6 15 1 59");
        prefs.putInteger("current level",1);
        prefs.putInteger("current experience", 5);
        prefs.flush();

        loadProgress();
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
        int START_Y = 355, START_X = 30;
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
            if(i%3 == 0) { y -= ADD_Y; x = START_X; }
            if (i == 15 | i == 44 | i == 49 | i == 54 | i == 59) {START_X += 315; x = START_X;y = START_Y; }

            // Настройки только для Rare
            if (massiveParts.length == 7) {
                f_buf.setSpecialNumber(Integer.parseInt(massiveParts[4]));
                setLinks(f_buf,massiveParts[5],feelings);
                f_buf.setPositivePolarity(massiveParts[6].equals("+"));
            }

            //Уровень каждого чувства
                if (i < 15) f_buf.level = 1;
            else if (i < 44) f_buf.level = 2;
            else if (i < 49) f_buf.level = 3;
            else if (i < 54) f_buf.level = 4;
            else f_buf.level = 5;

            // Настройки только для Legendary
            if (massiveParts.length == 5)
                f_buf.setPositivePolarity(massiveParts[4].equals("+"));

            // Достаем следующий элемент
            key = String.valueOf(++i);
            element = prefs.getString(key,"");
        }

        loadNegative(feelings,formulas);

        setUpDescriptions(feelings);

        setUpFormulas(feelings,formulas);

        setUpRareChoiceFeelings(feelings);

        return feelings;
    }

    private static void loadNegative(ArrayList<Feeling> feelings, ArrayList<String> formulas) {
        Texture goals_texture = null, feeling_texture;
        String[] massiveParts;
        boolean isOpened;
        String element, key;
        Feeling f_buf;

        int i = 146;
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

    private static void setUpFormulas(ArrayList<Feeling> feelings, ArrayList<String> formulas) {
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
            if (n < 60 & !cur.isOpened()) cur.open();

            Collections.sort(formulaList,
                    (feeling, t1) -> { return feeling.getName().replaceAll(" ","").length() >= t1.getName().replaceAll(" ","").length()? 1:-1; });
            cur.setFormula(formulaList);
            formulaList = new ArrayList<>();
        }

        // Находится здесь чтобы не было проблем при создании формулы
        for (Feeling f : feelings) {
            //форматируем имя для написания
            f.setName(formatName(f.getName()));
        }
    }

    private static void setUpRareChoiceFeelings(ArrayList<Feeling> feelings) {
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

    private static void setUpDescriptions(ArrayList<Feeling> feelings) {
        String buf;
        for (Feeling f: feelings) {
            buf = prefs.getString(f.getName(),"");
            f.setDescription(buf);
            f.setBreathDescription("Test...");
        }
    }

    public static void loadProgress() {
        int level = prefs.getInteger("current level",1);
        String info = prefs.getString("Level " + level,"");
        String[] values = info.split(" ");

        ProgressData.setCurrentLevel(level);
        ProgressData.setGapsCount(Integer.parseInt(values[0]));
        ProgressData.setToReachNextLevel(Integer.parseInt(values[1]));
        ProgressData.setElementsCount(Integer.parseInt(values[2]));
        ProgressData.setStartNumber(Integer.parseInt(values[3]));

        int curExp = prefs.getInteger("current experience",0);
        ProgressData.setCurrentExperience(curExp);

        ProgressData.setBackgroundName("first.png");
    }

    static void loadNextLevel() {
        prefs.putInteger("current level",ProgressData.getCurrentLevel() + 1);
        prefs.putInteger("current experience",0);
    }

    private static String formatName(String name) {
        StringBuilder addition = new StringBuilder("");
        for(int i = 0; i < 17 - name.length();i++)
            addition.append(" ");
        return addition + name;
    }

    public static void saveStartChoice(boolean startChoice) {
        prefs.putBoolean("start", startChoice);
    }

    public static boolean loadStartChoice() {
        return prefs.getBoolean("start");
    }

    public static void changePlaces(Feeling f, Feeling f1) {
        String f_info,f1_info;

        f_info = prefs.getString(String.valueOf(f.getNumber()));
        f1_info = prefs.getString(String.valueOf(f1.getNumber()));

        prefs.putString(String.valueOf(f.getNumber()), f1_info);
        prefs.putString(String.valueOf(f1.getNumber()), f_info);
    }

    public static void deleteOddPolarity(Feeling f) {
        for (Feeling target: MyGdxGame.feelings) {
            if (target.getNumber() == f.getNumber() + 100) {
                prefs.putString(String.valueOf(target.getNumber()),"-1");
                prefs.putString(String.valueOf(target.getLinkedFeeling().getNumber()),"-1");
            }

            if (target.getNumber() + 100 == f.getNumber()) {
                changePlaces(target,f);

                prefs.putString(String.valueOf(target.getNumber()),"-1");
                prefs.putString(String.valueOf(target.getLinkedFeeling().getNumber()),"-1");
            }
        }
    }

    public static void startTimer() {
        String date_str = prefs.getString("time");

        try {
            MyGdxGame.currentTime = MyGdxGame.df.parse(date_str);
            TimeThread my = new TimeThread();
            my.start();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    static class TimeThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(1000);
                    MyGdxGame.currentTime.setTime(MyGdxGame.currentTime.getTime()+1000);

                    if (MyGdxGame.df.format(MyGdxGame.currentTime).substring(5,7).equals("00"))
                        if (Integer.parseInt(MyGdxGame.df.format(MyGdxGame.currentTime).substring(2,4)) % 2 == 0) {
                            // Утро
                        } else {
                            // Вечер
                        }



                        if (MyGdxGame.df.format(MyGdxGame.currentTime).substring(5,7).equals("20")) {
                            if (Integer.parseInt(MyGdxGame.df.format(MyGdxGame.currentTime).substring(2,4)) % 2 == 0) {
                                // День
                            } else  {
                                // Ночь
                            }
                        }

                    prefs.putString("time",MyGdxGame.df.format(MyGdxGame.currentTime));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
