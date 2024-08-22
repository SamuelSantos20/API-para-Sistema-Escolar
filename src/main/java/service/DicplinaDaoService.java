package service;

import java.util.List;

import domain.Diciplina;

public interface DicplinaDaoService {

	
	void Salvar(Diciplina diciplina);

	void Atualizar(Diciplina diciplina);

	void Deletar(Long id);

	Diciplina ListarPeloId(Long id);

	List<Diciplina> ListarTodas();
	
	public List<Diciplina>  PesquisarDiciplina(String texto);

	List<Diciplina> PesquisarDiciplinasId(Long id);
}
