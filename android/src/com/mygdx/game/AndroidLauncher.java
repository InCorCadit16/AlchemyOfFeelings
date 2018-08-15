package com.mygdx.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.logic.Buy;
import com.mygdx.game.logic.SendEmail;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useCompass = false;
		config.useAccelerometer = false;
		initialize(new MyGdxGame(new AndroidSendEmail(),new AndroidBuy()), config);
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

	private class AndroidBuy implements Buy {
		@Override
		public void open(String title, String text) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

					builder.setTitle(title).
							setMessage(text).
							setNegativeButton("Нет", null).
							setPositiveButton("Беру", (dialog, which) -> {
								buy();
							}).create().show();
				}
			});

		}

		private void buy() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

					builder.setMessage("Покупки не доступны в бета-версии игры").
							setNeutralButton("Жалко :(", null).
							create().show();
				}
			});

		}
	}
}
