package com.praticando.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.praticando.entidade.Usuario;
import com.praticando.repositorio.UsuarioRepositorio;

@Controller
@RequestMapping("/")
public class UsuarioController {

	@Autowired
	private UsuarioRepositorio usuariorepositorio;
	
	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("usuario/lista");
		modelAndView.addObject("usuarios",usuariorepositorio.findAll());
		return modelAndView;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar(@RequestParam(name = "error", required = false) String error) {
	    ModelAndView modelAndView = new ModelAndView("usuario/cadastro");
	    modelAndView.addObject("usuario", new Usuario());
	    if (error != null) {
	        modelAndView.addObject("error", "CPF já Existente No Sistema!");
	    }
	    return modelAndView;
	}

	@PostMapping("/cadastrar")
	public String salvar(Usuario usuario) {
	    try {
	        usuariorepositorio.save(usuario);
	    } catch (DataIntegrityViolationException e) {
	        
	        return "redirect:/cadastrar?error=true";
	    }
	    return "redirect:/";
	}
	
	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("usuario/editar");
		Usuario usuario = usuariorepositorio.findById(id).orElse(null);
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}
	
	@PostMapping("/editar/{id}")
	public String atualizar(@PathVariable Long id, Usuario usuarioAtualizado) {
		Usuario usuarioExistente = usuariorepositorio.findById(id).orElse(null);
		
		if(usuarioExistente != null) {
			usuarioExistente.setNome(usuarioAtualizado.getNome());
			usuarioExistente.setCpf(usuarioAtualizado.getCpf());
			
			usuariorepositorio.save(usuarioExistente);
			
		}
		return "redirect:/";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		usuariorepositorio.deleteById(id);
		return "redirect:/";
	}
	
}
