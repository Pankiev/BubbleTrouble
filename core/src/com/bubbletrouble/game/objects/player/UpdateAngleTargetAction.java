package com.bubbletrouble.game.objects.player;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.Action;

import utils.Caster;

@Registerable
public class UpdateAngleTargetAction implements Action
{
	private float angleChange;
	public Vector2 mousePosition = new Vector2();

	@Override
	public void makeAction(GameObject gameObject)
	{
		Player player = Caster.cast(gameObject, Player.class);
		player.updateAngleTargetValue(mousePosition);
		angleChange = player.getRotation();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		Player player = Caster.cast(gameObject, Player.class);
		player.setRotationTarget(angleChange);
	}


}
