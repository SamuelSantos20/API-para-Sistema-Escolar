package dao;

import java.util.List;
import java.util.Optional;

import domain.Nota;



public interface NotaDao {

	void save(Nota nota);

	void update(Nota nota);

	void delete(Long id);

	Nota findById(Long id);

	List<Nota> findAll();
	
	List<Nota> NotasAluno(Long id);
	
	public List<Nota> BuscarNomeAluno(String texto);
	
	Optional<Nota> NotasAlunoEditar(Long id);
}
