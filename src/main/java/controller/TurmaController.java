package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Diciplina;
import domain.Turma;
import jakarta.servlet.http.HttpSession;
import service.DiciplinaImplService;
import service.TurmaImplService;
import util.GeradordeCodigo;

@Controller
public class TurmaController {

	//injeções de dependencia que permitem o acesso ao banco de dados 
	@Autowired
	private TurmaImplService turmaImplService;

	@Autowired
	private DiciplinaImplService diciplinaImplService;

	private GeradordeCodigo codigo = new GeradordeCodigo();
	
	//metodo que envia para a pagina a lista de diciplina  , objeto turma  , codigo da tura para ela poder ser adicionada 
	@GetMapping("/requesAddTurma")
	public ModelAndView RequestAddTurma(HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {

			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {

				mv.addObject("Turma", new Turma());
				mv.addObject("codigo", codigo.GeradorCodigo());
				mv.addObject("diciplinas", diciplinaImplService.ListarTodas());
				mv.setViewName("Turma/AddTurma.html");
				return mv;

			}

			else {

				mv.setViewName("redirect:/index");
				mv.addObject("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM ");
				return mv;

			}

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;
		}

	}
// metod que faz a adição da turma no sistema( cadastro no banco de dados )
	@PostMapping("/adicionar_Turma")
	public ModelAndView AdicionarTurma(Turma turma, @RequestParam("disciplina") Long diciplina_id, @RequestParam("codigo")String code , 
			HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {
			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {
				Diciplina diciplina = new Diciplina();

				diciplina.setId(diciplina_id);

				turma.getDiciplinas().add(diciplina);
                turma.setCodigo_turma(code);
				turmaImplService.Salvar(turma);
				mv.addObject("successMessage", "Turma adicionada ao Sistema!.");
				mv.addObject("Turma", new Turma());
				mv.addObject("codigo", codigo.GeradorCodigo());
				mv.addObject("diciplinas", diciplinaImplService.ListarTodas());
				mv.setViewName("Turma/AddTurma.html");
				return mv;
			}

			else {

				mv.setViewName("redirect:/index");
				mv.addObject("errorMessage",
						"Erro ao adicionar. Está função está disponivel apenas para funcionarios com Nível ADM.");
				return mv;
			}

		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;

		}

	}
//metodo que lista as diciplinas que o aluno possui e está cadastrado no sistema 
	@GetMapping("/ListarDiciplinasAluno")
	public ModelAndView ListadeDiciplinasdoAluno(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		Long id = (Long) session.getAttribute("aluno");

		try {
			mv.addObject("TurmaDiciplina", turmaImplService.ListarAsDiciplinaseTurmas(id));
			mv.setViewName("Diciplinas/diciplinasAluno.html");
			return mv;

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}

	}
// metodo que lista as turmas assim como suas informações na pagina 
	@GetMapping("/ListarTurmas")
	public ModelAndView ListarTurma() {
		ModelAndView mv = new ModelAndView();

		try {

			mv.addObject("Turmas", turmaImplService.getDiciplina_Turma());
			mv.addObject("Turma", new Turma());
			mv.setViewName("Turma/Turmas.html");
			return mv;
		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}
	}
//metodo utilizado para verifica os alunos cadastrados na turma selecionada pelo usuario 
	@PostMapping("/verAlunos")
	public ModelAndView ListarTurmaAlunos(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();
		try {
			System.out.println(id);
			mv.addObject("Alunos", turmaImplService.getDiciplina_TurmaID(id));
			mv.setViewName("Turma/turma-alunos.html");
			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			System.out.println(e);
			return mv;
		}

	}
	
	//metodo utilizado para fazer a pesquisa da turma no sistema pelo nome ou pelo codigo da turma 
	@PostMapping("/PesquisarTurmas")
	public ModelAndView pesquisaporTurmas(@RequestParam("texto") String texto) {
	ModelAndView mv = new ModelAndView();
		try {
		System.out.println(turmaImplService.pesquisarTurmas(texto));
		mv.addObject("Turmas", turmaImplService.pesquisarTurmas(texto));
		mv.addObject("Turma", new Turma());
		mv.setViewName("Turma/Turmas.html");
		return mv;
	} catch (Exception e) {
		System.out.println(e);
		mv.setViewName("redirect:/error");
		System.out.println(e);
		return mv;
	}
}
	}
	


