package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dto.AlunoDto;
import dao.AlunoDao;
import domain.Aluno;

@Service
@Transactional(readOnly = false)
public class AlunoImplService implements AlunoDaoService {

	@Autowired
	private AlunoDao alunoDao;

	@Override
	public void Salvar(Aluno aluno) {
		alunoDao.save(aluno);

	}

	@Override
	public void Atualizar(Aluno aluno) {
		alunoDao.update(aluno);
	}

	@Override
	public void Deletar(Long id) {
		alunoDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Aluno buscarporId(Long id) {

		return alunoDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Aluno> ListarTodos() {

		return alunoDao.findAll();
	}

	public List<Aluno> ListarOneAluno(Long id) {
		
		return alunoDao.ListarAluno(id);
	}
	
	//metodo que faz a validção do aluno no sistema para saber se ele é aluno é puxada no banco de dados  a matrica e senha inserida por ele.
	@Override
	@Transactional(readOnly = true)
	public Optional<Aluno> BuscarPorMatriculaeSenha(String matricula, String senha) {

		return alunoDao.findByMatriculaAndSenha(matricula, senha);
	}
// lista completa de todos alunos que tem no sistema 
	public List<AlunoDto> ListaCompleta() {

		List<Aluno> alunos = alunoDao.findAll();

		return alunos.stream().map(aluno -> new AlunoDto(aluno.getNome(), aluno.getMatricula(),
				aluno.getDataNascimento(), aluno.getSenha(), aluno.getTurmaId()

		)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
	}
// lista as informções de um unico aluno (utilizada apenas para acesso de aluno)
	public List<AlunoDto> ListaUnica(Long id) {

		List<Aluno> alunos = alunoDao.ListarAluno(id);

		return alunos.stream().map(aluno -> new AlunoDto(aluno.getNome(), aluno.getMatricula(),
				aluno.getDataNascimento(), aluno.getSenha(), aluno.getTurmaId()

		)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
	}

	// pesquisa de alunos no sistema, pesquisando por matricula ou nome 
	public List<AlunoDto> PesquisaDto(String texto) {

		List<Aluno> alunos = (List<Aluno>) alunoDao.BuscarporAlunoOrMatricula(texto);

		return alunos.stream().map(aluno -> new AlunoDto(aluno.getNome(), aluno.getMatricula(),
				aluno.getDataNascimento(), aluno.getSenha(), aluno.getTurmaId()

		)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
	}
// metodo tabém faz pesquisa mas utilizado para a geração de relatorio do aluno
	public List<Aluno> PesquisarAluno(String texto) {

		return alunoDao.BuscarporAlunoOrMatricula(texto);

	}

}
