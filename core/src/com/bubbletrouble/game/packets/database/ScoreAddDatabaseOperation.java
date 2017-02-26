package com.bubbletrouble.game.packets.database;

import com.bubbletrouble.game.database.managers.ScoreManager;
import com.bubbletrouble.game.database.tables.Score;
import com.bubbletrouble.game.kryonetcommon.Registerable;

@Registerable
public class ScoreAddDatabaseOperation implements DatabaseOperation
{
	private String nickname;
	private int points;

	// No-arg constructor needed with kryonet serialization
	public ScoreAddDatabaseOperation(){}

	public ScoreAddDatabaseOperation(String nickname, int points)
	{
		this.nickname = nickname;
		this.points = points;
	}

	public ScoreAddDatabaseOperation(Score score)
	{
		this.nickname = score.getNickname();
		this.points = score.getPoints();
	}

	@Override
	public void perform()
	{
		ScoreManager manager = new ScoreManager();
		Score score = new Score(nickname, points);
		manager.insertScore(score);
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
}
