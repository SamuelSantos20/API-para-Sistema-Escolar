package domain;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum SufixoMatricula {

	ADM("ADM"),
	PROF("PROF"),
	ALU("ALU");
	
	@Enumerated(EnumType.STRING)
	private String sufixo;	
	
	SufixoMatricula(String string) {
		this.sufixo = string;
	}

	public String getSufixo() {
		return sufixo;
	}

	public void setSufixo(String sufixo) {
		this.sufixo = sufixo;
	}

}
