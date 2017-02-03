package com.bubbletrouble.game.states.play.actions;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Player;
import com.bubbletrouble.game.server.packets.Action;
import com.bubbletrouble.game.server.packets.Registerable;

import utils.Caster;

@Registerable
public class UpdateAngleAction implements Action
{
	private float angleChange;
	public Vector2 mousePosition = new Vector2();
	@Override
	public void makeAction(GameObject gameObject)
	{
		Player player = Caster.castToPlayer(gameObject);
		player.updateAngle(mousePosition);
		angleChange = player.getRotation();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setRotation(angleChange);
	}


}
