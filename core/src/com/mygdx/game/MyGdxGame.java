package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.logic.Feeling;
import com.mygdx.game.logic.ProgressData;
import com.mygdx.game.view.MainMenuScreen;

import java.util.ArrayList;
import java.util.HashMap;

import com.mygdx.game.logic.SaveUtils;
import com.mygdx.game.view.ScreensUtils;


public class MyGdxGame extends Game {
    // Общие переменные
	public final static int VIRTUAL_WIDTH = 800, VIRTUAL_HEIGHT = 480;
	public SpriteBatch batch;
	public BitmapFont font;
	public Texture mainBackground, playBackground, storeBackground, goalsBackground, atlasBackground, settingsBackground,locked, gap, scrollBar, expBar, left_arrow,blure, play_texture, atlas_texture, settings_texture, write_texture, play_texture_pressed, atlas_texture_pressed, settings_texture_pressed, write_texture_pressed, goals_button, atlas_button,language_en, language_ru, music_on, music_off, sound_on, sound_off, notification_on, notification_off ;
	public static ArrayList<Feeling> feelings = new ArrayList<com.mygdx.game.logic.Feeling>();

    // Для проведения реакций
	public static HashMap<Integer, Feeling> startProducts = new HashMap<Integer, Feeling>();

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fonts/test.fnt"));

		initTextures();

        //if(!SaveUtils.isProgressCreated()) {
			SaveUtils.createProgress();
		//} else SaveUtils.loadProgress();

        feelings = SaveUtils.loadLibrary();

        initBackgrounds();

		setScreen(new MainMenuScreen(this));
	}

	private void initTextures() {
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
		expBar = new Texture(Gdx.files.internal("interface/game_zone/emty_exp_bar.bmp"));
		left_arrow = new Texture(Gdx.files.internal("interface/game_zone/left_arrow.psd"));
		blure = new Texture(Gdx.files.internal("interface/game_zone/blure.png"));


		notification_off = new Texture(Gdx.files.internal("interface/settings/notification_off.png"));
		notification_on = new Texture(Gdx.files.internal("interface/settings/notification_on.png"));
		music_off = new Texture(Gdx.files.internal("interface/settings/music_off.png"));
		music_on = new Texture(Gdx.files.internal("interface/settings/music_on.png"));
		sound_off = new Texture(Gdx.files.internal("interface/settings/sound_off.png"));
		sound_on = new Texture(Gdx.files.internal("interface/settings/sound_on.png"));
		language_en = new Texture(Gdx.files.internal("interface/settings/language_en.png"));
		language_ru = new Texture(Gdx.files.internal("interface/settings/language_ru.png"));

		SaveUtils.atlas = new Texture(Gdx.files.internal("atlas.bmp"));
	}

	private void initBackgrounds() {
		mainBackground = new Texture(Gdx.files.internal("mainBackgrounds/" + ProgressData.getBackgroundName()));
		playBackground = new Texture(Gdx.files.internal("backgrounds/" + ProgressData.getBackgroundName()));
		storeBackground = new Texture(Gdx.files.internal("backgrounds/store.bmp"));
		goalsBackground = new Texture(Gdx.files.internal("backgrounds/goals.psd"));
		atlasBackground = new Texture(Gdx.files.internal("backgrounds/atlas.bmp"));
		settingsBackground = new Texture(Gdx.files.internal("backgrounds/settings.psd"));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		font.dispose();
		mainBackground.dispose();
		playBackground.dispose();
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
