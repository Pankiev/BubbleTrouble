package com.bubbletrouble.game.states.play;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.ProduceInfo;
import com.bubbletrouble.game.server.packets.player.PlayerAddInfo;
import com.bubbletrouble.game.server.packets.player.PlayerRemoveInfo;
import com.esotericsoftware.minlog.Log;

public abstract class PlayState extends State
{
	Map<Integer, Player> players = new TreeMap<>();
	List<GameObject> gameObjects = new ArrayList<>();

	@Override
	public void render(SpriteBatch batch)
	{
		for (GameObject player : gameObjects)
			player.render(batch);
	}

	@Override
	public void update()
	{
		for (GameObject player : gameObjects)
			player.update();
	}

	public void addPlayer(PlayerAddInfo info)
	{
		Player newPlayer = (Player) info.produce();
		players.put(info.id, newPlayer);
		gameObjects.add(newPlayer);
	}

	public void removePlayer(PlayerRemoveInfo playerInfo)
	{
		Player player = players.get(playerInfo.id);
		players.remove(playerInfo.id);
		gameObjects.remove(player);
	}

	public Player getPlayer(int id)
	{
		return players.get(id);
	}

	public void addObject(GameObject gameObject)
	{
		gameObjects.add(gameObject);
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

	public void addObject(ProduceInfo info)
	{
		GameObject object = info.produce();
		gameObjects.add(object);
	}

	public void addPlayers(PlayerAddInfo[] ids)
	{
		for (PlayerAddInfo playerInfo : ids)
		{
			Log.info("Adding player " + playerInfo + "to " + ((PlayClientState) this).client.getID());
			addPlayer(playerInfo);
		}
	}

	public ProduceInfo[] getGameObjectsWithoutPlayersInfo()
	{
		ProduceInfo[] info = new ProduceInfo[gameObjects.size() - players.size()];
		int i = 0;
		for (GameObject gameObject : gameObjects)
		{
			if (!(gameObject instanceof Player))
			{
				info[i] = gameObject.produceInfo();
				i++;
			}
		}
		return info;
	}
}
