package com.bubbletrouble.game.libgdxcommon.objects;


import java.util.Collection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bubbletrouble.game.libgdxcommon.State;
import com.bubbletrouble.game.objects.ClientServerGameObject;
import com.bubbletrouble.game.packets.produce.InfoProcucable;

public abstract class GameObject extends Actor implements InfoProcucable, ClientServerGameObject, ScoreValue
{
	private Sprite sprite;
	private long id;
	private Circle circle = new Circle();
	protected State linkedState;

	protected GameObject(Texture lookout, State linkedState)
	{
		super();
		setName("Unnamed");
		this.linkedState = linkedState;
		sprite = new Sprite(lookout);
		sprite.setRegion(lookout);
		super.setSize(lookout.getWidth(), lookout.getHeight());
		circle.setRadius((lookout.getWidth() + lookout.getHeight()) / 4);
	}

	private boolean isColliding(GameObject gameObject)
	{
		return gameObject.isCollidable() && isColliding(gameObject.circle);
	}

	public boolean isColliding(Circle circle)
	{
		return this.circle.overlaps(circle);
	}

	protected GameObject checkForCollision(Collection<GameObject> possibleCollision)
	{
		for (GameObject gameObject : possibleCollision)
			if (isColliding(gameObject) && gameObject != this)
				return gameObject;
		return null;
	}


	public void render(SpriteBatch batch)
	{
		sprite.draw(batch);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		sprite.draw(batch, parentAlpha);
	}

	@Override
	public void setX(float x)
	{
		sprite.setX(x);
		circle.setX(x);
		super.setX(x);
	}

	@Override
	public void setY(float y)
	{
		sprite.setY(y);
		circle.setY(y);
		super.setY(y);
	}

	@Override
	public void setPosition(float x, float y)
	{
		sprite.setPosition(x, y);
		circle.setPosition(x, y);
		super.setPosition(x, y);
	}

	@Override
	public void setPosition(float x, float y, int alignment)
	{
		sprite.setPosition(x, y);
		circle.setPosition(x, y);
		super.setPosition(x, y, alignment);
	}

	@Override
	public void setWidth(float width)
	{
		sprite.setSize(width, sprite.getHeight());
		circle.setRadius((width + getHeight()) / 4);
		super.setWidth(width);
	}

	@Override
	public void setHeight(float height)
	{
		sprite.setSize(sprite.getWidth(), height);
		circle.setRadius((getWidth() + height) / 4);
		super.setHeight(height);
	}

	@Override
	public void setSize(float width, float height)
	{
		sprite.setSize(width, height);
		circle.setRadius((width + height) / 4);
		super.setSize(width, height);
	}

	@Override
	public void setBounds(float x, float y, float width, float height)
	{
		sprite.setBounds(x, y, width, height);
		circle.setPosition(x, y);
		circle.setRadius((width + height) / 4);
		super.setBounds(x, y, width, height);
	}

	@Override
	public void setOriginX(float originX)
	{
		sprite.setOrigin(originX, sprite.getOriginY());
		super.setOriginX(originX);
	}

	@Override
	public void setOriginY(float originY)
	{
		sprite.setOrigin(sprite.getOriginX(), originY);
		super.setOriginY(originY);
	}

	@Override
	public void setOrigin(float originX, float originY)
	{
		sprite.setOrigin(originX, originY);
		super.setOrigin(originX, originY);
	}

	@Override
	public void setScaleX(float scaleX)
	{
		sprite.setScale(scaleX, sprite.getScaleY());
		super.setScaleX(scaleX);
	}

	@Override
	public void setScaleY(float scaleY)
	{
		sprite.setScale(sprite.getScaleX(), scaleY);
		super.setScaleY(scaleY);
	}

	@Override
	public void setScale(float scaleXY)
	{
		sprite.setScale(scaleXY, scaleXY);
		super.setScale(scaleXY);
	}

	@Override
	public void setScale(float scaleX, float scaleY)
	{
		sprite.setScale(scaleX, scaleY);
		super.setScale(scaleX, scaleY);
	}

	@Override
	public void setRotation(float degrees)
	{
		sprite.setRotation(degrees);
		super.setRotation(degrees);
	}

	@Override
	public void setColor(Color color)
	{
		sprite.setColor(color);
		super.setColor(color);
	}

	@Override
	public void setColor(float r, float g, float b, float a)
	{
		sprite.setColor(r, g, b, a);
		super.setColor(r, g, b, a);
	}

	@Override
	public void rotateBy(float amountInDegrees)
	{
		sprite.rotate(amountInDegrees);
		super.rotateBy(amountInDegrees);
	}

	protected Texture getTexture()
	{
		return sprite.getTexture();
	}

	protected Sprite getSprite()
	{
		return sprite;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public Vector2 getCenter()
	{
		Vector2 center = new Vector2();
		return getSprite().getBoundingRectangle().getCenter(center);
	}

	public abstract boolean isCollidable();
}