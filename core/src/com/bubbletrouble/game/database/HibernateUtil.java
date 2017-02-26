package com.bubbletrouble.game.database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.bubbletrouble.game.database.tables.Score;

public class HibernateUtil
{
	private static SessionFactory sessionFactory;

	static
	{
		try
		{
			Configuration conf = new Configuration()
					.configure("hibernate.cfg.xml");
			conf.addAnnotatedClass(Score.class);
			StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(conf.getProperties()).build();

			sessionFactory = conf.buildSessionFactory(serviceRegistry);
		} catch (HibernateException exception)
		{
			System.err.println("Error creating session factory: " + exception);
			throw new ExceptionInInitializerError(exception);
		}
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static Session openSession()
	{
		return sessionFactory.openSession();
	}
}
