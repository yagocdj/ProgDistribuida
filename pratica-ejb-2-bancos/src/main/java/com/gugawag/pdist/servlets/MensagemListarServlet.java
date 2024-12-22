package com.gugawag.pdist.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gugawag.pdist.ejbs.MensagemService;
import com.gugawag.pdist.model.Mensagem;

@WebServlet(urlPatterns = {"/MensagemListarServlet"})
public class MensagemListarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB(lookup = "java:module/mensagemService")
	private MensagemService mensagemService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter resposta = response.getWriter();
		
		for (Mensagem mensagem : mensagemService.listar()) {
			resposta.println(mensagem.getMensagem());
		}
		
	}

}
