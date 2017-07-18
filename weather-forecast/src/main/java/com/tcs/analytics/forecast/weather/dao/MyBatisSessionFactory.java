package com.tcs.analytics.forecast.weather.dao;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;


/**
 * Provides data source connection via MyBatis.
 * 
 * @author Jyothish Kalavoor Parambil
 * @version 1.0
 */
public class MyBatisSessionFactory {

	public static final Logger LOGGER = Logger.getLogger(MyBatisSessionFactory.class);
	private static SqlSessionFactory sessionFactory;
	static {

		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
			
		} catch (IOException ex) {
			LOGGER.error(ExceptionUtils.getRootCause(ex));
		}
	}

	/**
	 * 
	 * @return
	 */
	public static SqlSession getSession() {
		return sessionFactory.openSession();
	}

}
