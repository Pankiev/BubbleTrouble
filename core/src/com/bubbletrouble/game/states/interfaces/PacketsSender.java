package com.bubbletrouble.game.states.interfaces;

import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.action.CollisionAction;

public interface PacketsSender
{
	void sendAction(Action action, long id);

	void sendAction(CollisionAction action, long id);

	void send(Object info);

}