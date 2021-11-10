package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.game.GdxGame;
import com.gdx.game.MainGameScreen;

public class MainMenuScreen implements Screen{
	
	private GdxGame game;
	private Stage stage;
	private int screenWidth, screenHeight;
	
	public MainMenuScreen(GdxGame game) {
		this.game = game;
		UITextures.init();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		setupStage();
	}
	
	private void setupStage() {
		Table table = new Table();
		table.setFillParent(true);
		table.setTransform(true);
		table.setScale(1.5f);
		table.setOrigin(screenWidth / 2 - table.getWidth() / 2, screenHeight / 2 - table.getHeight() / 2);
		//table.setDebug(true);
		stage.addActor(table);

		Skin skin = new Skin(Gdx.files.internal("skins/neon/neon-ui.json"));

		TextButton newGame = new TextButton("New Game", skin);
		TextButton preferences = new TextButton("Preferences", skin);
		TextButton exit = new TextButton("Exit", skin);
		Label titleLabel = new Label("SPACE EMPIRE", skin);
		table.add(titleLabel).top();
		table.row();
		table.add(newGame).fillX().uniformX();
		table.row();
		table.add(preferences).fillX().uniformX();
		table.row();
		table.add(exit).fillX().uniformX();
		
		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new LoadingScreen(game));
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
			}
		});
	
		
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
