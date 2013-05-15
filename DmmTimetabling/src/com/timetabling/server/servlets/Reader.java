package com.timetabling.server.servlets;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timetabling.server.curriculum.reading.CurriculumReader;

public class Reader extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(Reader.class.getSimpleName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String path = context.getRealPath("Уч_план_прикл_2_4_NEW.xls");
		resp.setContentType("text/plain");
		for (File root : File.listRoots())
			resp.getWriter().write(root.getAbsolutePath() + " * " + root.getName());
		try {
			new CurriculumReader(new File(path), 2013,  true).readAndPersistCurriculum();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Problems while opening curriculum file", e);
			e.printStackTrace();
		}
	}

}
