package com.fligth.shoot.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fligth.shoot.res.GameResourceManager;

public class PlayPlane extends Plane {

	private TextureRegion region;

	public PlayPlane() {
		region = new TextureRegion(GameResourceManager.getInstance()
				.getTextureAltas("plane.pack").findRegion("plan0"));
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
		int playW = (int) (getWidth() / 2);
		setPosition(160 - playW, -30);
		addAction(Actions.moveBy(0, 100, 1));
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (region != null) {
			batch.draw(region, getX(), getY(), getWidth(), getHeight(),
					getWidth(), getHeight(), 1, 1, 0);
		}
	}

	@Override
	public void move(int offsetX, int offsetY) {
		if (hasInGameBoundaries(offsetX, offsetY)) {
			setX(getX() + offsetX);
			setY(getY() + offsetY);
		}
	}

	private boolean hasInGameBoundaries(int offsetX, int offsetY) {
		if (getX() + offsetX > 0
				&& (getX() + offsetX + region.getRegionWidth()) < 320
				&& getY() + offsetY > 0
				&& (getY() + offsetY + region.getRegionHeight()) < 480) {
			return true;
		}
		return false;
	}

	@Override
	public void fire() {

	}

	@Override
	public boolean collision(Plane plane) {
		return false;
	}

	@Override
	public boolean removeOutOfBounds() {
		return false;
	}
}
