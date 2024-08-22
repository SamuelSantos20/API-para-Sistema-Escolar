package util;

import java.security.SecureRandom;

public class GeradordeCodigo {

	private SecureRandom random = new SecureRandom();

	private static final int TAMANHO = 3;

	public String GeradorCodigo() {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < TAMANHO; i++) {

			Long number = random.nextLong(10);
			builder.append(number);

		}
		return builder.toString() + "T";
	}

}
