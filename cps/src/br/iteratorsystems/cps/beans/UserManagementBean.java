package br.iteratorsystems.cps.beans;

import java.util.Date;

import br.iteratorsystems.cps.common.FacesUtil;
import br.iteratorsystems.cps.common.FindAddress;
import br.iteratorsystems.cps.common.Resources;

public class UserManagementBean {

	private String nome;
	private String sobrenome;
	private Date aniversario;
	private String cpf;
	private String rg;
	private String orgao_expeditor;
	private String email;
	private String cep;
	private String logradouro;
	private String numero_res;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String ddd_tel_res;
	private String tel_res;
	private String ddd_tel_cel;
	private String tel_cel;
	private String username;
	private String senha_antiga;
	private String nova_senha;
	private String confirma_senha;
	private String mensagem_informacao;

	private FindAddress findAddress;

	private boolean firstAccess;
	private boolean validUsername = true;

	public UserManagementBean() {}

	public static final String[] msgs_informacao = 
		{"Nome de usuário já cadastrado na base de dados",
		"Não são permitidos espaços em branco",
		"Sua mãe é minha!"};
	
	public void find() {
		findAddress = new FindAddress();
		findAddress.find(this.getCep());

		this.setLogradouro(findAddress.getLogradouro());
		this.setBairro(findAddress.getBairro());
		this.setCidade(findAddress.getCidade());
		this.setEstado(findAddress.getEstado());
		findAddress = null;
	}

	public String novo() {
		String regex = "[A-Za-z0-9\\._-]+@[A-Za-z]+\\.[A-Za-z]+";

		if (this.getEmail() == null || this.getCep() == null
				|| "".equals(this.getEmail()) || "".equals(this.getCep())) {
			FacesUtil.errorMessage("", Resources.getErrorProperties()
					.getString("empty_email_cep"), "preencha email e cep");
			return "";
		}

		if (this.getEmail().matches(regex)) {
			this.find();
			this.setFirstAccess(true);
			return "toCadUser";
		} else {
			FacesUtil.errorMessage("", Resources.getErrorProperties()
					.getString("invalid_email"), "email invalido");
			return "";
		}
	}

	// TODO Fazer limpar o formulario!
	public String limpa() {
		this.cep = "";
		this.nome = "";
		this.setComplemento("");
		this.nova_senha = "";
		return null;
	}

	public void findUser() {
		System.out.println("find user with name " + this.getUsername());
		this.setValidUsername(false);
		int a = (int) (Math.random()*3);
		this.setMensagem_informacao(msgs_informacao[a]);
	}

	public void salva() {
		if (!this.validateForm())
			return;
		if(this.firstAccess){
			
		}else{
			
		}
	}

	public void atualiza() {

	}

	public boolean validateForm() {
		if (this.firstAccess) {
			if (!this.validUsername) {
				return false;
			}
			if (this.getNova_senha() == null
					|| this.getConfirma_senha() == null
					|| "".equals(this.getNova_senha())
					|| "".equals(this.getConfirma_senha())) {
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("empty_password_field"),
						"preencha campo senha");
				return false;
			}
			if (!this.getConfirma_senha().equals(this.getNova_senha())) {
				FacesUtil.errorMessage("", Resources.getErrorProperties().getString("wrong_password_confirmation"),"confirmaçao incorreta");
				return false;
			}
			return true;
		}
		if (!this.firstAccess) {
			if (this.getSenha_antiga() != null
					|| !"".equals(this.getSenha_antiga())) {
				if (this.getNova_senha() != null
						&& this.getConfirma_senha() != null) {
					if (!this.getNova_senha().equals(this.getConfirma_senha())) {
						FacesUtil.errorMessage("", Resources.getErrorProperties().getString("wrong_password_confirmation"),"confirmaçao incorreta");
						return false;
					}
				}
				return true;
			}
		}
		return true;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the sobrenome
	 */
	public String getSobrenome() {
		return sobrenome;
	}

	/**
	 * @param sobrenome
	 *            the sobrenome to set
	 */
	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	/**
	 * @return the aniversario
	 */
	public Date getAniversario() {
		return aniversario;
	}

	/**
	 * @param aniversario
	 *            the aniversario to set
	 */
	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg
	 *            the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}

	/**
	 * @return the orgao_expeditor
	 */
	public String getOrgao_expeditor() {
		return orgao_expeditor;
	}

	/**
	 * @param orgaoExpeditor
	 *            the orgao_expeditor to set
	 */
	public void setOrgao_expeditor(String orgaoExpeditor) {
		orgao_expeditor = orgaoExpeditor;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the ddd_tel_res
	 */
	public String getDdd_tel_res() {
		return ddd_tel_res;
	}

	/**
	 * @param dddTelRes
	 *            the ddd_tel_res to set
	 */
	public void setDdd_tel_res(String dddTelRes) {
		ddd_tel_res = dddTelRes;
	}

	/**
	 * @return the tel_res
	 */
	public String getTel_res() {
		return tel_res;
	}

	/**
	 * @param telRes
	 *            the tel_res to set
	 */
	public void setTel_res(String telRes) {
		tel_res = telRes;
	}

	/**
	 * @return the ddd_tel_cel
	 */
	public String getDdd_tel_cel() {
		return ddd_tel_cel;
	}

	/**
	 * @param dddTelCel
	 *            the ddd_tel_cel to set
	 */
	public void setDdd_tel_cel(String dddTelCel) {
		ddd_tel_cel = dddTelCel;
	}

	/**
	 * @return the tel_cel
	 */
	public String getTel_cel() {
		return tel_cel;
	}

	/**
	 * @param telCel
	 *            the tel_cel to set
	 */
	public void setTel_cel(String telCel) {
		tel_cel = telCel;
	}

	/**
	 * @return the senha_antiga
	 */
	public String getSenha_antiga() {
		return senha_antiga;
	}

	/**
	 * @param senhaAntiga
	 *            the senha_antiga to set
	 */
	public void setSenha_antiga(String senhaAntiga) {
		senha_antiga = senhaAntiga;
	}

	/**
	 * @return the nova_senha
	 */
	public String getNova_senha() {
		return nova_senha;
	}

	/**
	 * @param novaSenha
	 *            the nova_senha to set
	 */
	public void setNova_senha(String novaSenha) {
		nova_senha = novaSenha;
	}

	/**
	 * @return the confirma_senha
	 */
	public String getConfirma_senha() {
		return confirma_senha;
	}

	/**
	 * @param confirmaSenha
	 *            the confirma_senha to set
	 */
	public void setConfirma_senha(String confirmaSenha) {
		confirma_senha = confirmaSenha;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the logradouro
	 */
	public String getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            the logradouro to set
	 */
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the cidade
	 */
	public String getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFirstAccess(boolean firstAccess) {
		this.firstAccess = firstAccess;
	}

	public boolean isFirstAccess() {
		return firstAccess;
	}

	public void setNumero_res(String numero_res) {
		this.numero_res = numero_res;
	}

	public String getNumero_res() {
		return numero_res;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setValidUsername(boolean validUsername) {
		this.validUsername = validUsername;
	}

	public boolean isValidUsername() {
		return validUsername;
	}

	public void setMensagem_informacao(String mensagem_informacao) {
		this.mensagem_informacao = mensagem_informacao;
	}

	public String getMensagem_informacao() {
		return mensagem_informacao;
	}
}
