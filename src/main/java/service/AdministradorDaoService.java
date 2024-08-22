package service;

import java.util.List;
import java.util.Optional;

import domain.Administrador;

public interface AdministradorDaoService {

	void Salvar(Administrador administrador);

	void Atualizar(Administrador administrador);

	void Deletar(Long id);

	Administrador ListarPorId(Long id);

	List<Administrador> ListarTodos();
	
	Optional<Administrador> BuscarPorMatriculaeSenha(String matricula, String senha);
}
