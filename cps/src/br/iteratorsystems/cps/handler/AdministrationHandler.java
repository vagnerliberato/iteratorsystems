package br.iteratorsystems.cps.handler;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.dao.LojaDao;
import br.iteratorsystems.cps.dao.RedeDao;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.LojaId;
import br.iteratorsystems.cps.entities.Rede;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.IDao;

public class AdministrationHandler {
	
	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private IDao<Login> daoLogin = null;
	private IDao<Rede> daoRede = null;
	private IDao<Loja> daoLoja = null;
	
	public void saveNewRede(Rede instance) throws CpsHandlerException{
		final String message = "saving new instance for Rede with: "+instance;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			
			daoRede = new Dao<Rede>();
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
	
	public void saveNewLoja(Loja loja,Rede rede) throws CpsHandlerException{
		final String message = "saving new Loja with instance: "+loja+",Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			daoLoja = new Dao<Loja>();
			
			LojaId id = new LojaId();
			id.setId(daoLoja.getLastIdFrom(loja));
			id.setIdRede(rede.getId());
			
			loja.setDataultimamodificacao(new Date());
			
			loja.setTipodevenda('1');
			loja.setId(id);
			
			loja.setCnpj(loja.getCnpj().replace(".","").replace("/", "").replace("-",""));
			loja.setTelCelCom(loja.getTelCelCom().replace("-",""));
			loja.setTelFax(loja.getTelFax().replace("-",""));
			
			
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
	
	public Rede getRede(String nome) throws CpsHandlerException{
		final String message = "getting Rede with name: "+nome;
		log.debug(message);
		Rede result = null;
		try{
			daoRede = new Dao<Rede>();
			result = daoRede.getRede(nome);
			return result;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Loja> getAllCnpj() throws CpsHandlerException{
		final String message = "getting all Rede";
		log.debug(message);
		List<Loja> lista = null;
		try{
			daoLoja = new Dao<Loja>();
			lista = (List<Loja>) daoLoja.getAll(new Loja());
			return lista;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Rede> getAllRedes() throws CpsHandlerException{
		final String message = "getting all Rede";
		log.debug(message);
		List<Rede> list = null;
		try{
			daoRede = new Dao<Rede>();
			list = (List<Rede>) daoRede.getAll(new Rede());
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Rede> getAllRedes(String partName) throws CpsHandlerException{
		final String message = "getting all Rede with name like "+partName;
		log.debug(message);
		List<Rede> list = null;
		try{
			daoRede = new Dao<Rede>();
			list = (List<Rede>) daoRede.getRedesByName(partName);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Loja> getAllLojas(String partName) throws CpsHandlerException{
		final String message = "getting all Loja with partName like= "+partName;
		log.debug(message);
		List<Loja> list = null;
		try{
			daoLogin = new Dao<Login>();
			list = daoLogin.getLojasByName(partName);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Login> getAllLogins(final String username) throws CpsHandlerException{
		final String message = "getting all Login with username like= "+username;
		log.debug(message);
		List<Login> list = null;
		try{
			daoLogin = new Dao<Login>();
			list = daoLogin.getAllLogin(username);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void deleteLogin(final Login login) throws CpsHandlerException{
		final String message = "dropping Login with instance= "+login;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			IDao<Usuario> daoUser = new Dao<Usuario>();
			daoUser.delete(login.getUsuario());
			transaction.commit();
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void updateRede(final Rede rede) throws CpsHandlerException{
		final String message = "updating Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			RedeDao redeDao = new RedeDao(Rede.class, session);
			rede.setDataultimamodificacao(new Date());
			redeDao.update(rede);
			session.flush();
			transaction.commit();
			log.debug("success!");
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void updateLoja(final Loja loja) throws CpsHandlerException{
		final String message = "updating Loja with instance: "+loja;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			LojaDao lojaDao = new LojaDao(Loja.class, session);
			loja.setDataultimamodificacao(new Date());
			loja.setTipodevenda('1');
			lojaDao.update(loja);
			session.flush();
			transaction.commit();
			log.debug("success!");
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void excluirRede(Rede rede) throws CpsHandlerException{
		final String message = "deleting Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			RedeDao redeDao = new RedeDao(Rede.class, session);
			redeDao.excluir(rede);
			session.flush();
			transaction.commit();
			log.debug("success!");
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void excluirLoja(final Loja loja) throws CpsHandlerException{
		final String message = "deleting Rede with instance: "+loja;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			LojaDao lojaDao = new LojaDao(Loja.class, session);
			lojaDao.excluir(loja);
			session.flush();
			transaction.commit();
			log.debug("success!");
		} catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
}
