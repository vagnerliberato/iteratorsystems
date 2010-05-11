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
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsConstraintException;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.interfaces.EntityAble;
import br.iteratorsystems.cps.interfaces.IDao;

public class Dao<T extends EntityAble> implements IDao<T> {

	private static final Log log = LogFactory.getLog(Dao.class);
	private Class<T> persistentClass;
	private Session session;
	
	public Dao(Session session,Class<T> persistentClass) {
		this.session = session;
		this.persistentClass = persistentClass;
	}
	
	public Dao(){}
	
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

	public Integer getIdUsuario(USUARIO instance) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(USUARIO.class);
		criteria.add(Restrictions.eq("cpfUsuario",instance.getCpfUsuario()));
		USUARIO user = (USUARIO) criteria.uniqueResult();
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
			return ++lastId;
		}catch (Exception e) {
			String errMsg = "error!! "+message;
			log.error(errMsg,e);
			throw new CpsDaoException(errMsg,e);
		}
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
	public List<LOGIN> getAllLogin(String username) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(LOGIN.class);
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
//			session.saveOrUpdate(instance);
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
	
	//TODO Odeio isso... Preciso que os outros integrantes do grupo descubram o erro e o resolvam
	// ao usar o método acima. E LOGO!
	public void updateUsuarioHQL(final USUARIO instance) throws CpsDaoException,CpsConstraintException{
		final String message = "updating USUARIO with istance: "+instance;
		log.debug(message);
		String queryHQL = "UPDATE USUARIO u SET u.nomeUsuario=:newName,"
				+ "u.sobrenomeUsuario=:newSobrenome, u.dataNascimento=:newDataNascimento,"
				+ "u.cpfUsuario=:newCpf, u.rgUsuario=:newRgUsuario, u.orgaoEspedidorUsu=:newOrgaoEspedidorUsu,"
				+ "u.dddCel=:newDddCel, u.telCel=:newTelCel, u.dddRes=:newDddRes, u.telRes=:newTelRes,"
				+ "u.email=:newEmail WHERE u.idUsuario=:id ";//u.dataultimamodificacao=:newDataultimamodificacao"
				//+ "WHERE u.idUsuario=:id";
		try{
			Query query = HibernateConfig.getSession().createQuery(queryHQL);
			query.setString("newName",instance.getNomeUsuario());
			query.setString("newSobrenome",instance.getSobrenomeUsuario());
			query.setDate("newDataNascimento",instance.getDataNascimento());
			query.setString("newCpf",instance.getCpfUsuario());
			query.setString("newRgUsuario",instance.getRgUsuario());
			query.setString("newOrgaoEspedidorUsu",instance.getOrgaoEspedidorUsu());
			query.setString("newDddCel",instance.getDddCel());
			query.setString("newTelCel",instance.getTelCel());
			query.setString("newDddRes",instance.getDddRes());
			query.setString("newTelRes",instance.getTelRes());
			query.setString("newEmail",instance.getEmail());
			//query.setDate("newDataultimamodificacao",instance.getDataultimamodificacao());
			query.setInteger("id",instance.getIdUsuario());
			query.executeUpdate();
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