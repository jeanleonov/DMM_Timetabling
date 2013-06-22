package com.timetabling.server.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;
import com.timetabling.server.generating.Generator;

public class GeneratorRunner extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(GeneratorRunner.class.getSimpleName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
			public void run() {
				try {
					new Generator(2013, true).getTTWithMark(0.77);
				}
				catch (Throwable t) {
					t.printStackTrace();
					logger.log(Level.WARNING, "Error in generating process", t);
				}
			}
		});
		thread.start();
	}

}
