package com.gdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Interpolation.Swing;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.game.ui.InGameTimeUI;
import com.gdx.game.ui.PlanetUI;
import com.gdx.game.ui.ResourceBar;
import com.gdx.game.ui.ShipUI;
import com.gdx.game.ui.UITextures;
import com.gdx.game.utils.GameLogger;
import com.gdx.game.world.Empire;
import com.gdx.game.world.Planet;
import com.gdx.game.world.ResourceTextures;
import com.gdx.game.world.Ship;
import com.gdx.game.world.Star;
import com.gdx.game.world.SurfaceTextures;
import com.gdx.game.world.World;
import com.gdx.game.world.WorldTextures;

public class MainGameScreen implements Screen, InputProcessor {

	private int worldWidth = 20000;
	private int worldHeight = 20000;

	private GdxGame game;
	private World world;
	private SpriteBatch batch;
	private ShapeRenderer shapeRender;
	private OrthographicCamera camera;

	private boolean up, down, left, right, zoomIn, zoomOut;
	private int cameraSpeed = 5;
	private float zoomSpeed = 0.01f;

	private PlanetUI planetUI;
	private ShipUI shipUI;
	private ResourceBar resourceBar;
	private InGameTimeUI inGameTimeUI;

	public MainGameScreen(GdxGame game) {
		this.game = game;
		//setupControls();
		//loadTextures();
		//setupWorld();
		//setupRendering();
		//setupUI();
	}

	public void setupControls() {
		try {
			up = false;
			down = false;
			left = false;
			right = false;
			zoomIn = false;
			zoomOut = false;
			GameLogger.log("setup controls");
		} catch (Exception e) {
			GameLogger.errorLog("failed to setup controls");
			e.printStackTrace();
		}
	}

	public void loadTextures() {
		try {
			WorldTextures.init();
			SurfaceTextures.init();
			ResourceTextures.init();
			GameLogger.log("textures loaded");
		} catch (Exception e) {
			GameLogger.errorLog("failed to load textures");
			e.printStackTrace();
		}
	}

	public void setupWorld() {
		try {
			world = new World(worldWidth, worldHeight);
			//world.createUniverse(100);
			world.createUniverse2(400);
			world.createEmpire("MainEmpire", true);
			//world.spawnEmpireOnStartingPlanet(world.getPlayerEmpire());
			Sprite startingSprite = world.getPlayerEmpire().getStartingPlanet().getSprite();
			world.spawnShip((int) startingSprite.getX(), (int) startingSprite.getY(), WorldTextures.SHIP_TEX, "Ship1",
					1, world.getPlayerEmpire());
			GameLogger.log("world created");
		} catch (Exception e) {
			GameLogger.errorLog("failed to setup world");
			e.printStackTrace();
		}
	}

	public void setupRendering() {
		try {
			batch = new SpriteBatch();
			shapeRender = new ShapeRenderer();
			camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Sprite startingSprite = world.getPlayerEmpire().getStartingPlanet().getSprite();
			camera.position.set(startingSprite.getX() + (startingSprite.getWidth() / 2),
					startingSprite.getY() + (startingSprite.getHeight() / 2), 0);
			GameLogger.log("render engine started");
		} catch (Exception e) {
			GameLogger.errorLog("failed to setup render engine");
			e.printStackTrace();
		}

	}

	public void setupUI() {
		try {
			resourceBar = new ResourceBar(this, world.getPlayerEmpire());
			inGameTimeUI = new InGameTimeUI(this);
			Gdx.input.setInputProcessor(this);
			GameLogger.log("user interface started");
		} catch (Exception e) {
			GameLogger.errorLog("failed to setup UI");
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		
		ScreenUtils.clear(0, 0, 0, 1);
		updateAll();

		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();

		renderStars();
		renderPlanets();
		renderShips();

		batch.end();

		handlePlanetUI();
		handleShipUI();
		handleGameUI();
		
		Gdx.graphics.setTitle("Space_Empire " + "(FPS: " + Gdx.graphics.getFramesPerSecond() + ")");
	}

	private void updateAll() {
		updateCamera();
		world.update();
	}

	private void updateCamera() {
		if (up) {
			camera.position.y += cameraSpeed;
		}
		if (down) {
			camera.position.y -= cameraSpeed;
		}
		if (left) {
			camera.position.x -= cameraSpeed;
		}
		if (right) {
			camera.position.x += cameraSpeed;
		}
		
		if(zoomIn) {
			camera.zoom -= zoomSpeed;
		}
		if(zoomOut) {
			camera.zoom += zoomSpeed;
		}

		camera.update();
	}

	private void renderStars() {
		ArrayList<Star> stars = world.getStars();
		for (Star s : stars) {
			if(starInView(s)) {
				batch.draw(WorldTextures.STAR_TEX, s.getX(), s.getY(), Star.DEFAULT_SIZE, Star.DEFAULT_SIZE);
			}
		}
	}
	
	private boolean starInView(Star s) {
		Vector3 proj = new Vector3();
		camera.project(proj.set(s.getX(), s.getY(), 0));
		Rectangle screenBounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		return screenBounds.contains(proj.x, proj.y);
	}

	private void renderPlanets() {
		ArrayList<Planet> planets = world.getPlanets();
		for (Planet p : planets) {
			if(planetInView(p)) {
				p.getSprite().draw(batch);
			}
		}

		world.getStartingPlanetSymbol().draw(batch);
	}
	
	private boolean planetInView(Planet p) {
		int buffer = 400;
		Vector3 proj = new Vector3();
		Sprite pSprite = p.getSprite();
		camera.project(proj.set(pSprite.getX() + pSprite.getWidth() / 2 , pSprite.getY() + pSprite.getHeight() / 2, 0));
		Rectangle screenBounds = new Rectangle(-buffer, -buffer, Gdx.graphics.getWidth() + buffer * 2, Gdx.graphics.getHeight() + buffer * 2);
		return screenBounds.contains(proj.x, proj.y);
	}

	private void renderShips() {
		ArrayList<Ship> ships = world.getShips();
		for (Ship sh : ships) {
			sh.getSprite().draw(batch);
		}
	}

	private void handlePlanetUI() {
		ArrayList<Planet> planets = world.getPlanets();

		for (Planet p : planets) {
			if (p.isSelected()) {
				shapeRender.setProjectionMatrix(camera.combined);
				shapeRender.begin(ShapeType.Line);
				shapeRender.setColor(1, 0, 0, 1);
				Sprite s = p.getSprite();
				shapeRender.circle(s.getX() + s.getWidth() / 2, s.getY() + s.getHeight() / 2, s.getWidth() / 2);
				shapeRender.end();

				if (planetUI != null) {
					planetUI.act();
					planetUI.draw();

				}
			}
		}
	}

	private void handleShipUI() {
		ArrayList<Ship> ships = world.getShips();

		for (Ship sh : ships) {
			if (sh.isSelected()) {
				shapeRender.setProjectionMatrix(camera.combined);
				shapeRender.begin(ShapeType.Line);
				shapeRender.setColor(1, 0, 0, 1);
				Sprite shSprite = sh.getSprite();
				shapeRender.circle(shSprite.getX() + shSprite.getWidth() / 2,
						shSprite.getY() + shSprite.getHeight() / 2, shSprite.getWidth() / 2);
				shapeRender.end();

				if (shipUI != null) {
					shipUI.act();
					shipUI.draw();
				}
			}
		}
	}

	private void handleGameUI() {
		resourceBar.act();
		resourceBar.draw();
		inGameTimeUI.act();
		inGameTimeUI.draw();
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
		WorldTextures.dispose();
		SurfaceTextures.dispose();
		ResourceTextures.dispose();
		UITextures.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case (Input.Keys.W):
			up = true;
			break;
		case (Input.Keys.A):
			left = true;
			break;
		case (Input.Keys.S):
			down = true;
			break;
		case (Input.Keys.D):
			right = true;
			break;
		case (Input.Keys.EQUALS):
			zoomIn = true;
			break;
		case (Input.Keys.MINUS):
			zoomOut = true;
			break;
		}
		
			
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case (Input.Keys.W):
			up = false;
			break;
		case (Input.Keys.A):
			left = false;
			break;
		case (Input.Keys.S):
			down = false;
			break;
		case (Input.Keys.D):
			right = false;
			break;
		case (Input.Keys.EQUALS):
			zoomIn = false;
			break;
		case (Input.Keys.MINUS):
			zoomOut = false;
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		ArrayList<Planet> planets = world.getPlanets();
		ArrayList<Ship> ships = world.getShips();
		Vector3 unProj = new Vector3();
		camera.unproject(unProj.set(screenX, screenY, 0));
		for (Planet p : planets) {

			if (p.getSprite().getBoundingRectangle().contains(unProj.x, unProj.y) && button == Input.Buttons.LEFT) {
				p.setSelected(!p.isSelected());
				if (p.isSelected()) {
					for (Planet p2 : planets) {
						if (p2.equals(p))
							continue;
						p2.setSelected(false);
					}
					planetUI = new PlanetUI(p, this);
					if (shipUI != null)
						shipUI.hide();
				}
			}

		}

		for (Ship s : ships) {
			if (s.getSprite().getBoundingRectangle().contains(unProj.x, unProj.y) && button == Input.Buttons.LEFT) {
				s.setSelected(!s.isSelected());

			}

			if (s.isSelected()) {
				if (button == Input.Buttons.RIGHT) {
					s.move((int) unProj.x, (int) unProj.y);
				}
				for (Ship s2 : ships) {
					if (s2.equals(s))
						continue;
					s2.setSelected(false);
				}
				shipUI = new ShipUI(s, this);
				if (planetUI != null)
					planetUI.hide();

			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

}
