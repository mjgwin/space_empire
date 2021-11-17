package com.gdx.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.game.GdxGame;
import com.gdx.game.MainGameScreen;

public class LoadingScreen implements Screen{
	
	private final int BAR_WIDTH = 500;
	private final int BAR_HEIGHT = 50;
	private final int CHUNK_WIDTH = 100;
	
	private int currWidth = 0;
	private int spacing = 10;
	private int delay = 100;
	
	private GdxGame game;
	private MainGameScreen mainGame;
	private int screenWidth, screenHeight;
	
	private SpriteBatch batch;
	
	public LoadingScreen(GdxGame game) {
		this.game = game;
		mainGame = new MainGameScreen(game);
		batch = new SpriteBatch();
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		loadGame();
	}
			//setupControls();
			//loadTextures();
			//setupWorld();
			//setupRendering();
			//setupUI();
	
	private void loadGame() {
		
		Gdx.app.postRunnable(new Runnable() {
			@Override
			public void run() {
				mainGame.setupControls();
				currWidth += CHUNK_WIDTH;
				
				Gdx.graphics.requestRendering();
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						mainGame.loadTextures();
						currWidth += CHUNK_WIDTH;
						
						Gdx.graphics.requestRendering();
						try {
							Thread.sleep(delay);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						Gdx.app.postRunnable(new Runnable() {
							@Override
							public void run() {
								mainGame.setupWorld();
								currWidth += CHUNK_WIDTH;
								
								Gdx.graphics.requestRendering();
								try {
									Thread.sleep(delay);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										mainGame.setupRendering();
										currWidth += CHUNK_WIDTH;
										
										Gdx.graphics.requestRendering();
										try {
											Thread.sleep(delay);
										} catch (InterruptedException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										Gdx.app.postRunnable(new Runnable() {
											@Override
											public void run() {
												mainGame.setupUI();
												currWidth += CHUNK_WIDTH;
												
												Gdx.graphics.requestRendering();
												try {
													Thread.sleep(delay);
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												game.setScreen(mainGame);
												
											}
										});
									}
								});
							}
						});
					}
				});
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
		batch.begin();
		batch.draw(UITextures.LOADING_BAR_OUTLINE, 0, 0, BAR_WIDTH, BAR_HEIGHT);
		batch.draw(UITextures.LOADING_BAR, spacing, spacing, currWidth, BAR_HEIGHT - spacing * 3);
		batch.end();
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
