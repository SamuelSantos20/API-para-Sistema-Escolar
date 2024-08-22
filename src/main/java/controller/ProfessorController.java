package controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Aluno;
import domain.Diciplina;
import domain.Nota;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.AlunoImplService;
import service.DiciplinaImplService;
import service.NotaImplService;
import service.ProfessorImplService;
import util.CalculaMedia;
import util.GeradordeRelatorio;

// O Professor é reponsavel por adicionar a nota dos alunos no sistema (assim como o ADM  também pode) podendo consultar suas informações turmas , alunos e suas informações
//BASICAS como matricula , turma e nome , assim como editar a nota do aluno . podendo tabem consultar suas proprias informações 
@Controller
public class ProfessorController {

	//injeções de dependencias para acessar o sistema do banco de dados 
	@Autowired
	private AlunoImplService alunoImplService;

	@Autowired
	private DiciplinaImplService diciplinaImplService;

	@Autowired
	private NotaImplService notaImplService;

	@Autowired
	private GeradordeRelatorio geradordeRelatorio;

	@Autowired
	private ProfessorImplService professorImplService;

	// metodo que faz o redirecionamento para a pagina de adição de notas no siatemma assim com também retorna as coisas necessarias para a adição da nota no sistema como objeto
	// aluno que a nota deve ser adicionado podendo fazer a busca desse aluno no sistema (assim como da diciplina que a nota será adicionada) apenas disponivel para ADMS e professores 
	@GetMapping("/requestAdicionarNota")
	public ModelAndView requestAddNota(HttpSession session) {

		ModelAndView mv = new ModelAndView();
		Abrangencia abrangencia = (Abrangencia) session.getAttribute("abrangencia");

		try {

			if (abrangencia == Abrangencia.ADM || abrangencia == Abrangencia.PROFESSOR) {

				mv.addObject("Alunos", alunoImplService.ListarTodos());
				mv.addObject("Diciplinas", diciplinaImplService.ListarTodas());
				mv.addObject("Nota", new Nota());
				mv.setViewName("Notas/adicionar-notas.html");
				return mv;

			}

			else {
				mv.addObject("errorMessage",
						"ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM OU PROFESSOR!");
				mv.setViewName("index.html");
				return mv;
			}

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;

		}

	}
// metodo que faz a buasca do aluno no banco de dados para fazer a adição de sua nota no sistema 
	@PostMapping("/buscarAluno")
	public ModelAndView postMethodName(@RequestParam("search_aluno") String texto, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		try {
			// Pesquisar alunos e armazenar na sessão
			session.setAttribute("Alunos", alunoImplService.PesquisarAluno(texto));
			mv.addObject("Alunos", session.getAttribute("Alunos"));

			// Manter as disciplinas já buscadas na sessão
			mv.addObject("Diciplinas", session.getAttribute("Diciplinas") != null ? session.getAttribute("Diciplinas")
					: diciplinaImplService.ListarTodas());

			mv.addObject("Nota", new Nota());
			mv.setViewName("Notas/adicionar-notas.html");

			return mv;

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
// metodo que faz a bsuca no sistema da diciplina a qual a nota será adicionada no sistema 
	@PostMapping("/BuscarMateria")
	public ModelAndView PesquisarMateria(@RequestParam("search_materia") String texto, HttpSession session) {
		ModelAndView mv = new ModelAndView();

		try {
			// Pesquisar disciplinas e armazenar na sessão
			session.setAttribute("Diciplinas", diciplinaImplService.PesquisarDiciplina(texto));
			mv.addObject("Diciplinas", session.getAttribute("Diciplinas"));

			// Manter os alunos já buscados na sessão
			mv.addObject("Alunos", session.getAttribute("Alunos") != null ? session.getAttribute("Alunos")
					: alunoImplService.ListarTodos());

			mv.addObject("Nota", new Nota());
			mv.setViewName("Notas/adicionar-notas.html");

			return mv;

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
// metod que faz a adição da nota no sistema do banco de dadps 
	@PostMapping("/adicionar-nota")
	public ModelAndView AdicionarNota(Nota nota, HttpSession session, @RequestParam("aluno") Long id_aluno,
			@RequestParam("materia") Long id_materia) {

		ModelAndView mv = new ModelAndView();
		CalculaMedia c = new CalculaMedia();
		Aluno aluno = new Aluno();
		Diciplina diciplina = new Diciplina();

		Abrangencia abrangencia = (Abrangencia) session.getAttribute("abrangencia");

		try {

			if (abrangencia == Abrangencia.ADM || abrangencia == Abrangencia.PROFESSOR) {
				nota.setMedia(c.Media(nota.getNota_trabalho(), nota.getNota_Teste(), nota.getNota_prova()));

				aluno.setId(id_aluno);
				diciplina.setId(id_materia);
				nota.setAluno_id(aluno);
				nota.setDiciplina_id(diciplina);

				notaImplService.Salvar(nota);
				mv.addObject("successMessage", "Nota adicionada ao Sistema!.");
				mv.addObject("Alunos", alunoImplService.ListarTodos());
				mv.addObject("Diciplinas", diciplinaImplService.ListarTodas());
				mv.addObject("Nota", new Nota());
				mv.setViewName("Notas/adicionar-notas.html");
				return mv;

			}

			else {
				mv.addObject("errorMessage",
						"ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM OU PROFESSOR!");
				mv.setViewName("index.html");
				return mv;
			}

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;

		}
	}
//metodo que serve para gerar um elatorio das notas do aluno ou dos alunos no sistema 
	@GetMapping("/Relatorio")
	public ModelAndView Relatorio() {
		ModelAndView mv = new ModelAndView();
		try {

			mv.addObject("Dados", notaImplService.ListarRelatorio());
			mv.setViewName("Relatorios/relatorios.html");
			return mv;

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}

	}
// metodo que faz a busca do aluno no sistema caso o ADM ou o Professor quera uma nota especifica 
	@PostMapping("/PesquisaAluno")
	public ModelAndView PesquisarAluno(@RequestParam("search") String texto, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {

			session.setAttribute("texto", texto);

			mv.addObject("Dados", notaImplService.BuscarPorAluno(texto));
			mv.setViewName("Relatorios/relatoriopesquisa.html");
			return mv;

		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;
		}

	}
//metodo que gera um relatorio total de todas as notas dos alunos do sistema 
	@GetMapping("/GerarRelatorio")
	public void GerarRelatorio(HttpServletResponse response, HttpSession session) {
		geradordeRelatorio.gerarRelatoriocontato(response, notaImplService.ListarRelatorio());
		session.removeAttribute("texto");
	}
//metodo que  gera o relatorio referente ao aluno da pesquisa (o relaorio de um unico aluno)
	@GetMapping("/GerarRelatorioPesquisa")
	public void GerarRelatorioporPesquisa(HttpServletResponse response, HttpSession session) {
		String texto = String.valueOf(session.getAttribute("texto"));
		geradordeRelatorio.gerarRelatoriocontato(response, notaImplService.BuscarPorAluno(texto));

	}
	
	//metodo que lista os professores com suas informações e rectivas materias que lecionam 
	@GetMapping("/ListaProfessores")
	public ModelAndView RequetsProfessore(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {

			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {
				mv.addObject("professores", professorImplService.RequestListarProfessores());
				mv.setViewName("Professor/professores.html");

			}

			else {

				mv.setViewName("redirect:/index");
				mv.addObject("errorMessage",
						"Erro ao adicionar. Está função está disponivel apenas para funcionarios com Nível ADM.");
				return mv;

			}

			return mv;
		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
// metodo que carrega as notas diciplina e alunoo qual deseja que a nota seja alterada 
	@GetMapping("/preEditarNota")
	public ModelAndView PreEditarNota(@RequestParam("id") Long id, HttpSession session,
			@RequestParam("diciplina") Long diciplina) {
		ModelAndView mv = new ModelAndView();
		session.setAttribute("id_aluno_nota", id);
		session.setAttribute("id_diciplina_nota", diciplina);
		System.out.println(diciplina);
		System.out.println(diciplinaImplService.PesquisarDiciplinasId(diciplina));
		try {
			if (notaImplService.ListarIdAluno(id).orElse(new Nota()) != null) {
				Nota notas = notaImplService.ListarIdAluno(id).orElse(new Nota());

				mv.addObject("Alunos", alunoImplService.ListarOneAluno(id));
				mv.addObject("Diciplinas", diciplinaImplService.PesquisarDiciplinasId(diciplina));
				mv.addObject("Nota", notas);
				mv.setViewName("Notas/adicionar-notas.html");
				return mv;

			}

			else {

				mv.setViewName("redirect:/ListarTurmas");
				mv.addObject("errorMessage",
						"Erro ao adicionar. Esse aluno ainda não possui notas registradas ao Sistema!");
				return mv;

			}
		} catch (Exception e) {

			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;
		}

	}
// metodo que faz adição da nota enviando a nova nota para o sistema
	@PostMapping("/editarNota")
	public ModelAndView EditarNota(Nota nota, HttpSession session, @RequestParam("materia") Long id_diciplina,
			@RequestParam("aluno") Long id_aluno) {
		ModelAndView mv = new ModelAndView();
		Long id_aluno_nota = (Long) session.getAttribute("id_aluno_nota");
		Long id_diciplina_nota = (Long) session.getAttribute("id_diciplina_nota");
		try {

			Aluno aluno = new Aluno();
	        aluno.setId( id_aluno);

	        Diciplina diciplina = new Diciplina();
	        diciplina.setId(id_diciplina);

	        CalculaMedia calculaMedia = new CalculaMedia();
	        nota.setMedia(calculaMedia.Media(nota.getNota_trabalho(), nota.getNota_Teste(), nota.getNota_prova()));
	        nota.setDiciplina_id(diciplina);
	        nota.setAluno_id(aluno);
	        
			notaImplService.Atualizar(nota);
			Nota notas = notaImplService.ListarIdAluno(id_aluno_nota).orElse(new Nota());
			mv.addObject("Alunos", alunoImplService.ListarOneAluno(id_aluno_nota));
			mv.addObject("Diciplinas", diciplinaImplService.PesquisarDiciplinasId(id_diciplina_nota));
			mv.addObject("Nota", notas);
			mv.addObject("successMessage", "Nota editada com secesso !");
			mv.setViewName("Notas/adicionar-notas.html");
			return mv;
		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;
		}

	}
//metodo utilizado para ver as notas inviduas de cada aluno referente a turma escolhida 
	@GetMapping("/verificarNotas")
	public ModelAndView verifinotasAluno(@RequestParam("id") Long id) {
		ModelAndView mv = new ModelAndView();

		try {

			System.out.println(id);
			System.out.println(notaImplService.ListarImpressaoNotaAluno(id));
			
			mv.addObject("Notas", notaImplService.ListarImpressaoNotaAluno(id));
			mv.setViewName("Professor/verificarnotas-alunos.html");
			return mv;
			
		} catch (Exception e) {
			System.out.println(e);
			mv.setViewName("redirect:/error");
			return mv;
			
		}
	}

}
