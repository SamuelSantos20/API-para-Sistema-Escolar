package dao;

import java.util.List;
import java.util.Optional;

import domain.Professor;

public interface ProfessorDao {

	void save(Professor professor);

	void update(Professor professo);

	void delete(Long id);

	Professor findById(Long id);

	List<Professor> findAll();
	
	Optional<Professor> findByMatriculaAndSenha(String matricula, String senha);

}
