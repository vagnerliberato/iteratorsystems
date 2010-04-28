package br.iteratorsystems.cps.handler;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.IDao;

public class UserManagementHandler extends Handler{

	private static final Log log = LogFactory.getLog(UserManagementHandler.class);
	
	private IDao<USUARIO> idaoUsuario;
	private IDao<LOGIN> idaoLogin;
	private IDao<ENDERECO> idaoEndereco;
	
	public void save(final USUARIO usuario,final LOGIN login,final ENDERECO endereco) throws CpsHandlerException{
		final String message = "saving object with instance: "+usuario+" "+login+" "+endereco;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = getSession().beginTransaction();
			
			idaoUsuario = new Dao<USUARIO>();
			Integer id = (Integer) idaoUsuario.save(usuario);
			
			idaoLogin = new Dao<LOGIN>();
			login.setIdLogin(id);
			idaoLogin.save(login);
			
			idaoEndereco = new Dao<ENDERECO>();
			endereco.setIdUsuario(id);
			idaoEndereco.save(endereco);
			
			transaction.commit();
			log.debug("success!");
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			transaction.rollback();
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public void delete(Object instance) throws CpsHandlerException {
	}

//	public LOGIN get(LOGIN instance) throws CpsHandlerException {
//		final String message = "getting All LOGIN object WITH instances: "+instance;
//		log.debug(message);
//		try{
//			idaoLogin = new Dao<LOGIN>();
//			idaoLogin.get(instance)
//		}catch (CpsDaoException e) {
//		    final String errMsg = "error! "+message;
//			log.error(errMsg,e);
//			throw new CpsHandlerException(errMsg,e);
//		}
//		return null;
//	}

	public Collection<USUARIO> getAllUser() throws CpsHandlerException {
		final String message = "getting All USUARIO object instances";
		log.debug(message);
		Collection<USUARIO> dados= null;
		try{
			idaoUsuario = new Dao<USUARIO>();
			dados = idaoUsuario.getAll(new USUARIO());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}

	public Collection<LOGIN> getAllLogin() throws CpsHandlerException {
		final String message = "getting All LOGIN object instances";
		log.debug(message);
		Collection<LOGIN> dados= null;
		try{
			idaoLogin = new Dao<LOGIN>();
			dados = idaoLogin.getAll(new LOGIN());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public void update(Object instance) throws CpsHandlerException {
	}
}
