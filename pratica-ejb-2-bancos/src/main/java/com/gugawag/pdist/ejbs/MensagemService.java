package com.gugawag.pdist.ejbs;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sound.sampled.Line;

import com.gugawag.pdist.model.Mensagem;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;

@Stateless(name = "mensagemService")
@Remote
public class MensagemService {
	
	@EJB
	private MensagemDAO mensagemDao;
	
	private List<String> palavroes = Arrays.asList("Porra", "Caralho", "Foda",
			"Mizera", "Filho da puta", "Filha da puta");
	
	public List<Mensagem> listar() {
		return mensagemDao.listar();
	}
	
	public void inserir(long id, String textoMensagem) {
		
		if (mensagemPossuiPalavrao(textoMensagem)) {
			throw new RuntimeException("A mensagem possui algum palavrão!");
		}
		
		Mensagem mensagem = new Mensagem(id, textoMensagem);
		mensagemDao.inserir(mensagem);
		
	}
	
	private boolean mensagemPossuiPalavrao(String mensagem) {
		for (String palavrao : palavroes) {
			if (mensagem.toLowerCase().contains(palavrao.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
}
