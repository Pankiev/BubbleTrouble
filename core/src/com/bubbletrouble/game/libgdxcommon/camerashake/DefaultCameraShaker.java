package com.bubbletrouble.game.libgdxcommon.camerashake;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class DefaultCameraShaker implements CameraShaker
{
	private final Vector3 originalCameraPosition;
	private final OrthographicCamera camera;
	private float shakingTime = 0;
	private final float length;
	private final float power;
	private final Random random = new Random();

	public DefaultCameraShaker(OrthographicCamera camera, float power, float length)
	{
		this.camera = camera;
		this.power = power;
		this.length = length;
		originalCameraPosition = new Vector3(camera.position);
	}

	@Override
	public void shake()
	{
		shakingTime += Gdx.graphics.getDeltaTime();
		if ((hasFinished()))
			resetCamera();
		else
			repositionCamera();
	}

	private void resetCamera()
	{
		camera.position.x = originalCameraPosition.x;
		camera.position.y = originalCameraPosition.y;
	}

	@Override
	public boolean hasFinished()
	{
		return shakingTime > length;
	}

	private void repositionCamera()
	{
		resetCamera();
		float currentPower = power * ((length - shakingTime) / length);
		float x = (random.nextFloat() - 0.5f) * currentPower;
		float y = (random.nextFloat() - 0.5f) * currentPower;
		camera.translate(-x, -y);
	}

	@Override
	public void reset()
	{
		shakingTime = 0;
	}

}
