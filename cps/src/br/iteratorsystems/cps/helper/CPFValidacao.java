package br.iteratorsystems.cps.helper;

public class CPFValidacao {

	private CPFValidacao() {
			super();
		}

		/**
		* Valida se um cpf é valido.
		* @param entrada - String de entrada a ser validada.
		* @return Se o cpf é valido ou não.
		*/
	    public static boolean validarCpf(String entrada)
	    {
	       if (entrada != null && entrada.length() == 11 )
	       {
	           int     d1, d2; 
	           int     digito1, digito2, resto; 
	           int     digitoCPF; 
	           String  nDigResult; 
	           d1 = d2 = 0; 
	           digito1 = digito2 = resto = 0; 
	           for (int n_Count = 1; n_Count < entrada.length() -1; n_Count++) 
	           { 
	              digitoCPF = Integer.valueOf (entrada.substring(n_Count -1, n_Count)).intValue(); 
	//--------- Multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante. 
	              d1 = d1 + ( 11 - n_Count ) * digitoCPF; 
	//--------- Para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior. 
	              d2 = d2 + ( 12 - n_Count ) * digitoCPF; 
	           }; 
	//--------- Primeiro resto da divisão por 11. 
	           resto = (d1 % 11); 
	//--------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior. 
	           if (resto < 2) 
	              digito1 = 0; 
	           else 
	              digito1 = 11 - resto; 
	           d2 += 2 * digito1; 
	//--------- Segundo resto da divisão por 11. 
	           resto = (d2 % 11); 
	//--------- Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior. 
	           if (resto < 2) 
	              digito2 = 0; 
	           else 
	              digito2 = 11 - resto; 
	//--------- Digito verificador do CPF que está sendo validado. 
	           String nDigVerific = entrada.substring (entrada.length()-2, entrada.length()); 
	//--------- Concatenando o primeiro resto com o segundo. 
	           nDigResult = String.valueOf(digito1) + String.valueOf(digito2); 
	//--------- Comparar o digito verificador do cpf com o primeiro resto + o segundo resto. 
	           return nDigVerific.equals(nDigResult); 
	        }
	        else 
	            return false;
	    }
	}