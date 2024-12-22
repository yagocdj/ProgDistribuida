package com.gugawag.pdist.ejbs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.gugawag.pdist.model.Mensagem;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {
	
	@EJB
	private MensagemDAO mensagemDao;
	
	public List<Mensagem> listar() {
		return mensagemDao.listar();
	}
	
	public void inserir(long id, String textoMensagem) {
		Mensagem mensagem = new Mensagem(id, textoMensagem);
		mensagemDao.inserir(mensagem);
		
		// TODO - check if the message contains any dirty words
	}
}
