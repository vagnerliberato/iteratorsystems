package br.iteratorsystems.cps.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.dao.DaoLogin;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

public class LoginUserHandler {

	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private DaoLogin idaoLogin;

	public Tabelas_Login doLogin(final String username, final String password) throws CpsHandlerException {
		final String message = "getting Tabelas_Login with username: " + username+ ", password: " + password;
		log.debug(message);
		Tabelas_Login login = null;
		try {
			idaoLogin = new DaoLogin();
			login = idaoLogin.get(username, password);
			return login;
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}

	public Tabelas_Login getLogin(final Integer id) throws CpsHandlerException{
		final String message = "getting Tabelas_Login with id: "+id;
		log.debug(message);
		Tabelas_Login login = null;
		try {
			idaoLogin = new DaoLogin();
			login = idaoLogin.get(id);
			return login;
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public Tabelas_Usuario getUserRelated(final Integer idLogin) throws CpsHandlerException {
		final String message = "getting Tabelas_Usuario with idLogin: " + idLogin;
		log.debug(message);
		Tabelas_Usuario usuario = null;
		try {
			idaoLogin = new DaoLogin();
			usuario = idaoLogin.getUsuarioRelated(idLogin);
			return usuario;
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}

	public Tabelas_Endereco getEnderecoRelated(final Integer idUsuario)	throws CpsHandlerException {
		final String message = "getting Tabelas_Endereco with idUsuario: " + idUsuario;
		log.debug(message);
		Tabelas_Endereco endereco = null;
		try {
			idaoLogin = new DaoLogin();
			endereco = idaoLogin.getEnderecoRelated(idUsuario);
			return endereco;
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public boolean checkPassword(String pass) throws CpsHandlerException{
		final String message = "checking if password existis: " + pass;
		log.debug(message);
		boolean exists = false;
		try{
			idaoLogin = new DaoLogin();
			exists = idaoLogin.checkPassword(pass);
			return exists;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
}
