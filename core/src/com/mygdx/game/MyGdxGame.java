package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.logic.Buy;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.Group;
import com.mygdx.game.logic.ProgressData;
import com.mygdx.game.logic.SendEmail;
import com.mygdx.game.view.MainMenuScreen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.mygdx.game.logic.SaveUtils;
import com.mygdx.game.view.StoryTellingScreen;


public class MyGdxGame extends Game {
    // Общие переменные
	public final static int VIRTUAL_WIDTH = 800, VIRTUAL_HEIGHT = 480;
	public SpriteBatch batch;
	public BitmapFont font;
	public Texture wall, night, evening, morning, day, storeBackground, goalsBackground, atlasBackground, settingsBackground,locked, gap, scrollBar, expBar, left_arrow, blure, play_texture, atlas_texture, settings_texture, write_texture, play_texture_pressed, atlas_texture_pressed, settings_texture_pressed, write_texture_pressed, goals_button, atlas_button,language_en, language_ru, music_on, music_off, sound_on, sound_off, notification_on, notification_off, main_button, main_button_pressed,atlasElementBackground, name_background, coin, glasses, structure, sin, save, universal, ques_pack,two_pack,three_pack, four_pack, panel;
	public static ArrayList<Feeling> feelings = new ArrayList<>();
	public static ArrayList<Group> groups = new ArrayList<>();
	public SendEmail sendEmail;
	public Buy buy;
	public Music playlist;

	// Для времени
	public static Date currentTime;
	public static final SimpleDateFormat df = new SimpleDateFormat("D HH:mm:ss", Locale.UK);
	public float r = 1,g = 1,b = 1;
	public float alpha_morning,alpha_day,alpha_evening,alpha_night;

    //Переменные настроек
    public boolean sound, music, notification, language;

    // Для проведения реакций
	public static HashMap<Integer, Feeling> startProducts = new HashMap<>();

	public MyGdxGame(SendEmail EmailImplementation, Buy buyImplementation) {
		sendEmail = EmailImplementation;
		buy = buyImplementation;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = setupMinecraftFont();

        initTextures();
        initBackgrounds();

		if(!SaveUtils.isProgressCreated()) {
			playlist = Gdx.audio.newMusic(Gdx.files.internal("music/Intro.mp3"));
			playlist.setVolume(0.1f);
			playlist.setLooping(true);
			playlist.play();
			setScreen(new StoryTellingScreen(this));
		} else
            loadGameData();

	}

	public void loadGameData() {
        SaveUtils.loadProgress();
        feelings = SaveUtils.loadLibrary();
        SaveUtils.startTimer(this);
        sound = music = language = notification = true;
		setupMusic();
        setScreen(new MainMenuScreen(this, false));
    }

	private void initTextures() {
		main_button = new Texture(Gdx.files.internal("interface/main_menu/button.psd"));
		main_button_pressed = new Texture(Gdx.files.internal("interface/main_menu/button_pressed.psd"));

		play_texture = new Texture(Gdx.files.internal("interface/main_menu/play_button.psd"));
		atlas_texture = new Texture(Gdx.files.internal("interface/main_menu/atlas_button.psd"));
		settings_texture = new Texture(Gdx.files.internal("interface/main_menu/settings_button.psd"));
		write_texture = new Texture(Gdx.files.internal("interface/main_menu/write_button.psd"));

		play_texture_pressed = new Texture(Gdx.files.internal("interface/main_menu/play_button_pressed.psd"));
		atlas_texture_pressed = new Texture(Gdx.files.internal("interface/main_menu/atlas_button_pressed.psd"));
		settings_texture_pressed = new Texture(Gdx.files.internal("interface/main_menu/settings_button_pressed.psd"));
		write_texture_pressed = new Texture(Gdx.files.internal("interface/main_menu/write_button_pressed.psd"));

		goals_button = new Texture(Gdx.files.internal("interface/game_zone/goals_button.psd"));
		atlas_button = new Texture(Gdx.files.internal("interface/game_zone/atlas_button.bmp"));

		locked = new Texture(Gdx.files.internal("interface/game_zone/locked.psd"));
		gap = new Texture(Gdx.files.internal("interface/game_zone/gap.psd"));
		scrollBar = new Texture(Gdx.files.internal("interface/game_zone/scrollBar.bmp"));
		expBar = new Texture(Gdx.files.internal("interface/experience_bar/"+ ProgressData.getCurrentExperience() +".bmp"));
		left_arrow = new Texture(Gdx.files.internal("interface/game_zone/left_arrow.psd"));
		blure = new Texture(Gdx.files.internal("interface/game_zone/blure.png"));
		name_background = new Texture(Gdx.files.internal("interface/game_zone/name_background.png"));
		panel = new Texture(Gdx.files.internal("interface/game_zone/panel.psd"));

		notification_off = new Texture(Gdx.files.internal("interface/settings/notification_off.png"));
		notification_on = new Texture(Gdx.files.internal("interface/settings/notification_on.png"));
		music_off = new Texture(Gdx.files.internal("interface/settings/music_off.png"));
		music_on = new Texture(Gdx.files.internal("interface/settings/music_on.png"));
		sound_off = new Texture(Gdx.files.internal("interface/settings/sound_off.png"));
		sound_on = new Texture(Gdx.files.internal("interface/settings/sound_on.png"));
		language_en = new Texture(Gdx.files.internal("interface/settings/language_en.png"));
		language_ru = new Texture(Gdx.files.internal("interface/settings/language_ru.png"));

		coin = new Texture(Gdx.files.internal("interface/atlas/coin.png"));
		glasses = new Texture(Gdx.files.internal("interface/atlas/glasses.png"));
		structure = new Texture(Gdx.files.internal("interface/atlas/structure.png"));

		sin = new Texture(Gdx.files.internal("interface/store/sin.png"));
		universal = new Texture(Gdx.files.internal("interface/store/universal.png"));
		save = new Texture(Gdx.files.internal("interface/store/save.png"));
		ques_pack = new Texture(Gdx.files.internal("interface/store/ques_pack.png"));
		two_pack = new Texture(Gdx.files.internal("interface/store/two_pack.png"));
		three_pack = new Texture(Gdx.files.internal("interface/store/three_pack.png"));
		four_pack = new Texture(Gdx.files.internal("interface/store/four_pack.png"));
	}

	private void initBackgrounds() {
		wall = new Texture(Gdx.files.internal("backgrounds/wall.psd"));
		morning = new Texture(Gdx.files.internal("backgrounds/morning.png"));
		day = new Texture(Gdx.files.internal("backgrounds/day.png"));
		evening = new Texture(Gdx.files.internal("backgrounds/evening.png"));
		night = new Texture(Gdx.files.internal("backgrounds/night.png"));
		storeBackground = new Texture(Gdx.files.internal("backgrounds/store.bmp"));
		goalsBackground = new Texture(Gdx.files.internal("backgrounds/goals.psd"));
		atlasBackground = new Texture(Gdx.files.internal("backgrounds/atlas.png"));
		settingsBackground = new Texture(Gdx.files.internal("backgrounds/settings.psd"));
		atlasElementBackground = new Texture(Gdx.files.internal("backgrounds/atlasElementBackground.png"));
	}

	private void setupMusic() {
		if (playlist != null) playlist.stop();
		switch (ProgressData.getCurrentLevel()) {
			case 1:
			case 2:
			case 3:
			case 4:
				playlist = Gdx.audio.newMusic(Gdx.files.internal("music/1.mp3"));
				break;
			case 5:
				playlist = Gdx.audio.newMusic(Gdx.files.internal("music/5.mp3"));
		}

		playlist.setVolume(0.1f);
		playlist.setLooping(true);
		playlist.play();
	}

	public static BitmapFont setupMinecraftFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/minecraft.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		parameter.color = Color.WHITE;
		parameter.spaceX = 3;
		parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	public static BitmapFont setupHandWritingFont() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/handwriting.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 36;
		parameter.color = Color.BLACK;
		parameter.characters = "абвгдежзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
		BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setColor(r,g,b,1f);
		super.render();

	}

	@Override
	public void dispose () {
		font.dispose();
		locked.dispose();
		gap.dispose();
		expBar.dispose();
		scrollBar.dispose();
		play_texture.dispose();
		settings_texture.dispose();
		atlas_texture.dispose();
		write_texture.dispose();
	}
}
