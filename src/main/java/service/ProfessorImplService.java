package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dto.ProfessorDto;
import dao.ProfessorDao;
import domain.Diciplina;
import domain.Professor;

@Service
@Transactional(readOnly = false)
public class ProfessorImplService implements ProfessorDaoService {

	@Autowired
	private ProfessorDao professorDao;

	@Override
	public void Salvar(Professor professor) {
		
		professorDao.save(professor);
	}

	@Override
	public void Atualizar(Professor professo) {
	professorDao.update(professo);
		
	}

	@Override
	public void Deletar(Long id) {
	professorDao.delete(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Professor ListarPorId(Long id) {
				return professorDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Professor> ListarTodos() {
		
		return professorDao.findAll();
	}

	//metodo que faz a validção do professor  no sistema para saber se ele é professor  é puxada no banco de dados  a matrica e senha inserida por ele.
	@Override
	@Transactional(readOnly = true)
	public Optional<Professor> BuscarPorMatriculaeSenha(String matricula, String senha) {
		
		return professorDao.findByMatriculaAndSenha(matricula,senha);
	}
	// metodo resgata e lista todos professores cadastrados no sistema com suas respectivas informações
	public List<ProfessorDto> RequestListarProfessores() {
		List<Professor> professores =  professorDao.findAll();
		
		return professores.stream().map( professor -> new ProfessorDto(professor.getNome(), professor.getMatricula(), professor.getSenha(), professor.getTelefone(), professor.getEmail(), professor.getDiciplinas().stream().map(Diciplina :: getNome).collect(Collectors.toSet()))).collect(Collectors.toList());
	}
	
	
}
