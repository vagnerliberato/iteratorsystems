package br.iteratorsystems.cps.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_Rede;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.interfaces.EntityAble;
import br.iteratorsystems.cps.interfaces.IDao;

public class Dao<T extends EntityAble> implements IDao<T> {

	private static final Log log = LogFactory.getLog(Dao.class);
	
	public Integer save(T instance) throws CpsDaoException, CpsConstraintException {
		final String message = "saving with istance: "+instance;
		log.debug(message);
		Integer id = null;
		try{
			Session session = HibernateConfig.getSession();
			Object obj = (Object) session.save(instance);
			
			if(obj instanceof Integer){
				id = (Integer) obj;
			}
			
			log.debug("success!");
			return id;
		}catch (ConstraintViolationException e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsConstraintException(errMsg,e);
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
	}
	
	public void delete(T instance) throws CpsDaoException,CpsConstraintException {
		final String message = "dropping instance: "+instance;
		log.debug(message);
		try{
			Session session = HibernateConfig.getSession();
			session.delete(instance);
			session.flush();
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
	}

	public Integer getIdUsuario(Tabelas_Usuario instance) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Usuario.class);
		criteria.add(Restrictions.eq("cpfUsuario",instance.getCpfUsuario()));
		Tabelas_Usuario user = (Tabelas_Usuario) criteria.uniqueResult();
		Integer id = user.getIdUsuario();
		return id;
	}
	
	public Integer getLastIdFrom(EntityAble entity) throws CpsDaoException{
		final String message = "retrieving last id from entity: "+entity.getClass().getSimpleName();
		log.debug(message);
		Integer lastId = null;
		try{
			String query = "select max(model.id.id) from "+entity.getClass().getSimpleName()+" model";
			Session session = HibernateConfig.getSession();
			Query q = session.createQuery(query);
			lastId = (Integer) q.uniqueResult();
			return lastId == null ? 1 : ++lastId;
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
	}
	
	public Integer getLastIdFromModel(EntityAble entity) throws CpsDaoException{
		final String message = "retrieving last id from entity: "+entity.getClass().getSimpleName();
		log.debug(message);
		Integer lastId = null;
		try{
			String query = "select max(model.id) from "+entity.getClass().getSimpleName()+" model";
			Session session = HibernateConfig.getSession();
			Query q = session.createQuery(query);
			lastId = (Integer) q.uniqueResult();
			return lastId == null ? 1 : ++lastId;
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
	}
	
	public Tabelas_Rede getRede(String nome) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Rede.class);
		criteria.add(Restrictions.eq("nome",nome));
		return (Tabelas_Rede) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Tabelas_Rede> getRedesByName(String nome) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Rede.class);
		criteria.add(Restrictions.ilike("nome","%"+nome+"%"));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	public List<Tabelas_Loja> getLojasByName(String nome) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Loja.class);
		criteria.add(Restrictions.ilike("nomefantasia","%"+nome+"%"));
		criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public T get(T instance) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(instance.getClass());
		return (T) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getAll(Object type) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(type.getClass());
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Tabelas_Login> getAllLogin(String username) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Login.class);
		criteria.add(Restrictions.ilike("nomeLogin","%"+username+"%"));
		criteria.addOrder(Order.asc("idLogin"));
		return criteria.list();
	}
	
	public void update(T instance) throws CpsDaoException,CpsConstraintException {
		final String message = "merging with istance: "+instance;
		log.debug(message);
		try{
			Session session = HibernateConfig.getSession(); 
			session.merge(instance);
			session.flush();
			log.debug("success!");
		}catch (ConstraintViolationException e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsConstraintException(errMsg,e);
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
	}
}