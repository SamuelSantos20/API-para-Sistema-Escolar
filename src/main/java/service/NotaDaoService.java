package service;

import java.util.List;
import java.util.Optional;

import Dto.NotaDto;
import domain.Nota;

public interface NotaDaoService {

	void Salvar(Nota nota);

	void Atualizar(Nota nota);

	void Deletar(Long id);

	Nota ListarPorId(Long id);

	List<Nota> Listartodos();

	List<NotaDto> ListarRelatorio();

	List<NotaDto> ListarImpressaoNotaAluno(Long id);

	List<NotaDto> BuscarPorAluno(String texto);

	Optional<Nota> ListarIdAluno(Long id);
}
