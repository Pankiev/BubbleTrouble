package com.bubbletrouble.game.database.managers;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bubbletrouble.game.database.HibernateUtil;
import com.bubbletrouble.game.database.tables.Score;

public class ScoreManager
{
	public Integer insertScore(String nickname, int points)
	{
		Score score = new Score();
		score.setNickname(nickname);
		score.setPoints(points);
		return insertScore(score);
	}

	public Integer insertScore(Score score)
	{
		Integer id;
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			id = (Integer) session.save(score);
		}
		catch (HibernateException exception) 
		{
			throw new ScoreInsertionException(exception);
		}
		return id;
	}

	public void clearAll()
	{
		try (Session session = HibernateUtil.getSessionFactory().openSession())
		{
			Transaction transaction = session.beginTransaction();
			Query clearTable = session.createQuery("DELETE FROM Score");
			clearTable.executeUpdate();
			transaction.commit();
		} catch (HibernateException exception)
		{
			throw new ScoreInsertionException(exception);
		}
	}

	private class ScoreInsertionException extends HibernateException
	{
		public ScoreInsertionException(Throwable cause)
		{
			super(cause);
		}
	}
}
