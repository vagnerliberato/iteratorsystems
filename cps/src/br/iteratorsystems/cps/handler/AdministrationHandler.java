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
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_LojaId;
import br.iteratorsystems.cps.entities.Tabelas_Rede;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.IDao;

public class AdministrationHandler {
	
	private static final Log log = LogFactory.getLog(LoginUserHandler.class);
	private IDao<Tabelas_Login> daoLogin = null;
	private IDao<Tabelas_Rede> daoRede = null;
	private IDao<Tabelas_Loja> daoLoja = null;
	
	public void saveNewRede(Tabelas_Rede instance) throws CpsHandlerException{
		final String message = "saving new instance for Tabelas_Rede with: "+instance;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			
			daoRede = new Dao<Tabelas_Rede>();
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
	
	public void saveNewLoja(Tabelas_Loja loja,Tabelas_Rede rede) throws CpsHandlerException{
		final String message = "saving new Tabelas_Loja with instance: "+loja+",Tabelas_Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			daoLoja = new Dao<Tabelas_Loja>();
			
			Tabelas_LojaId id = new Tabelas_LojaId();
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
	
	public Tabelas_Rede getRede(String nome) throws CpsHandlerException{
		final String message = "getting Tabelas_Rede with name: "+nome;
		log.debug(message);
		Tabelas_Rede result = null;
		try{
			daoRede = new Dao<Tabelas_Rede>();
			result = daoRede.getRede(nome);
			return result;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Tabelas_Loja> getAllCnpj() throws CpsHandlerException{
		final String message = "getting all Tabelas_Rede";
		log.debug(message);
		List<Tabelas_Loja> lista = null;
		try{
			daoLoja = new Dao<Tabelas_Loja>();
			lista = (List<Tabelas_Loja>) daoLoja.getAll(new Tabelas_Loja());
			return lista;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Tabelas_Rede> getAllRedes() throws CpsHandlerException{
		final String message = "getting all Tabelas_Rede";
		log.debug(message);
		List<Tabelas_Rede> list = null;
		try{
			daoRede = new Dao<Tabelas_Rede>();
			list = (List<Tabelas_Rede>) daoRede.getAll(new Tabelas_Rede());
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Tabelas_Rede> getAllRedes(String partName) throws CpsHandlerException{
		final String message = "getting all Tabelas_Rede with name like "+partName;
		log.debug(message);
		List<Tabelas_Rede> list = null;
		try{
			daoRede = new Dao<Tabelas_Rede>();
			list = (List<Tabelas_Rede>) daoRede.getRedesByName(partName);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Tabelas_Loja> getAllLojas(String partName) throws CpsHandlerException{
		final String message = "getting all Tabelas_Loja with partName like= "+partName;
		log.debug(message);
		List<Tabelas_Loja> list = null;
		try{
			daoLogin = new Dao<Tabelas_Login>();
			list = daoLogin.getLojasByName(partName);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public List<Tabelas_Login> getAllLogins(final String username) throws CpsHandlerException{
		final String message = "getting all Tabelas_Login with username like= "+username;
		log.debug(message);
		List<Tabelas_Login> list = null;
		try{
			daoLogin = new Dao<Tabelas_Login>();
			list = daoLogin.getAllLogin(username);
			return list;
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void deleteLogin(final Tabelas_Login login) throws CpsHandlerException{
		final String message = "dropping Tabelas_Login with instance= "+login;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			IDao<Tabelas_Usuario> daoUser = new Dao<Tabelas_Usuario>();
			daoUser.delete(login.getUsuario());
			transaction.commit();
		}catch (CpsDaoException e) {
			final String errMsg = "error! " + message;
			log.error(errMsg, e);
			throw new CpsHandlerException(errMsg, e);
		}
	}
	
	public void updateRede(final Tabelas_Rede rede) throws CpsHandlerException{
		final String message = "updating Tabelas_Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			RedeDao redeDao = new RedeDao(Tabelas_Rede.class, session);
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
	
	public void updateLoja(final Tabelas_Loja loja) throws CpsHandlerException{
		final String message = "updating Tabelas_Loja with instance: "+loja;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			LojaDao lojaDao = new LojaDao(Tabelas_Loja.class, session);
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
	
	public void excluirRede(Tabelas_Rede rede) throws CpsHandlerException{
		final String message = "deleting Rede with instance: "+rede;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			RedeDao redeDao = new RedeDao(Tabelas_Rede.class, session);
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
	
	public void excluirLoja(final Tabelas_Loja loja) throws CpsHandlerException{
		final String message = "deleting Rede with instance: "+loja;
		log.debug(message);
		Transaction transaction = null;
		Session session = null;
		try {
			transaction = HibernateConfig.getSession().beginTransaction();
			session = HibernateConfig.getSession();
			LojaDao lojaDao = new LojaDao(Tabelas_Loja.class, session);
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
