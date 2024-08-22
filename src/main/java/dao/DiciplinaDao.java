package dao;

import java.util.List;

import domain.Diciplina;

public interface DiciplinaDao {

	void save(Diciplina diciplina);

	void update(Diciplina diciplina);

	void delete(Long id);

	Diciplina findById(Long id);

	List<Diciplina> findAll();
	
	public List<Diciplina> BuscarPordiciplina(String texto);
	
	List<Diciplina> PesquisarDiciplinaId(Long id);
}
