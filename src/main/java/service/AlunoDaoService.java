package service;

import java.util.List;
import java.util.Optional;

import Dto.AlunoDto;
import domain.Aluno;

public interface AlunoDaoService {

	

	void Salvar(Aluno aluno);

	void Atualizar(Aluno aluno);

	void Deletar(Long id);

	Aluno buscarporId(Long id);

	List<Aluno> ListarTodos();

	  Optional<Aluno> BuscarPorMatriculaeSenha(String matricula, String senha);
	 
	  List<AlunoDto> ListaCompleta();
	 
	 
	 List<Aluno> PesquisarAluno(String texto);
	 
	 List<AlunoDto> ListaUnica(Long id);
	 
	 List<AlunoDto> PesquisaDto(String texto);
	 
	  List<Aluno> ListarOneAluno(Long id);
}
