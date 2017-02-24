package com.bubbletrouble.game.states.play;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.bubbletrouble.game.packets.action.CollisionAction;

public interface ClientPacketsSender
{
	void sendAction(Action action, long id);

	void sendAction(CollisionAction action, long id);

	void send(Object info);

}