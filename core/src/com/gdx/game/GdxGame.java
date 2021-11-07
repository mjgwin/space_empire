package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.game.ui.MainMenuScreen;
import com.gdx.game.utils.GameLogger;

public class GdxGame extends Game {
	
	private MainMenuScreen menuScreen;
	
	
	@Override
	public void create () {
		menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
		GameLogger.log("changed to main menu screen");
	}

	@Override
	public void render () {
		//ScreenUtils.clear(0, 0, 0, 1);
		super.render();

	}
	
	@Override
	public void dispose () {
	
	}
}
