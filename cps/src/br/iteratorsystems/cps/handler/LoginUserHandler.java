package br.iteratorsystems.cps.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.dao.DaoLogin;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

public class LoginUserHandler {

	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private DaoLogin idaoLogin;

	public LOGIN doLogin(final String username, final String password) throws CpsHandlerException {
		final String message = "getting LOGIN with username: " + username+ ", password: " + password;
		log.debug(message);
		LOGIN login = null;
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

	public USUARIO getUserRelated(final Integer idLogin) throws CpsHandlerException {
		final String message = "getting USUARIO with idLogin: " + idLogin;
		log.debug(message);
		USUARIO usuario = null;
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

	public ENDERECO getEnderecoRelated(final Integer idUsuario)	throws CpsHandlerException {
		final String message = "getting ENDERECO with idUsuario: " + idUsuario;
		log.debug(message);
		ENDERECO endereco = null;
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
