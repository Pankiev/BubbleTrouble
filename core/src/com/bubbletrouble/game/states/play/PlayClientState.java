package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;
import com.bubbletrouble.game.server.packets.player.PlayerPositionUpdateInfo;
import com.esotericsoftware.kryonet.Client;

public class PlayClientState extends PlayState
{
	Client client;

	public PlayClientState(Client client)
	{
		this.client = client;
		addPlayer(new PlayerAddInfo(client.getID()));
		inputHandler = new PlayInputHandler(this);
		activateInputHandler();
	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
	}

	@Override
	public void update()
	{
	}

	public void update(PlayerPositionUpdateInfo updateInfo)
	{
		Player playerToUpdate = getPlayer(updateInfo.id);
		if (updateInfo.x != -1)
			playerToUpdate.setX(updateInfo.x);
		if (updateInfo.y != -1)
			playerToUpdate.setY(updateInfo.y);
	}
}
