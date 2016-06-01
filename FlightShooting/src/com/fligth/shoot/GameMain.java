package com.fligth.shoot;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.fligth.shoot.res.GameResourceManager;
import com.fligth.shoot.ui.Loading;

public class GameMain extends Game {
	public static GameMain instance;

	private int width, height;

	@Override
	public void create() {
		instance = this;
		width = 320;
		height = 480;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		GameResourceManager.getInstance().loadResource();
		setScreen(new Loading());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
