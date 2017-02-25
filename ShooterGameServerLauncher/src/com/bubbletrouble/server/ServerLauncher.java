package com.bubbletrouble.server;

import java.io.IOException;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bubbletrouble.game.ShooterGameServer;

public class ServerLauncher {
	public static void main(String[] arg) throws IOException
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ShooterGameServer(), config);
	}
}
