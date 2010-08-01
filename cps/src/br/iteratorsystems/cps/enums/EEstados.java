package br.iteratorsystems.cps.enums;

/**
 * Enum de Estados do Brasil
 * @author André
 *
 */
public enum EEstados {
	
	AC("Acre"),
	AL("Alagoas"),
	AP("Amapá"),
	AM("Amazonas"),
	BA("Bahia"),
	CE("Ceará"),
	DF("Distrito Federal"),
	GO("Goiás"),
	ES("Espírito Santo"),
	MA("Maranhão"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Pará"),
	PB("Paraíba"),
	PR("Paraná"),
	PE("Pernambuco"),
	PI("Piauí"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	RO("Rondônia"),
	RR("Rôraima"),
	SP("São Paulo"),
	SC("Santa Catarina"),
	SE("Sergipe"),
	TO("Tocantins");
	
	/**
	 * Construtor default
	 * @param s
	 */
	EEstados(String s){
		this.setNome(s);
	}

	private String nome_completo;
	
	/**
	 * Modifica o nome do estado
	 * @param nome - Novo nome
	 */
	public void setNome(String nome) {
		this.nome_completo = nome;
	}
	
	/**
	 * Recupera o nome do estado
	 * @return Nome do estado.
	 */
	public String getNome() {
		return nome_completo;
	}
}
