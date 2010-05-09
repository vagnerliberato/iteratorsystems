package br.iteratorsystems.cps.handler;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.IDao;

public class AdministrationHandler extends Handler {
	
	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private IDao<LOGIN> daoLogin = null;
	
	public List<LOGIN> getAllLogins(final String username) throws CpsHandlerException{
		final String message = "getting all LOGIN with username like= "+username;
		log.debug(message);
		List<LOGIN> list = null;
		try{
			daoLogin = new Dao<LOGIN>();
			list = daoLogin.getAllLogin(username);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void deleteLogin(final LOGIN login) throws CpsHandlerException{
		final String message = "dropping LOGIN with instance= "+login;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = getSession().beginTransaction();
			IDao<USUARIO> daoUser = new Dao<USUARIO>();
			daoUser.delete(login.getUsuario());
			transaction.commit();
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
}
