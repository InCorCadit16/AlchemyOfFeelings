package com.mygdx.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Buy;
import com.mygdx.game.logic.SendEmail;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGame(new DesktopSendEmail(),new DesktopBuy()), config);
	}

	private static class DesktopSendEmail implements SendEmail {
		@Override
		public void sendIntent() {

		}
	}

	private static class DesktopBuy implements Buy {
		@Override
		public void open(String title, String text) {

		}
	}
}
