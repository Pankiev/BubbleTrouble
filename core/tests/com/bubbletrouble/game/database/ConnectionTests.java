package com.bubbletrouble.game.database;

import org.junit.Test;

import com.bubbletrouble.game.database.managers.ScoreManager;

public class ConnectionTests
{

	@Test
	public void testConnection()
	{
		HibernateUtil.getSessionFactory().openSession().close();
	}

	@Test
	public void addScore()
	{
		ScoreManager manager = new ScoreManager();
		Integer id = manager.insertScore("Pankiev", 10000);
	}

	@Test
	public void clearScoreTable()
	{
		ScoreManager manager = new ScoreManager();
		manager.clearAll();
	}
}
