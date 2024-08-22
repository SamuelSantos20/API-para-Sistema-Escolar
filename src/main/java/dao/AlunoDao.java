package dao;

import java.util.List;
import java.util.Optional;

import domain.Aluno;

public interface AlunoDao {

	void save(Aluno aluno);

	void update(Aluno aluno);

	void delete(Long id);

	Aluno findById(Long id);

	List<Aluno> findAll();

	 public Optional<Aluno> findByMatriculaAndSenha(String matricula, String senha);

	 public List<Aluno> ListarAluno(Long id);
	 
	 public List<Aluno> BuscarporAlunoOrMatricula(String texto);
}
