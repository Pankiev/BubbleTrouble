package com.bubbletrouble.game.states.play;

import java.util.List;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.ActionInfo;
import com.bubbletrouble.game.server.packets.player.PlayerPositionUpdateInfo;
import com.esotericsoftware.kryonet.Server;
import com.rits.cloning.Cloner;

public class PlayServerState extends PlayState
{
	Cloner cloner = new Cloner();
	Javers javers = JaversBuilder.javers().build();
	Server server;

	public PlayServerState(Server server)
	{
		this.server = server;
	}

	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void render(SpriteBatch batch)
	{
	}


	public PlayerPositionUpdateInfo makeActionAndGetDifference(ActionInfo actionInfo)
	{
		Player player = getPlayer(actionInfo.targetId);
		Player playerClone = cloner.deepClone(player);
		super.makeAction(actionInfo);
		Diff difference = javers.compare(playerClone, player);
		List<ValueChange> changes = difference.getChangesByType(ValueChange.class);
		PlayerPositionUpdateInfo info = produceUpdateInfo(actionInfo.targetId, changes);
		return info;
	}

	private PlayerPositionUpdateInfo produceUpdateInfo(int id, List<ValueChange> changes)
	{
		PlayerPositionUpdateInfo info = new PlayerPositionUpdateInfo(id);
		for (ValueChange valueChange : changes)
			if (valueChange.getPropertyName() == "x")
				info.x = (float) valueChange.getRight();
			else if (valueChange.getPropertyName() == "y")
				info.y = (float) valueChange.getRight();
		return info;
	}

}
