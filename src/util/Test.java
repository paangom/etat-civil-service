package util;

import services.ActivationServices;


public class Test {

	
	@SuppressWarnings("unused")
	private static final int[] key = {3,1,4,2};
	static String code = "";
	static String clair = "";
	ActivationServices activeService = new ActivationServices();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String code = Chiffertext("SYSCOWEB00061SERVICE11082014ETATCIVIL11102014SERVICE00000088SYSCO001");
		System.out.println(code);
		
	
	}
	
	public static String Chiffertext(String plainText){
		int nbreLettre = plainText.length();
		int lettremanq = nbreLettre % 4;
		if(lettremanq != 0)
			lettremanq = 4 - lettremanq;
		int x = 0;
		while(lettremanq > x){
			plainText = plainText+"@";
			x++;
		}
		int nbreColone = plainText.length()/4;
		char[][] tab = new char[nbreColone][4];
		int t = 0;
		for(int i = 0; i < nbreColone ; i++){
			for(int j = 0 ; j < 4 ; j++){
				tab[i][j] = plainText.charAt(t);
				t++;
			}
		}
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][1];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][3];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][0];
		}
		code = code+"-";
		for(int i = 0; i<nbreColone; i++){
			code = code+tab[i][2];
		}
		
		return code;
	}
	
	public static String plainText(String chifer){
		int nbreColonne = chifer.length() / 4;
		char[][] tab = new char[nbreColonne][4];
		
		String[] code = chifer.split("-");
		for(int i = 0; i < nbreColonne; i++){
			tab[i][0] = code[2].charAt(i);
		}
		
		for(int i = 0; i < nbreColonne; i++){
			tab[i][1] = code[0].charAt(i);
		}
		
		for(int i = 0; i < nbreColonne; i++){
			tab[i][2] = code[3].charAt(i);
		}
		
		for(int i = 0; i < nbreColonne; i++){
			tab[i][3] = code[1].charAt(i);
		}
		
		for(int i = 0; i < nbreColonne; i++){
			for(int j = 0; j < 4; j++){
				clair = clair+tab[i][j];
			}
		}
		
		return clair;
	}

}
