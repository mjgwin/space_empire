package com.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.game.GdxGame;
import com.gdx.game.utils.GameLogger;

public class DesktopLauncher {
	public static void main (String[] arg) {
		try {
			LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
			config.width = 1280;
			config.height = 720;
			config.title = "Space_Empire";
			config.vSyncEnabled = true;
			config.foregroundFPS = 60;
			config.backgroundFPS = 60;
			new LwjglApplication(new GdxGame(), config);
		}catch(Exception e) {
			GameLogger.errorLog("application failed to launch: check config settings");
			e.printStackTrace();
		}
		GameLogger.log("application launched");
	}
}
