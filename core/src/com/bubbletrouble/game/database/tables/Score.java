package com.bubbletrouble.game.database.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "scores")
public class Score
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "points")
	private int points;
	
	public Score(){}
	public Score(String nickname, int points)
	{
		this.nickname = nickname;
		this.points = points;	
	}

	
	public String getNickname()
	{
		return nickname;
	}

	public int getPoints()
	{
		return points;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}
}
