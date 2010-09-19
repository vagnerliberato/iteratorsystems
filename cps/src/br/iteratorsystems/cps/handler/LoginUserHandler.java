package br.iteratorsystems.cps.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.iteratorsystems.cps.dao.DaoLogin;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;

public class LoginUserHandler {

	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private DaoLogin idaoLogin;

	public Login doLogin(final String username, final String password) throws CpsHandlerException {
		final String message = "getting Login with username: " + username+ ", password: " + password;
		log.debug(message);
		Login login = null;
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

	public Login getLogin(final Integer id) throws CpsHandlerException{
		final String message = "getting Login with id: "+id;
		log.debug(message);
		Login login = null;
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
	
	public Usuario getUserRelated(final Integer idLogin) throws CpsHandlerException {
		final String message = "getting Usuario with idLogin: " + idLogin;
		log.debug(message);
		Usuario usuario = null;
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

	public Usuario getLoginBy(String email,String username) throws CpsHandlerException {
		final String message = "recovering login";
		log.debug(message);
		Usuario usuario = null;
		try{
			idaoLogin = new DaoLogin();
			if(email != null) {
				usuario = idaoLogin.getByEmail(email);
			}else{
				usuario = idaoLogin.getByUsername(username);
			}
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
		return usuario;
	}
	
	public Endereco getEnderecoRelated(final Integer idUsuario)	throws CpsHandlerException {
		final String message = "getting Endereco with idUsuario: " + idUsuario;
		log.debug(message);
		Endereco endereco = null;
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
