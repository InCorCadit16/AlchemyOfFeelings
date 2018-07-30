package com.mygdx.game;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.SendEmail;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;
		initialize(new MyGdxGame(new AndroidSendEmail()), config);
	}

	private class AndroidSendEmail implements SendEmail {
		@Override
		public void sendIntent() {
			Uri email = Uri.parse("incorcadit16@gmail.com");
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("message/rfc822");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"incorcadit16@gmail.com"});
			intent.putExtra(Intent.EXTRA_SUBJECT,"Alchemy of Feelings: напиши нам");
			intent.putExtra(Intent.EXTRA_TEXT, "Отзыв, сообщение о баге или предложения:");
			try {
				startActivity(Intent.createChooser(intent, "Send mail..."));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
