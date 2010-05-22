package br.iteratorsystems.cps.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;

import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.dao.LojaDao;
import br.iteratorsystems.cps.dao.RedeDao;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.LOJA;
import br.iteratorsystems.cps.entities.LOJAID;
import br.iteratorsystems.cps.entities.REDE;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.IDao;

public class AdministrationHandler extends Handler {
	
	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private IDao<LOGIN> daoLogin = null;
	private IDao<REDE> daoRede = null;
	private IDao<LOJA> daoLoja = null;
	
	public void saveNewRede(REDE instance) throws CpsHandlerException{
		final String message = "saving new instance for REDE with: "+instance;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = getSession().beginTransaction();
			
			daoRede = new Dao<REDE>();
			instance.setDataultimamodificacao(new Date());
			instance.setLojas(null);
			instance.setId(daoRede.getLastIdFromModel(instance));
			daoRede.save(instance);
			
			transaction.commit();
			log.debug("success!");
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			transaction.rollback();
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void saveNewLoja(LOJA loja,REDE rede) throws CpsHandlerException{
		final String message = "saving new LOJA with instance: "+loja+", and REDE with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = getSession().beginTransaction();
			daoLoja = new Dao<LOJA>();
			
			LOJAID id = new LOJAID();
			id.setId(daoLoja.getLastIdFrom(loja));
			id.setIdRede(rede.getId());
			loja.setId(id);
			
			loja.setDataultimamodificacao(new Date());
			daoLoja.save(loja);
			transaction.commit();
			log.debug("success!");
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			transaction.rollback();
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public REDE getRede(String nome) throws CpsHandlerException{
		final String message = "getting REDE with name: "+nome;
		log.debug(message);
		REDE result = null;
		try{
			daoRede = new Dao<REDE>();
			result = daoRede.getRede(nome);
			return result;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<LOJA> getAllCnpj() throws CpsHandlerException{
		final String message = "getting all REDE";
		log.debug(message);
		List<LOJA> lista = null;
		try{
			daoLoja = new Dao<LOJA>();
			lista = (List<LOJA>) daoLoja.getAll(new LOJA());
			return lista;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<REDE> getAllRedes() throws CpsHandlerException{
		final String message = "getting all REDE";
		log.debug(message);
		List<REDE> list = null;
		try{
			daoRede = new Dao<REDE>();
			list = (List<REDE>) daoRede.getAll(new REDE());
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<REDE> getAllRedes(String partName) throws CpsHandlerException{
		final String message = "getting all REDE with name like "+partName;
		log.debug(message);
		List<REDE> list = null;
		try{
			daoRede = new Dao<REDE>();
			list = (List<REDE>) daoRede.getRedesByName(partName);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
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
	
	public void updateRede(final REDE rede){
		Session session = getSession();
		RedeDao redeDao = new RedeDao(REDE.class, session);
		redeDao.update(rede);
		session.flush();
	}
	
	public void updateLoja(final LOJA loja){
		Session session = getSession();
		LojaDao lojaDao = new LojaDao(LOJA.class, session);
		lojaDao.update(loja);
		session.flush();
	}
}
