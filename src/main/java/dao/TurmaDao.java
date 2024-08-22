package dao;

import java.util.List;



import domain.Turma;

public interface TurmaDao {

	void save(Turma turma);

	void update(Turma turma);

	void delete(Long id);

	Turma findById(Long id);

	List<Turma> findAll();
	
    List<Turma> findTurmasByAlunoId(Long alunoId);
    
    List<Turma> ListarPorID(Long id);
    
    List<Turma> PesquisarTurmas(String texto);
}
