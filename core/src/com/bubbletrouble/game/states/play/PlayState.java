package com.bubbletrouble.game.states.play;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.PlayerInfo;
import com.esotericsoftware.minlog.Log;

public abstract class PlayState extends State
{
	Map<Integer, Player> players = new TreeMap<>();

	@Override
	public void render(SpriteBatch batch)
	{
		for (Player player : players.values())
			player.render(batch);
	}

	@Override
	public void update()
	{
		for (Player player : players.values())
			player.update();
	}

	public void addPlayer(PlayerInfo info)
	{
		Player newPlayer = new Player();
		newPlayer.x = info.x;
		newPlayer.y = info.y;
		players.put(info.id, newPlayer);
	}

	public void removePlayer(int id)
	{
		players.remove(id);
	}

	public Player getPlayer(int id)
	{
		return players.get(id);
	}

	public PlayerInfo[] getPlayersInfo()
	{
		int playersCount = countPlayers();
		PlayerInfo[] playersInfo = new PlayerInfo[playersCount];
		int i=0;
		for (Entry<Integer, Player> player : players.entrySet())
		{
			PlayerInfo playerInfo = new PlayerInfo();
			playerInfo.id = player.getKey();
			playerInfo.x = player.getValue().x;
			playerInfo.y = player.getValue().y;
			playersInfo[i] = playerInfo;
			i++;
		}

		return playersInfo;
	}

	private int countPlayers()
	{
		int size = 0;
		for (Player player : players.values())
			size++;
		return size;
	}

	public void addPlayers(PlayerInfo[] ids)
	{
		for (PlayerInfo playerInfo : ids)
		{
			Log.info("Adding player " + playerInfo + "to " + ((PlayClientState) this).client.getID());
			addPlayer(playerInfo);
		}
	}

	public void makeAction(ActionInfo actionInfo)
	{
		Player player = players.get(actionInfo.targetId);
		Action action = actionInfo.action;
		action.makeAction(player);
	}
}
