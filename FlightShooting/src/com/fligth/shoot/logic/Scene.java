package com.fligth.shoot.logic;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.fligth.shoot.GameMain;
import com.fligth.shoot.res.GameResourceManager;

public class Scene implements Screen, InputProcessor {

	private TextureRegion background;

	private Stage uiStage;

	private Stage stage;

	private InputMultiplexer input;

	private TextureAtlas atlas;

	private int width, height;

	private Plane playPlane;

	private ArrayList<EnemyPlane> enemyPlaneList;

	private ArrayList<EnemyPlane> deleteEnemyPlaneLists;

	private ArrayList<Bullet> enemyBulletList;

	private ArrayList<Bullet> deleteEnemyBulletList;

	public Scene() {
		width = GameMain.instance.getWidth();
		height = GameMain.instance.getHeight();
		initRes();
		initGame();
	}

	private void initGame() {
		playPlane = new PlayPlane();
		addPlane(playPlane);

		enemyPlaneList = new ArrayList<EnemyPlane>();
		deleteEnemyPlaneLists = new ArrayList<EnemyPlane>();
		Timer t = new Timer();
		t.scheduleTask(new Task() {
			@Override
			public void run() {
				addPlane(new EnemyPlane(Scene.this));
			}
		}, 1, 1);

		enemyBulletList = new ArrayList<Bullet>();
		deleteEnemyBulletList = new ArrayList<Bullet>();
	}

	private void initRes() {
		atlas = GameResourceManager.getInstance().getTextureAltas("plane.pack");
		for (Texture t : atlas.getTextures()) {
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		Texture texture = new Texture(Gdx.files.internal("map1.png"));
		background = new TextureRegion(texture);
		background.setRegion(0, 0, 320, 480);
		background.getTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		uiStage = new Stage(new FillViewport(width, height));
		stage = new Stage(new FillViewport(width, height));
		input = new InputMultiplexer();
		input.addProcessor(uiStage);
		input.addProcessor(stage);
		input.addProcessor(this);
		Gdx.input.setInputProcessor(input);
	}

	private <T> void addPlane(T plane) {
		if (plane instanceof PlayPlane) {
			stage.addActor((Plane) plane);
		} else {
			stage.addActor((EnemyPlane) plane);
			enemyPlaneList.add((EnemyPlane) plane);
		}
	}

	public <T> void addEnemyBullet(float x, float y) {
		EnemyBullet bullet = new EnemyBullet();
		bullet.setPosition(x, y);
		enemyBulletList.add(bullet);
		stage.addActor(bullet);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		uiStage.draw();
		uiStage.act();
		Batch batch = uiStage.getBatch();
		batch.begin();
		batch.draw(background, 0, 0, background.getRegionWidth(),
				background.getRegionHeight(), 320, 480, 1, 1, 0);
		background.scroll(0, -0.001f);
		batch.end();

		stage.act();
		stage.draw();
		checkCollision();
	}

	private void checkCollision() {
		for (EnemyPlane enemy : enemyPlaneList) {
			// 敌机与主机碰撞
			if (enemy.collision(playPlane)) {
				enemy.remove();
				deleteEnemyPlaneLists.add(enemy);
			}
			// 敌机超出界框
			if (enemy.removeOutOfBounds()) {
				enemy.remove();
			}
		}

		for (Bullet bullet : enemyBulletList) {
			if (bullet.collision(playPlane)) {
				bullet.remove();
				deleteEnemyBulletList.add(bullet);
			}
		}

		for (Bullet bullet : deleteEnemyBulletList) {
			enemyBulletList.remove(bullet);
		}
		deleteEnemyBulletList.clear();

		// 消除
		for (EnemyPlane enemy : deleteEnemyPlaneLists) {
			enemyPlaneList.remove(enemy);
		}
		deleteEnemyPlaneLists.clear();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}

	private int curX, curY, lastX, lastY;

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector2 oldPostion = new Vector2(screenX, screenY);
		Vector2 newPostion = stage.screenToStageCoordinates(oldPostion);
		int x = (int) newPostion.x;
		int y = (int) newPostion.y;

		lastX = curX;
		lastY = curY;
		curX = x;
		curY = y;
		if (lastX != 0) {
			int offsetX = curX - lastX;
			int offsetY = curY - lastY;
			playPlane.move(offsetX, offsetY);
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		curX = 0;
		curY = 0;
		lastX = 0;
		lastY = 0;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
}
