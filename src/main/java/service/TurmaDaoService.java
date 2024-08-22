package service;

import java.util.List;

import Dto.TurmaDto;
import domain.Turma;

public interface TurmaDaoService {
	
	

	void Salvar(Turma turma);

	void Atualizar(Turma turma);

	void Deletar(Long id);

	Turma BuscaePorId(Long id);

	List<Turma> BuscarTodas();
	
	 List<Turma> ListarTurmaId( Long id);
	 
	 List<TurmaDto> pesquisarTurmas(String texto);

}
