package com.fligth.shoot.logic;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Plane extends Actor {

	private Rectangle rect;

	public Plane() {
		rect = new Rectangle();
	}

	public abstract void move(int curX, int curY);

	public abstract boolean collision(Plane plane);

	public abstract boolean removeOutOfBounds();

	public abstract void fire();

	public Rectangle getRect() {
		return rect;
	}

	/**
	 * 设置大小宽高
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setRect(float x, float y, float width, float height) {
		rect.set(x, y, width, height);
	}

	/**
	 * 覆盖
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean overlaps(float x, float y, float width, float height) {
		return x < rect.x + rect.width && x + width > rect.x
				&& y < rect.y + rect.height && y + height > rect.y;
	}

}
