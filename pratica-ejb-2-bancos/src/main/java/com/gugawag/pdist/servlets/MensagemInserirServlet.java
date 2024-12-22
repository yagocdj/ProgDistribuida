package com.gugawag.pdist.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gugawag.pdist.ejbs.MensagemService;

@WebServlet("/MensagemInserirServlet")
public class MensagemInserirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB(lookup = "java:module/mensagemService")
	private MensagemService mensagemService;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long id = Integer.parseInt(request.getParameter("id"));
		String mensagem = request.getParameter("mensagem");
		mensagemService.inserir(id, mensagem);
	}

}
