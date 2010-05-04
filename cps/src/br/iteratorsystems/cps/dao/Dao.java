package br.iteratorsystems.cps.dao;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.handler.UserManagementHandler;
import br.iteratorsystems.cps.interfaces.EntityAble;
import br.iteratorsystems.cps.interfaces.IDao;

public class Dao<T extends EntityAble> extends HibernateConfig implements IDao<T> {

	private static final Log log = LogFactory.getLog(UserManagementHandler.class);
	
	public Integer save(T instance) throws CpsDaoException, CpsConstraintException {
		final String message = "saving with istance: "+instance;
		log.debug(message);
		Integer id = null;
		try{
			Session session = getSession();
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
	}

	public Integer getIdUsuario(USUARIO instance) throws CpsDaoException {
		Criteria criteria = getSession().createCriteria(USUARIO.class);
		criteria.add(Restrictions.eq("cpfUsuario",instance.getCpfUsuario()));
		USUARIO user = (USUARIO) criteria.uniqueResult();
		Integer id = user.getIdUsuario();
		return id;
	}
	
	public T get(T instance) throws CpsDaoException {
		Criteria criteria = getSession().createCriteria(instance.getClass());
		return (T) criteria.uniqueResult();
	}

	public Collection<T> getAll(Object type) throws CpsDaoException {
		Criteria criteria = getSession().createCriteria(type.getClass());
		return criteria.list();
	}

	public void update(T instance) throws CpsDaoException,CpsConstraintException {
		final String message = "merging with istance: "+instance;
		log.debug(message);
		try{
			Session session = getSession();
			session.update(instance);
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
