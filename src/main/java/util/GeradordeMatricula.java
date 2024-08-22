package util;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;



@Service
public class GeradordeMatricula {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int ID_LENGTH = 6; 

   
    public static String generateMatricula(String type) {
       
        StringBuilder idBuilder = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) {
            int digit = secureRandom.nextInt(10);
            idBuilder.append(digit);
        }

        
        String id = idBuilder.toString();
        return id + type;
    }
    
    
    public static Long geradorSenha() {
      
        Long senha = 0L;

        for (int i = 0; i < ID_LENGTH; i++) {
            Long scuritySenha = secureRandom.nextLong(12);

            senha = senha * 10 + scuritySenha;
        }

        return senha;
    }

    
}
