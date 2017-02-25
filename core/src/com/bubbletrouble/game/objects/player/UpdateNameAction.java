package com.bubbletrouble.game.objects.player;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.packets.action.Action;

@Registerable
public class UpdateNameAction implements Action
{
	public String name;

	@Override
	public void makeAction(GameObject gameObject)
	{
		gameObject.setName(name);
	}

	@Override
	public void applyChangesToOther(GameObject gameObject)
	{
		makeAction(gameObject);
	}

}
