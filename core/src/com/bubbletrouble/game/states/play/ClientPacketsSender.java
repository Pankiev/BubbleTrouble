package com.bubbletrouble.game.states.play;

import com.bubbletrouble.game.server.packets.action.Action;
import com.bubbletrouble.game.server.packets.action.CollisionAction;

public interface ClientPacketsSender
{

	void sendAction(Action action, long id);

	void sendAction(CollisionAction action, long id);

	void send(Object info);

}