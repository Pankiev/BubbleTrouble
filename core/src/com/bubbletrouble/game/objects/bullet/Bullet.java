package com.bubbletrouble.game.objects.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.libgdxcommon.objects.GameObject;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.objects.player.AddPointsAction;
import com.bubbletrouble.game.objects.player.Player;
import com.bubbletrouble.game.packets.action.Action;
import com.bubbletrouble.game.packets.produce.ProduceBulletInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.interfaces.GameObjectsContainer;
import com.bubbletrouble.game.states.interfaces.PacketsSender;

import utils.Caster;

public class Bullet extends MovableGameObject
{
	private Vector2 flyingVector;
	private final float MAX_LIVE_TIME = 2.0f;
	private float livingTime = 0.0f;
	private Vector2 mousePosition;
	private long shooterId;

	public Bullet(State linkedState, Vector2 mousePosition, Vector2 sourcePosition, long shooterId)
	{
		super((Texture) ShooterGameClient.assets.get("bullet.png"), linkedState);
		this.mousePosition = mousePosition;
		this.shooterId = shooterId;
		setPosition(sourcePosition.x, sourcePosition.y);
		setMoveSpeed(6.5f);
		flyingVector = sourcePosition.sub(mousePosition);
		setPictureRotation();
	}

	private void setPictureRotation()
	{
		float angle = flyingVector.angle();
		setRotation(angle + 180);
	}

	@Override
	public void serverUpdate()
	{
		updateLivingTime();
		Action flightAction = new FlightAction();
		flightAction.makeAction(this);
		((PacketsSender) linkedState).sendAction(flightAction, getId());
		handlePossibleCollision();
		if (shouldBeDeleted())
			removeFromGame(this);
	}

	@Override
	public void clientUpdate()
	{

	}

	private void removeFromGame(GameObject object)
	{
		((GameObjectsContainer) linkedState).addToGarbage(object);
	}

	private void handlePossibleCollision()
	{
		GameObject collision = checkForCollision(((GameObjectsContainer) linkedState).getGameObjects());
		if (collision != null)
			handleCollision(collision);
	}

	private void handleCollision(GameObject collision)
	{
		removeFromGame(this);
		removeFromGame(collision);

		AddPointsAction addPointsAction = new AddPointsAction();
		addPointsAction.pointsToAdd = collision.getPointsValue();
		Player player = getShooter();
		addPointsAction.makeAction(player);
		((PacketsSender) linkedState).sendAction(addPointsAction, shooterId);
	}

	private Player getShooter()
	{
		return Caster.cast(((GameObjectsContainer) linkedState).getObject(shooterId), Player.class);
	}

	private void updateLivingTime()
	{
		livingTime += Gdx.graphics.getDeltaTime();
	}

	void updatePosition()
	{
		float angle = flyingVector.angle();
		flyingVector.set(getMoveSpeed(), getMoveSpeed());
		flyingVector.setAngle(angle);
		setRotation(angle);
		setPosition(getX() - flyingVector.x, getY() - flyingVector.y);
	}

	private boolean shouldBeDeleted()
	{
		return livingTime > MAX_LIVE_TIME;
	}

	@Override
	public ProduceInfo produceInfo()
	{
		ProduceBulletInfo info = new ProduceBulletInfo();
		info.id = getId();
		info.mouseX = mousePosition.x;
		info.mouseY = mousePosition.y;
		info.x = getX();
		info.y = getY();
		info.shooterId = shooterId;
		return info;
	}

}
