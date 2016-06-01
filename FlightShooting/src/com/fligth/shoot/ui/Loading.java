package com.fligth.shoot.ui;

import com.badlogic.gdx.Screen;
import com.fligth.shoot.GameMain;
import com.fligth.shoot.logic.Scene;
import com.fligth.shoot.res.GameResourceManager;

public class Loading implements Screen {

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		if (GameResourceManager.getInstance().updateAssetManager()) {
			GameMain.instance.setScreen(new Scene());
		}
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

}
