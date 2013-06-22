package com.timetabling.server.servlets;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.timetabling.server.generating.Generator;

public class ResultProvider extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(ResultProvider.class.getSimpleName());
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (Generator.lastPDF == null)
			logger.log(Level.WARNING, "TT is not generated yet");
		else
			blobstoreService.serve(Generator.lastPDF, resp);
	}

}
