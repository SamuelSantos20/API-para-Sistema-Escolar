package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Dto.NotaDto;
import dao.NotaDao;
import domain.Nota;

@Service
@Transactional(readOnly = false)
public class NotaImplService implements NotaDaoService {

	@Autowired
	private NotaDao notaDao;

	@Override
	public void Salvar(Nota nota) {
		notaDao.save(nota);

	}

	@Override
	public void Atualizar(Nota nota) {
		notaDao.update(nota);

	}

	@Override
	public void Deletar(Long id) {
		notaDao.delete(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Nota ListarPorId(Long id) {

		return notaDao.findById(id);
	}

	public Optional<Nota> ListarIdAluno(Long id) {
		return notaDao.NotasAlunoEditar(id);
	}
	@Override
	@Transactional(readOnly = true)
	public List<Nota> Listartodos() {

		return notaDao.findAll();
	}

	//metodo que faz a listagem de todas as notas de todos os alunos cadastrados no sistema  
	public List<NotaDto> ListarRelatorio(){
		
		List<Nota> list = notaDao.findAll();
		
		return list.stream().map(notas -> new NotaDto(notas.getMedia(),notas.getAluno_id().getNome(), notas.getDiciplina_id().getNome(), notas.getNota_trabalho(), notas.getNota_Teste(),
				notas.getNota_prova())).collect(Collectors.toList());
	}
	
	//recebe o id do aluno logado na plataforma ou o id do aluno que o adm ou professor deseja pegar o relatorio (metodo utilizado principalmente para o relatorio 
	public List<NotaDto> ListarImpressaoNotaAluno(Long id){
		
		List<Nota> list = notaDao.NotasAluno(id);
		
		return list.stream().map(notas -> new NotaDto(notas.getMedia(),notas.getAluno_id().getNome(), notas.getDiciplina_id().getNome(), notas.getNota_trabalho(), notas.getNota_Teste(),
				notas.getNota_prova())).collect(Collectors.toList());
	}
	
	//busca a nota do aluno pelo id dele no sistema (banco de dados )
	public List<NotaDto> BuscarPorAluno(String texto) {
		
List<Nota> list = notaDao.BuscarNomeAluno(texto);
		
		return list.stream().map(notas -> new NotaDto(notas.getMedia(),notas.getAluno_id().getNome(), notas.getDiciplina_id().getNome(), notas.getNota_trabalho(), notas.getNota_Teste(),
				notas.getNota_prova())).collect(Collectors.toList());
	}
	
	
}
