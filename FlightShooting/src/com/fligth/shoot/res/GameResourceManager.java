package com.fligth.shoot.res;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameResourceManager extends ResourceManager {

	private static GameResourceManager instance = new GameResourceManager();

	private GameResourceManager() {
		super();
	}

	public static GameResourceManager getInstance() {
		return instance;
	}

	@Override
	public void loadResource() {
		load("plane.pack", TextureAtlas.class);
	}

}
