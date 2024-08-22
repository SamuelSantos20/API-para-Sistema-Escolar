package controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Administrador;
import domain.Aluno;
import domain.Professor;
import jakarta.servlet.http.HttpSession;
import service.AdministradorImplService;
import service.AlunoImplService;
import service.ProfessorImplService;

// o login é reponsalvel pelo login de todos no sistema e da pagina de erro padrão para todos os metodos 
@Controller
public class LoginController {

	//injeção de dependencias para acessar o sistema
	@Autowired
	private AlunoImplService alunoImplService;

	@Autowired
	private AdministradorImplService administradorImplService;

	@Autowired
	private ProfessorImplService professorImplService;

	@GetMapping("/entrada")
	public ModelAndView RequestSelect() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("Abrangencias", getAbrangencia());
		mv.setViewName("Login/selectCargo.html");
		return mv;
	}
//metodo que faz a listagem de opções de login no sistema 
	@PostMapping("/select")
	public ModelAndView preLogin(@RequestParam("cargo") Abrangencia abrangencia, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		session.setAttribute("abrangencia", abrangencia);
		mv.setViewName("Login/login.html");

		return mv;
	}
// metodo que faz busca de matricula e senha no sistema para saber se o usuario faz parete dele ,  se logado ele será redireciona para a pagina que corresponde ao nivel de acesso dele
	@PostMapping("/login")
	public ModelAndView RequestLogin(@RequestParam("matricula") String matricula, @RequestParam("senha") String senha,
			HttpSession session) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/index");
		ModelAndView md = new ModelAndView("Login/login.html");
		try {

			Abrangencia abrangencia = (Abrangencia) session.getAttribute("abrangencia");

			System.out.println(senha);
			System.out.println(matricula);

			if (abrangencia == null) {
				md.addObject("errorMessage", "Abrangência não definida!");
				return md;
			}

			boolean ALUNO = false;

			boolean ADM = false;

			boolean PROFESSOR = false;
    
			switch (abrangencia) {
			case ADM:
				ADM = administradorImplService.BuscarPorMatriculaeSenha(matricula, senha) != null;
				break;
			case ALUNO:
				ALUNO = alunoImplService.BuscarPorMatriculaeSenha(matricula, senha) != null;
				break;
			case PROFESSOR:
			
				PROFESSOR = professorImplService.BuscarPorMatriculaeSenha(matricula, senha) != null;
				break;
			default:
				md.addObject("errorMessage", "Abrangência desconhecida!");
				return md;
			}

			if (ALUNO) {
				session.setAttribute("abrangencia", abrangencia);
                Optional<Aluno> alunos = alunoImplService.BuscarPorMatriculaeSenha(matricula, senha);
				session.setAttribute("aluno", alunos.get().getId());
				return mv;
			} else if (ADM) {
				session.setAttribute("abrangencia", abrangencia);
				Optional<Administrador> adm = administradorImplService.BuscarPorMatriculaeSenha(matricula, senha);
				session.setAttribute("administrador", adm.get().getId());
				return mv;

			} else if (PROFESSOR) {
				session.setAttribute("abrangencia", abrangencia);
				Optional<Professor> professor = professorImplService.BuscarPorMatriculaeSenha(matricula, senha);
				session.setAttribute("professor", professor.get().getId());
				return mv;
			}

			else {
				md.addObject("errorMessage", "Matrícula ou Senha Inválida!");
				return md;
				
			}

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}
	}
// metodo que redireciona para a pagina inicial correspondente ao nivel de acesso da pesso logada 
	@GetMapping("/index")
	public ModelAndView IndexAluno(HttpSession session) {
		ModelAndView mv = new ModelAndView();

		Abrangencia abrangencia = (Abrangencia) session.getAttribute("abrangencia");
		try {

			switch (abrangencia) {
			case ADM: {
				mv.setViewName("indexADM.html");
				return mv;

			}

			case ALUNO: {

				mv.setViewName("index.html");
				return mv;

			}

			case PROFESSOR: {

				mv.setViewName("indexProfessor.html");
				return mv;

			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + abrangencia);
			}

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;
		}

	}

	//metodo que resgata as abrangencias que existem no sistema 
	public Abrangencia[] getAbrangencia() {

		return Abrangencia.values();
	}

}
