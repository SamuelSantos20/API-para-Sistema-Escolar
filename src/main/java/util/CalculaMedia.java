package util;

public class CalculaMedia {

	
	public double Media(double trabalho , double teste , double prova) {
		
	double	media = (trabalho + teste + prova);
		
			if(media > 10) {
				
				return -1;
			}
	
			else {
				return media;
				
			}
		
	}
}
