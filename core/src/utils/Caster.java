package utils;

import com.bubbletrouble.game.libgdxcommon.BadTypeException;
import com.bubbletrouble.game.libgdxcommon.GameObject;
import com.bubbletrouble.game.objects.Player;

public class Caster
{
	public static Player castToPlayer(GameObject gameObject)
	{
		if (!(gameObject instanceof Player))
			throw new BadTypeException(Player.class.getName());
		Player player = (Player) gameObject;
		return player;
	}
}
