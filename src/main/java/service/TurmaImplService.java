package service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dto.TurmaDto;
import dao.TurmaDao;
import domain.Aluno;
import domain.Diciplina;
import domain.Turma;

@Service
@Transactional(readOnly = false)
public class TurmaImplService implements TurmaDaoService {

	@Autowired
	private TurmaDao turmaDao;

	
	
	@Override
	public void Salvar(Turma turma) {
		turmaDao.save(turma);

	}

	@Override
	public void Atualizar(Turma turma) {
		turmaDao.update(turma);

	}

	@Override
	public void Deletar(Long id) {

		turmaDao.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Turma BuscaePorId(Long id) {

		return turmaDao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Turma> BuscarTodas() {

		return turmaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Turma> ListarTurmaId(Long id) {

		return turmaDao.ListarPorID(id);
	}

	@Transactional(readOnly = true)
	public List<TurmaDto> getDiciplina_Turma() {

		List<Turma> turma = turmaDao.findAll();

		return turma.stream()
				.map(turmas -> new TurmaDto(turmas.getId(), turmas.getNome(), turmas.getDescricao(), turmas.getCodigo_turma(), 
						turmas.getData_inicio(), turmas.getData_fim(),
						turmas.getAlunos(), 
						turmas.getDiciplinas())).toList();

	}
// metodo lista uma unica turma referente ao id da mesma 
	@Transactional(readOnly = true)
	public List<TurmaDto> getDiciplina_TurmaID(Long id) {

		List<Turma> turma = turmaDao.ListarPorID(id);

		return turma.stream()
				.map(turmas -> new TurmaDto(turmas.getId(), turmas.getNome(), turmas.getDescricao(), turmas.getCodigo_turma(),
						turmas.getData_inicio(), turmas.getData_fim(),
						turmas.getAlunos(),    
						turmas.getDiciplinas())).toList();

	}
// metodo faz a pesquisa da turma pelo seu codigo ou pelo seu nome 
	@Transactional(readOnly = true)
	public List<TurmaDto> pesquisarTurmas(String texto){
		
		List<Turma> turma = turmaDao.PesquisarTurmas(texto);

		return turma.stream()
				.map(turmas -> new TurmaDto(turmas.getId(), turmas.getNome(), turmas.getDescricao(), turmas.getCodigo_turma(),
						turmas.getData_inicio(), turmas.getData_fim(),
						turmas.getAlunos(),    
						turmas.getDiciplinas())).collect(Collectors.toList());
	}
	
	
//metodo lista todas as turmas e as diciplinas que são lecionadas nelas , assim como as informações de cada turma 
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	public List<Turma> ListarAsDiciplinaseTurmas(Long id) {

		return turmaDao.findTurmasByAlunoId(id);

	}

}
