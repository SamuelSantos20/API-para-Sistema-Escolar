package dao;

import java.util.List;
import java.util.Optional;

import domain.Administrador;

public interface AdministradorDao {


	void save(Administrador administrador);

	void update(Administrador administrador);

	void delete(Long id);

	Administrador findById(Long id);

	List<Administrador> findAll();
	
	Optional<Administrador> findByMatriculaAndSenha(String matricula, String senha);
}
