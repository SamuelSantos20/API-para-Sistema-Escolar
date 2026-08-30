package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.AdministradorDao;
import domain.Administrador;

@Service
@Transactional(readOnly = false)
public class AdministradorImplService implements AdministradorDaoService {

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private AdministradorDao administradorDao;

	private boolean isAlreadyHashed(String senha) {
		return senha.startsWith("$2a$") || senha.startsWith("$2b$") || senha.startsWith("$2y$");
	}

	@Override
	public void Salvar(Administrador administrador) {
		if (administrador.getSenha() != null && !isAlreadyHashed(administrador.getSenha())) {
			administrador.setSenha(passwordEncoder.encode(administrador.getSenha()));
		}
		administradorDao.save(administrador);

	}

	@Override
	public void Atualizar(Administrador administrador) {
		if (administrador.getSenha() != null && !isAlreadyHashed(administrador.getSenha())) {
			administrador.setSenha(passwordEncoder.encode(administrador.getSenha()));
		}
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
