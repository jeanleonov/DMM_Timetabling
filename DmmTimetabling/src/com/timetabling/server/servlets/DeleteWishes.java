package com.timetabling.server.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;
import com.timetabling.client.base.communication.Communicator;
import com.timetabling.client.communication.requests.TeacherRequest;
import com.timetabling.server.data.entities.curriculum.extentions.Wish;
import com.timetabling.server.data.managers.simple.TeacherManager;
import com.timetabling.server.generating.Generator;

public class DeleteWishes extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(DeleteWishes.class.getSimpleName());

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
					TeacherManager tm = new TeacherManager();
					long teacherId = 1;
					long cathedraId = 1;
					List<Wish> wishes = tm.getAllWishesFor(teacherId, cathedraId);
					List<Long> idWishes = new ArrayList<Long>();
					for (Wish wish : wishes)
						idWishes.add(wish.getId());
					
					tm.deleteWishes(teacherId, cathedraId, idWishes);
				}
				catch (Throwable t) {
					t.printStackTrace();
					logger.log(Level.WARNING, "Error in deleting wished process", t);
				}
			}
		});
		thread.start();
	}

}
