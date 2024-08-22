package service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dto.DiciplinaDto;
import dao.DiciplinaDao;
import domain.Diciplina;
import domain.Professor;

@Service
@Transactional(readOnly = false)
public class DiciplinaImplService implements DicplinaDaoService {

	@Autowired
	private DiciplinaDao diciplinaDao;

	@Override
	public void Salvar(Diciplina diciplina) {
		diciplinaDao.save(diciplina);

	}

	@Override
	public void Atualizar(Diciplina diciplina) {
		diciplinaDao.update(diciplina);

	}

	@Override
	public void Deletar(Long id) {
		diciplinaDao.delete(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Diciplina ListarPeloId(Long id) {

		return diciplinaDao.findById(id);
	}

	public List<Diciplina> PesquisarDiciplinasId(Long id){
		return diciplinaDao.PesquisarDiciplinaId(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Diciplina> ListarTodas() {

		return diciplinaDao.findAll();
	}

	public List<DiciplinaDto> getDiciplina_professorNome() {

		List<Diciplina> diciplinas = diciplinaDao.findAll();

		return diciplinas.stream()
				.map(diciplina -> new DiciplinaDto(diciplina.getId(), diciplina.getNome(),
						diciplina.getProfessor().stream().map(Professor::getNome).collect(Collectors.toSet())))
				.collect(Collectors.toList());

	}

	public List<Diciplina> PesquisarDiciplina(String texto) {

		return diciplinaDao.BuscarPordiciplina(texto);

	}

	
}
