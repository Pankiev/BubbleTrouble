package com.bubbletrouble.game.packets.requsets;

import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.states.play.PlayState;

import utils.Caster;

@Registerable
public class ShootRequest implements Request
{
	public long id;
	public float mouseX;
	public float mouseY;

	@Override
	public void perform(PlayState state)
	{
		Player player = Caster.cast(state.getObject(id), Player.class);
		player.shootIfCan(mouseX, mouseY);
	}
}
