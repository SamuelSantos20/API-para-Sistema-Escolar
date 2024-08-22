package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AlunoImplService;
import service.NotaImplService;
import util.GeradordeRelatorio;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// o aluno possui acesso somente a coisas basicas do sistema que envolvem eles mesmos 
@Controller
public class AlunoController {

	//injeções para aceassar o banco de dados 
	@Autowired
	private AlunoImplService alunoImplService;

	@Autowired
	private NotaImplService notaImplService;

	@Autowired
	private GeradordeRelatorio geradordeRelatorio;
	
	// Metodo que lista informções do aluno na pagina
	@GetMapping("/listaAlunos")
	public ModelAndView getMethodName( HttpSession session) {
		Long id = (Long) session.getAttribute("aluno");
		
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("Alunos", alunoImplService.ListaUnica(id));
			mv.setViewName("Aluno/aluno.html");
			return mv;

		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
//Metodo que lista notas no aluno na pagina 
	@GetMapping("/listarNotas")
	public ModelAndView ListarNotasAluno(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {
			Long id = (Long) session.getAttribute("aluno");
			System.out.println(id);
			System.out.println(notaImplService.ListarImpressaoNotaAluno(id));
			
			mv.addObject("Notas", notaImplService.ListarImpressaoNotaAluno(id));
			mv.setViewName("Notas/notas.html");
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}

	}
	
	//Metodo que lista todos alunos na pagina ( esse metodo só é disponivel para professores e ADMS)
	@GetMapping("/ListarTodos")
	public ModelAndView ListarTodosAlunos(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		try {
			mv.setViewName("Aluno/alunos.html");
			mv.addObject("Alunos", alunoImplService.ListaCompleta());
			return mv;
		} catch (Exception e) {
		
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}
		
		
		
	}
	
	
	//Metodo que faz uso da classe de geração de relatorio , gera um relatorio das notas do aluno
	@GetMapping("/ImprimirNota")
	public void ImpressaodeNota(HttpSession session , HttpServletResponse response) {
		Long id = (Long) session.getAttribute("aluno");
		System.out.println(notaImplService.ListarImpressaoNotaAluno(id));
		geradordeRelatorio.gerarRelatoriocontato(response, notaImplService.ListarImpressaoNotaAluno(id));
		
	}
	// metodo usado para fazer a pesquisa do aluno no sistema 
	@PostMapping("/PesquisarAlunos")
	public ModelAndView PesquisarAluno(@RequestParam("texto") String texto) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.setViewName("Aluno/alunos.html");
			mv.addObject("Alunos", alunoImplService.PesquisaDto(texto));
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}
		
	
	}
	

}
