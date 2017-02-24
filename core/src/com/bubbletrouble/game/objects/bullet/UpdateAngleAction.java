package com.bubbletrouble.game.objects.bullet;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.packets.action.Action;

import utils.Caster;

@Registerable
public class UpdateAngleAction implements Action
{
	private float angleChange;
	public Vector2 mousePosition = new Vector2();

	@Override
	public void makeAction(GameObject gameObject)
	{
		Player player = Caster.cast(gameObject, Player.class);
		player.updateAngle(mousePosition);
		angleChange = player.getRotation();
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		gameObject.setRotation(angleChange);
	}


}
