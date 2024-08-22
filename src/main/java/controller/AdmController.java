package controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Administrador;
import domain.Aluno;
import domain.Professor;
import domain.SufixoMatricula;
import domain.Turma;
import jakarta.servlet.http.HttpSession;
import service.AdministradorImplService;
import service.AlunoImplService;
import service.ProfessorImplService;
import service.TurmaImplService;
import util.GeradordeMatricula;

// O unico que pode adicionar alguem ou alguma turma ou diciplina no sistema e o ADM
@Controller
public class AdmController {

	//injeção de dependecias para serviços do banco de dados.
	@Autowired
	private ProfessorImplService professorImplService;

	@Autowired
	private AdministradorImplService administradorImplService;

	@Autowired
	private TurmaImplService turmaImplService;

	@Autowired
	private AlunoImplService alunoImplService;

	// metodo para listar opções para adicionar no sistema (aluno , professor , administrador)
	@GetMapping("/adicionarFuncionario")
	public ModelAndView RequestAdicionarFuncionario(Abrangencia abrangencia, HttpSession session) {

		ModelAndView mv = new ModelAndView();

		try {

			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {
				mv.setViewName("Registro/SelectType.html");
				mv.addObject("Abrangencias", getAbrangencia());
				return mv;

			}

			else {

				mv.setViewName("redirect:/index");
				mv.addObject("errorMessage",
						"Erro ao adicionar. Está função está disponivel apenas para funcionarios com Nível ADM.");
				return mv;

			}

		} catch (Exception e) {

			mv.addObject("errorMessage", "Erro ao adicionar: " + e);
			mv.setViewName("index.html");
			return mv;
		}

	}

	//metodo para enviar a opção selecionado para o sistema
	@PostMapping("/selectAdd")
	public ModelAndView SelectAdicionar(@RequestParam("cargo") Abrangencia abrangencia, HttpSession session) {

		ModelAndView mv = new ModelAndView();
		try {
		//verifica se o usuario é de nivel ADM
			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {

				session.setAttribute("selectaddFuncionario", abrangencia);
				mv.setViewName("redirect:/requestaaddFuncionarior");
				return mv;

			}

			else {

				mv.addObject("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM !");
				mv.setViewName("redirect:/index");
				return mv;

			}

		} catch (Exception e) {
			mv.addObject("errorMessage", "Erro ao Carregar: " + e);
			mv.setViewName("redirect:/index");
			return mv;

		}
	}
     //envia os objtos e informações (matricula , senha) necessarios para a adição do funcionario ou aluno escolhido
	@GetMapping("/requestaaddFuncionarior")
	public ModelAndView preAdicionarProf(HttpSession session, SufixoMatricula sufixo) {

		ModelAndView mv = new ModelAndView();
		Abrangencia abrangencia = (Abrangencia) session.getAttribute("selectaddFuncionario");
		String Adm = String.valueOf(sufixo.ADM);
		String professor = String.valueOf(sufixo.PROF);
		String aluno = String.valueOf(sufixo.ALU);

		System.out.println(Adm);

		System.out.println(professor);

		switch (abrangencia) {
		case PROFESSOR: {
			mv.addObject("matricula", GeradordeMatricula.generateMatricula(professor));
			mv.addObject("senha", GeradordeMatricula.geradorSenha());
			mv.setViewName("Registro/registrofuncionarios.html");
			return mv;
		}

		case ADM: {
			mv.addObject("matricula", GeradordeMatricula.generateMatricula(Adm));
			mv.addObject("senha", GeradordeMatricula.geradorSenha());
			mv.setViewName("Registro/registrofuncionarios.html");
			return mv;

		}

		case ALUNO: {

			mv.addObject("matricula", GeradordeMatricula.generateMatricula(aluno));
			mv.addObject("senha", GeradordeMatricula.geradorSenha());
			mv.addObject("Turmas", turmaImplService.BuscarTodas());
			mv.setViewName("Aluno/registroAluno.html");
			return mv;

		}

		default:
			mv.setViewName("redirect:/error");
			return mv;

		}

	}
// metodo que adiciona o funcionario no sistema
	@PostMapping("/adicionar-funcionario")
	public ModelAndView AddFuncionarios(@RequestParam("nome") String nome, @RequestParam("matricula") String matricula,
			@RequestParam("email") String email, @RequestParam("senha") String senha,
			@RequestParam("telefone") String telefone, HttpSession session, SufixoMatricula sufixoMatricula) {

		ModelAndView mv = new ModelAndView();

		try {

			Abrangencia abrangencia = (Abrangencia) session.getAttribute("selectaddFuncionario");

			switch (abrangencia) {

			case PROFESSOR: {

				Professor professor = new Professor();
				String professorSufixo = String.valueOf(sufixoMatricula.PROF);

				professor.setNome(nome);
				professor.setEmail(email);
				professor.setSenha(senha);
				professor.setMatricula(matricula);
				professor.setTelefone(telefone);

				professorImplService.Salvar(professor);
				mv.addObject("successMessage", "Professor adicionado ao Sistema!.");
				mv.setViewName("Registro/registrofuncionarios.html");
				mv.addObject("matricula", GeradordeMatricula.generateMatricula(professorSufixo));
				mv.addObject("senha", GeradordeMatricula.geradorSenha());
				mv.addObject("funcionario", new Professor());
				break;
			}

			case ADM: {
				Administrador administrador = new Administrador();
				String string = String.valueOf(sufixoMatricula.ADM);

				administrador.setEmail(email);
				administrador.setMatricula(matricula);
				administrador.setNome(nome);
				administrador.setTelefone(telefone);
				administrador.setSenha(senha);

				administradorImplService.Salvar(administrador);
				mv.addObject("successMessage", "Administrador adicionado ao Sistema!.");
				mv.setViewName("Registro/registrofuncionarios.html");
				mv.addObject("matricula", GeradordeMatricula.generateMatricula(string));
				mv.addObject("senha", GeradordeMatricula.geradorSenha());
				mv.addObject("funcionario", new Administrador());

				break;

			}

			default:

				mv.setViewName("Professor/registroprofessrores.html");
				mv.addObject("errorMessage", "Erro ao adicionar Professor ao Sistema:  ");
				break;
			}

			return mv;

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;

		}

	}
//metodo que adiciona o aluno no sistema
	@PostMapping("/adicionar_aluno")
	public ModelAndView AdicionarAluno(@RequestParam("id_turma") Long id_turma, SufixoMatricula sufixo,
			@RequestParam("matricula") String matricula, @RequestParam("senha") String senha,
			@RequestParam("data") @DateTimeFormat(pattern = "yyyy-MM-dd") Date data,
			@RequestParam("nome") String nome) {
		System.out.println("Data recebida: " + data);
		ModelAndView mv = new ModelAndView();
		String aluno = String.valueOf(sufixo.ALU);

		System.out.println("turma: " + id_turma);
		System.out.println("sufixo: " + sufixo);
		System.out.println("matricula: " + matricula);
		System.out.println("senha: " + senha);
		System.out.println("data_nascimento: " + data);
		System.out.println("nome: " + nome);

		try {
			Turma turma = new Turma();
			Aluno alunos = new Aluno();

			turma.setId(id_turma);

			alunos.setTurmaId(turma);
			alunos.setMatricula(matricula);
			alunos.setSenha(senha);
			alunos.setDataNascimento(data);
			alunos.setNome(nome);

			alunoImplService.Salvar(alunos);

			mv.addObject("successMessage", "Aluno adicionado ao Sistema!.");
			mv.addObject("matricula", GeradordeMatricula.generateMatricula(aluno));
			mv.addObject("senha", GeradordeMatricula.geradorSenha());
			mv.addObject("Aluno", new Aluno());
			mv.addObject("Turmas", turmaImplService.BuscarTodas());
			mv.setViewName("Aluno/registroAluno.html");
			return mv;

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;

		}

	}

	public Abrangencia[] getAbrangencia() {

		return Abrangencia.values();
	}

}
