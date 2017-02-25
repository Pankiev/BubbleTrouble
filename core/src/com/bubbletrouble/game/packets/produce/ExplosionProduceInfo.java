package com.bubbletrouble.game.packets.produce;

import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.kryonetcommon.Registerable;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.objects.explosion.Explosion;

@Registerable
public class ExplosionProduceInfo extends ProduceInfo
{
	public Vector2 position;

	@Override
	public GameObject produce(State linkedState)
	{
		Explosion explosion = new Explosion(linkedState);
		explosion.setPosition(position.x, position.y);
		explosion.setId(id);
		return explosion;
	}

}
