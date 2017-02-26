package utils;

import com.bubbletrouble.game.libgdxcommon.exception.BadTypeException;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.esotericsoftware.minlog.Log;

public class Caster
{
	public static <T> T cast(GameObject gameObject, Class<T> type)
	{
		if (gameObject == null)
		{
			Log.warn("Caster: received null, returning null");
			return null;
		}
		if (!(type.isInstance(gameObject)))
			throw new BadTypeException(type.getName(), gameObject.getClass().getName());
		T object = (T) gameObject;
		return object;
	}

}
