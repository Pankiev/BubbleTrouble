package com.bubbletrouble.game.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.ShooterGame;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.kryonetcommon.IdProvider;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.packets.produce.PlayerProduceInfo;
import com.bubbletrouble.game.packets.produce.ProduceBulletInfo;
import com.bubbletrouble.game.packets.produce.ProduceInfo;
import com.bubbletrouble.game.states.interfaces.GameObjectsContainer;
import com.bubbletrouble.game.states.interfaces.PacketsSender;
import com.bubbletrouble.game.states.play.PlayClientState;
import com.bubbletrouble.game.states.play.PlayState;

public class Player extends MovableGameObject
{
	private static final float ROTATION_CONSTANT = 140.0f;
	private static final float ROTATION_SPEED = 10.0f;
	private float shootingTime = 0.0f;
	private float shootingInterval = 0.3f;
	private float ANGLE_UPDATE_INTERVAL = 0.1f;
	private float angleUpdateTime = 0.0f;
	private int points = 0;

	private BitmapFont font = ShooterGame.assets.getFont();
	private float targetRotation = 0.0f;

	public Player(PlayState linkedState, long id)
	{
		super((Texture) ShooterGameClient.assets.get("blue.gif"), linkedState);
		setId(id);
	}

	@Override
	public void clientUpdate()
	{
		angleUpdateTime += Gdx.graphics.getDeltaTime();
		updateAngle();
		if (shouldUpdateAngleTarget())
			updateAngleTarget(getMousePosition());
	}

	private void updateAngle()
	{
		float rotation = getRotation();
		float angleDifference = ((targetRotation - rotation + 180) % (360) - 180) * 
				Gdx.graphics.getDeltaTime() * ROTATION_SPEED;
		setRotation(rotation + angleDifference);
	}


	private boolean shouldUpdateAngleTarget()
	{
		return ((PlayClientState) linkedState).getItsOwnPlayer() == this;
	}


	private Vector2 getMousePosition()
	{
		return new Vector2(Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight());
	}

	@Override
	public void serverUpdate()
	{
		shootingTime += Gdx.graphics.getDeltaTime();
		setRotation(targetRotation);
	}

	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		font.draw(batch, "Points: " + String.valueOf(points), getX() - 10, getY() + 38);
		font.draw(batch, getName(), getX() - 10, getY() + 55);
	}

	private void updateAngleTarget(Vector2 mousePosition)
	{
		updateAngleTargetValue(mousePosition);
		if (shouldSendUpdateAngleAction())
		{
			sendUpdateAngleAction();
			angleUpdateTime = 0.0f;
		}
	}

	public void updateAngleTargetValue(Vector2 mousePosition)
	{
		Vector2 center = getCenter();
		Vector2 difference = center.sub(mousePosition);
		targetRotation = difference.angle() + ROTATION_CONSTANT;
	}

	private boolean shouldSendUpdateAngleAction()
	{
		return angleUpdateTime >= ANGLE_UPDATE_INTERVAL;
	}

	private void sendUpdateAngleAction()
	{
		UpdateAngleTargetAction action = new UpdateAngleTargetAction();
		action.mousePosition = getMousePosition();
		action.makeAction(this);
		((PacketsSender) linkedState).sendAction(action, getId());
	}

	private ProduceBulletInfo produceShootenBulletInfo(float mouseX, float mouseY)
	{
		ProduceBulletInfo info = new ProduceBulletInfo();
		info.id = IdProvider.getNextId();
		info.shooterId = getId();
		info.mouseX = mouseX;
		info.mouseY = mouseY;
		Vector2 center = getCenter();

		Vector2 circularPosition = new Vector2(getX() - mouseX, getY() - mouseY);
		float angle = circularPosition.angle();
		circularPosition.set(15.0f, 15.0f);
		circularPosition.setAngle(angle);

		info.x = center.x - 7.5f - circularPosition.x;
		info.y = center.y - 3.5f - circularPosition.y;
		return info;
	}

	@Override
	public ProduceInfo produceInfo()
	{
		PlayerProduceInfo info = new PlayerProduceInfo();
		info.x = getX();
		info.y = getY();
		info.id = getId();
		info.points = points;
		info.name = getName();
		return info;
	}

	public void shootIfCan(float mouseX, float mouseY)
	{
		if (canShoot(mouseX, mouseY))
			shoot(mouseX, mouseY);
	}

	private boolean canShoot(float mouseX, float mouseY)
	{
		Circle mouseNotAllowedArea = new Circle(getCenter(), 30.0f);
		return shootingTime > shootingInterval && !mouseNotAllowedArea.contains(mouseX, mouseY);
	}

	private void shoot(float mouseX, float mouseY)
	{
		shootingTime = 0.0f;
		updateAngleTargetValue(new Vector2(mouseX, mouseY));
		ProduceInfo info = produceShootenBulletInfo(mouseX, mouseY);
		((PacketsSender) linkedState).send(info);
		((GameObjectsContainer) linkedState).addObject(info.produce(linkedState), info.id);
	}

	@Override
	public int getPointsValue()
	{
		return 1000;
	}

	public void addPoints(int points)
	{
		this.points += points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public int getPoints()
	{
		return points;
	}

	public void setRotationTarget(float rotation)
	{
		targetRotation = rotation;
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}
}
