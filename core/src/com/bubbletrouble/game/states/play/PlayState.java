package com.bubbletrouble.game.states.play;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;
import com.bubbletrouble.game.server.packets.player.PlayerRemoveInfo;
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

	public void addPlayer(PlayerAddInfo info)
	{
		Player newPlayer = new Player();
		newPlayer.setX(info.x);
		newPlayer.setY(info.y);
		players.put(info.id, newPlayer);
	}

	public void removePlayer(PlayerRemoveInfo playerInfo)
	{
		players.remove(playerInfo.id);
	}

	public Player getPlayer(int id)
	{
		return players.get(id);
	}

	public PlayerAddInfo[] getPlayersInfo()
	{
		int playersCount = countPlayers();
		PlayerAddInfo[] playersInfo = new PlayerAddInfo[playersCount];
		int i=0;
		for (Entry<Integer, Player> player : players.entrySet())
		{
			PlayerAddInfo playerInfo = new PlayerAddInfo();
			playerInfo.id = player.getKey();
			playerInfo.x = player.getValue().getX();
			playerInfo.y = player.getValue().getY();
			playersInfo[i] = playerInfo;
			i++;
		}

		return playersInfo;
	}

	private int countPlayers()
	{
		return players.values().size();
	}

	public void addPlayers(PlayerAddInfo[] ids)
	{
		for (PlayerAddInfo playerInfo : ids)
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
