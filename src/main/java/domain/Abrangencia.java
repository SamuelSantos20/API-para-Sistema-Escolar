package domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * 
 */
public enum Abrangencia {

	ALUNO("ALUNO") , 
	ADM("ADM") , 
	PROFESSOR("PROFESSOR");

	@Enumerated(EnumType.STRING)
	private String  Abrangencia;
	
	Abrangencia(String string) {
		this.Abrangencia = string;
	}

	
	
	public String getAbrangencia() {
		return Abrangencia;
	}

	
	
	
	
	
}
