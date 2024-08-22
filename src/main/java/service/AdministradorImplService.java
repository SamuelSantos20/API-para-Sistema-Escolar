package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AdministradorDao;
import domain.Administrador;

@Service
@Transactional(readOnly = false)
public class AdministradorImplService implements AdministradorDaoService {

	@Autowired
	private AdministradorDao administradorDao;

	@Override
	public void Salvar(Administrador administrador) {
		administradorDao.save(administrador);

	}

	@Override
	public void Atualizar(Administrador administrador) {
		administradorDao.update(administrador);
	}

	@Override
	public void Deletar(Long id) {
		administradorDao.delete(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Administrador ListarPorId(Long id) {
		return administradorDao.findById(id);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<Administrador> ListarTodos() {
		
		return administradorDao.findAll();
	}

	//metodo que faz a validção do adm no sistema para saber se ele é adm é puxada no banco de dados  a matrica e senha inserida por ele.
	@Override
	@Transactional(readOnly = true)
	public Optional<Administrador> BuscarPorMatriculaeSenha(String matricula, String senha) {
		
		return administradorDao.findByMatriculaAndSenha(matricula ,senha);
	}

}
