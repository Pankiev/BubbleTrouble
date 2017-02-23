package com.bubbletrouble.game.states.connection;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.bubbletrouble.game.BubbleTroubleGameClient;
import com.bubbletrouble.game.libgdxcommon.State;
import com.esotericsoftware.kryonet.Client;

public class PreReconnectionState extends State
{
	private Client client;
	private BitmapFont font = BubbleTroubleGameClient.assets.getFont();
	Label label = new Label("Some text", new LabelStyle(font, new Color(0.2f, 0.2f, 0.2f, 0.8f)));
	
	public PreReconnectionState(Client client)
	{
		label.setText("Some Text");
		label.setBounds(10, 10, 100, 20);
		this.client = client;
	}

	@Override
	public void render(SpriteBatch batch)
	{
		TextButton button = new TextButton("Play again", new Skin());
		button.draw(batch, 1.0f);
		label.draw(batch, 1.0f);
	}

	@Override
	public void update()
	{

	}

}
