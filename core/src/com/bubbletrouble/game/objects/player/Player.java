package com.bubbletrouble.game.objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.bubbletrouble.game.ShooterGameClient;
import com.bubbletrouble.game.kryonetcommon.IdProvider;
import com.bubbletrouble.game.libgdxcommon.objects.MovableGameObject;
import com.bubbletrouble.game.libgdxcommon.stringdraw.BitmapStringDrawer;
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
	private float shootingTime = 0.0f;
	private float shootingInterval = 0.3f;
	private float ANGLE_UPDATE_INTERVAL = 0.4f;
	private float angleUpdateTime = 0.0f;
	private int points = 0;

	public Player(PlayState linkedState, long id)
	{
		super((Texture) ShooterGameClient.assets.get("blue.gif"), linkedState);
		setId(id);
	}

	@Override
	public void clientUpdate()
	{
		angleUpdateTime += Gdx.graphics.getDeltaTime();
		if (shouldUpdateAngle())
			updateAngle(getMousePosition());
	}

	private boolean shouldUpdateAngle()
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
	}

	@Override
	public void render(SpriteBatch batch)
	{
		BitmapStringDrawer drawer = new BitmapStringDrawer();
		super.render(batch);
		drawer.draw(batch, "Points: " + String.valueOf(points), getX() - 10, getY() + 38);
	}

	private void updateAngle(Vector2 mousePosition)
	{
		updateAngleValue(mousePosition);
		if (shouldSendUpdateAngleAction())
		{
			sendUpdateAngleAction();
			angleUpdateTime = 0.0f;
		}
	}

	public void updateAngleValue(Vector2 mousePosition)
	{
		Vector2 center = getCenter();
		Vector2 difference = center.sub(mousePosition);
		float rotation = difference.angle() + ROTATION_CONSTANT;
		setRotation(rotation);
	}

	private boolean shouldSendUpdateAngleAction()
	{
		return angleUpdateTime >= ANGLE_UPDATE_INTERVAL;
	}

	private void sendUpdateAngleAction()
	{
		UpdateAngleAction action = new UpdateAngleAction();
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
		updateAngleValue(new Vector2(mouseX, mouseY));
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

}
