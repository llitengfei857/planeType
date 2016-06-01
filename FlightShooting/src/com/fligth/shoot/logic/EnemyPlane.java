package com.fligth.shoot.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fligth.shoot.res.GameResourceManager;

public class EnemyPlane extends Plane {

	private TextureRegion region;

	private Scene scene;

	private float stateTime;

	public EnemyPlane(Scene scene) {
		super();
		this.scene = scene;
		region = new TextureRegion(GameResourceManager.getInstance()
				.getTextureAltas("plane.pack").findRegion("e10"));
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
		int x = 320 / 2 - region.getRegionWidth() / 2;
		setX(MathUtils.random(x - 100, x + 100));
		setY(MathUtils.random(400, 450));
		addAction(Actions.moveBy(0, -600, 3));
		fire();
		stateTime = 0;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (region != null) {
			batch.draw(region, getX(), getY(), getWidth(), getHeight(),
					getWidth(), getHeight(), 1, 1, 0);
		}

		stateTime += Gdx.graphics.getRawDeltaTime() * 10;
		if (stateTime > 10 && stateTime < 20) {
			stateTime = 0;
			fire();
		}
	}

	@Override
	public void fire() {
		float centerX = getX() + getWidth() / 2f;
		float centerY = getY() + getHeight() / 2f;
		scene.addEnemyBullet(centerX - getWidth() / 6f * 2 - 10, centerY + 20);
		scene.addEnemyBullet(centerX + getWidth() / 6f * 2, centerY + 20);
	}

	@Override
	public void move(int curX, int curY) {

	}

	@Override
	public boolean collision(Plane plane) {
		PlayPlane play = (PlayPlane) plane;
		setRect(play.getX(), play.getY(), play.getWidth(), play.getHeight());
		if (overlaps(getX(), getY(), getWidth(), getHeight())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeOutOfBounds() {
		if (getX() < 0 || getY() < -getHeight()) {
			return true;
		}
		return false;
	}

}
