package service;

import java.util.List;
import java.util.Optional;

import Dto.ProfessorDto;
import domain.Professor;

public interface ProfessorDaoService {

	
	void Salvar(Professor professor);

	void Atualizar(Professor professo);

	void Deletar(Long id);

	Professor ListarPorId(Long id);

	List<Professor> ListarTodos();
	
	Optional<Professor> BuscarPorMatriculaeSenha(String matricula, String senha);
	
	public List<ProfessorDto> RequestListarProfessores();
}
