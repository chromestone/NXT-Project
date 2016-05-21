package com.github.chromestone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.github.chromestone.Application;

/**
 * Contains the main method that launches the "application"
 * 
 * @author Derek Zhang
 * 
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 400;
		new LwjglApplication(new Application(), config);
	}
}
