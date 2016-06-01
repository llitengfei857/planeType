package com.fligth.shoot.logic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.fligth.shoot.res.GameResourceManager;

/**
 * µÐÈË×Óµ¯
 * 
 * @author ÀîÌÚ·É
 * 
 */
public class EnemyBullet extends Bullet {

	private TextureRegion region;

	public EnemyBullet() {
		region = new TextureRegion(GameResourceManager.getInstance()
				.getTextureAltas("plane.pack").findRegion("bullet"));
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
		addAction(Actions.moveBy(0, -700, 1f));
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
	public boolean collision(Plane plane) {
		PlayPlane play = (PlayPlane) plane;
		setRect(play.getX(), play.getY(), play.getWidth(), play.getHeight());
		if (overlaps(getX(), getY(), getWidth(), getHeight())) {
			return true;
		}
		return false;
	}

}
