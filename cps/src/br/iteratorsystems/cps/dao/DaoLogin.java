package br.iteratorsystems.cps.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.interfaces.EntityAble;

public class DaoLogin extends Dao<EntityAble> {
	
	//TODO melhorar este metodo similar ao da classe pai!
	public Tabelas_Login get(final String username,final String password) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Login.class);
		criteria.add(Restrictions.eq("nomeLogin", username));
		criteria.add(Restrictions.eq("senha",password));
		return (Tabelas_Login) criteria.uniqueResult();
	}
	
	public Tabelas_Login get(final Integer id) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Login.class);
		criteria.add(Restrictions.eq("idLogin", id));
		return (Tabelas_Login) criteria.uniqueResult();
	}
	
	//TODO melhorar este metodo similar ao da classe pai!
	public Tabelas_Usuario getUsuarioRelated(final Integer idLogin) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Usuario.class);
		criteria.add(Restrictions.eq("idUsuario",idLogin));
		return (Tabelas_Usuario) criteria.uniqueResult();
	}
	
	//TODO melhorar este metodo similar ao da classe pai!
	public Tabelas_Endereco getEnderecoRelated(final Integer idUsuario) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Endereco.class);
		criteria.add(Restrictions.eq("usuario.idUsuario",idUsuario));
		return (Tabelas_Endereco) criteria.uniqueResult();
	}

	public boolean checkPassword(String pass) throws CpsDaoException{
		Tabelas_Login result = null;
		Criteria criteria = HibernateConfig.getSession().createCriteria(Tabelas_Login.class);
		criteria.add(Restrictions.eq("senha",pass));
		result = (Tabelas_Login) criteria.uniqueResult();
		return result == null ? false : true;
	}
}
