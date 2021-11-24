package com.eventoapp.controllers;

import javax.validation.Valid;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.ConvidadosWrapper;
import com.eventoapp.models.Evento;
import com.eventoapp.models.Notificacao;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EventoController {

	@Autowired // Vai fazer uma injeção de dependências, toda vez que precisarmos usar essa interface será criada uma nova instância automaticamente.
	private EventoRepository er; 
	
	@Autowired // Vai fazer uma injeção de dependências, toda vez que precisarmos usar essa interface será criada uma nova instância automaticamente.
	private ConvidadoRepository cr;
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST)
	public String salvarEvento(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";
		}
		er.save(evento);
		attributes.addFlashAttribute("mensagem", Notificacao.getInstance("Evento Cadastrado com Sucesso!"));
		return "redirect:/cadastrarEvento";
	}
	
	// Método para retornar a lista de eventos:
	
	@RequestMapping("/eventos") // dentro das aspas ficam o que vai retornar. Vai retornar o evento
	public ModelAndView listarEventos(){
		ModelAndView mv = new ModelAndView("index"); //index é a página que vai ser renderizada: index.html
		Iterable<Evento> eventos = er.findAll(); // Busca toda a lista de eventos
		mv.addObject("eventos", eventos); // O "eventos" dentro das aspas é a palavra eventos que está nessa parte do código do index.html (${eventos}): <div th:each="evento : ${eventos}">
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET) // vai retornar o código de cada evento e mostrar os detalhes dele
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento"); //index é a página que vai ser renderizada: detalhesEvento.html
		mv.addObject("evento", evento); // O "evento" dentro das aspas é a palavra evento que está nessa parte do código do detalhesEvento.html (${evento}): <div th:each="evento : ${evento}">
		System.out.println("evento" + evento);
		
		ConvidadosWrapper convidadosWrapper = new ConvidadosWrapper(cr.findByEvento(evento)); // Pega a lista de convidados através do código do evendo. O método está em ConvidadoRepository.java
		mv.addObject("convidadosWrapper", convidadosWrapper); // Aqui enviamos a lista para a view
		
		return mv;
	}
	
	@RequestMapping(value="/EditarEvento/{codigo}", method=RequestMethod.GET) 
	public ModelAndView buscarEventoParaEditar(@PathVariable("codigo") long codigo) {
		Evento evento = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("evento/editarDetalhesEvento"); 
		mv.addObject("evento", evento);
		System.out.println("evento" + evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento); 
		mv.addObject("convidados", convidados); 
		
		return mv;
	}
	
	@RequestMapping(value="/EditarEvento/{codigo}", method=RequestMethod.POST) 
	public String editarEvento(@PathVariable("codigo") long codigo, @Valid Evento evento) {
//		ModelAndView mv = new ModelAndView("evento/editarDetalhesEvento");
		er.save(evento);
		
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo){
		try {
			Evento evento = er.findByCodigo(codigo);
			er.delete(evento);
		}
		catch(Exception ex) {
			
		}
			
		return "redirect:/eventos";
		
		
		
		
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST) // vai retornar o código de cada evento e mostrar os detalhes dele
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		Evento evento = er.findByCodigo(codigo); // Faz a busca na tabela pelo código.
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado Adicionado com Sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
		
	}
	
	@PostMapping("/editarConvidado")
	public String editarConvidado(String rg){
		Convidado convidado = cr.findByRg(rg);
		cr.save(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/" + codigo;
	}

	@PostMapping("/editConvidados")
	public String editarConvidados(@ModelAttribute("convidadosWrapper") ConvidadosWrapper convidadosWrapper, Model model){
		System.out.println(convidadosWrapper.getConvidados());
		return "redirect:/" + 1;
	}
}
