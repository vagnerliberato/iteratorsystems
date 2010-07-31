package br.iteratorsystems.cps.test;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class TestHibernateCrudUsuario extends HibernateConfig{

	
	public void get() throws CpsHandlerException{
		Session session = getSession();
		Tabelas_Usuario result =  new LoginUserHandler().getUserRelated(new Integer(3));//(Tabelas_Usuario) session.createCriteria(Tabelas_Usuario.class).add(Restrictions.eq("idUsuario",new Integer(3))).uniqueResult();
		System.out.println(result);
		for(Tabelas_Endereco e : result.getEnderecos()){
			System.out.println(e);
		}
		for(Tabelas_Login l : result.getLogins()){
			System.out.println(l);
		}
	}
	
	public static void main(String[] args) throws CpsHandlerException {
		new TestHibernateCrudUsuario().get();
	}
	
}
