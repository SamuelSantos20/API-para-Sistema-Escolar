package controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Abrangencia;
import domain.Diciplina;
import domain.Professor;
import jakarta.servlet.http.HttpSession;
import service.DiciplinaImplService;
import service.ProfessorImplService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class DiciplinaController {
// injeções de dependencias para acesso ao banco de dados
	@Autowired
	private DiciplinaImplService diciplinaImplService;

	@Autowired
	private ProfessorImplService professorImplService;

	//metodo que envias os professores para as paginas e os objetos necessario para a adição da diciplina
	@GetMapping("/addDiciplina")
	public ModelAndView RequestAddDiciplina(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		try {

			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {

				mv.addObject("professor", professorImplService.ListarTodos());
				mv.addObject("Diciplina", new Diciplina());
				mv.setViewName("Diciplinas/adicionar-diciplinas.html");
				return mv;

			}
			
			else {
				mv.setViewName("indexADM.html");
				mv.addObject("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM ");
				return mv;

				
				
				
			}

		} catch (Exception e) {

			mv.setViewName("redirect:/error");
			return mv;
		}

		
	
	}
	
	//metodo que faz a adição da diciplina no sistema , juntamente com o professor da diciplina

	@PostMapping("/adicionar-materia")
	public ModelAndView AddDiciplina(Diciplina diciplina, @RequestParam("professorId") Long professor_id , HttpSession session) {

		ModelAndView mv = new ModelAndView();
		System.out.println(professor_id);
		try {
			if (session.getAttribute("abrangencia") == Abrangencia.ADM) {
				Professor professor = new Professor();
				professor.setId(professor_id);

				diciplina.getProfessor().add(professor);

				diciplinaImplService.Salvar(diciplina);

				mv.addObject("professor", professorImplService.ListarTodos());
				mv.addObject("Diciplina", new Diciplina());
				mv.addObject("successMessage", "Diciplina adicionada ao Sistema!.");
				mv.setViewName("Diciplinas/adicionar-diciplinas.html");

				return mv;

			}

			else {
				mv.setViewName("indexADM.html");
				mv.addObject("errorMessage", "ESSE SISTEMA É DISPONIVEL APENAS PARA USUARIOS A NIVÉL ADM ");
				return mv;

			}

		} catch (Exception e) {
			mv.setViewName("redirect:/error");
			return mv;
		}
	}

	//metodo que faz a listagem , na pagina ,  de diciplinas e dos professores que as lecionam 
	@GetMapping("/Listar-Diciplinas")
	public ModelAndView ListarDiciplinas() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("Diciplina", new Diciplina());
		mv.addObject("Diciplinas", diciplinaImplService.getDiciplina_professorNome());
		mv.setViewName("Diciplinas/disciplinas.html");

		return mv;
	}

}
