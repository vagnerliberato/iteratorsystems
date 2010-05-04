package br.iteratorsystems.cps.test;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class TestHibernateCrudUsuario extends HibernateConfig{

	
	public void get() throws CpsHandlerException{
		Session session = getSession();
		USUARIO result =  new LoginUserHandler().getUserRelated(new Integer(3));//(USUARIO) session.createCriteria(USUARIO.class).add(Restrictions.eq("idUsuario",new Integer(3))).uniqueResult();
		System.out.println(result);
		for(ENDERECO e : result.getEnderecos()){
			System.out.println(e);
		}
		for(LOGIN l : result.getLogins()){
			System.out.println(l);
		}
	}
	
	public static void main(String[] args) throws CpsHandlerException {
		new TestHibernateCrudUsuario().get();
	}
	
}
