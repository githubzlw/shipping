package com.cynergy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cynergy.main.MainSql;

public class CynergyListener implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("结束");
	}

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("开始");
		MainSql.Init();
	}

}
