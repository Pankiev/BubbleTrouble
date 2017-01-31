package com.bubbletrouble.game.server;

import com.esotericsoftware.kryonet.Server;

public class ServerLauncher
{
	public static void main(String[] args)
	{
		Server server = new Server();
		server.start();
		server.close();
	}
}
