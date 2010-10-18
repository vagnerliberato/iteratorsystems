package br.iteratorsystems.cps.integrationTest;


import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.LoginUserHandler;

public class TestHibernateCrudUsuario extends HibernateConfig{

	
	public void get() throws CpsHandlerException{
		Usuario result =  new LoginUserHandler().getUserRelated(new Integer(3));//(Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("idUsuario",new Integer(3))).uniqueResult();
		System.out.println(result);
		for(Endereco e : result.getEnderecos()){
			System.out.println(e);
		}
		for(Login l : result.getLogins()){
			System.out.println(l);
		}
	}
	
	public static void main(String[] args) throws CpsHandlerException {
		new TestHibernateCrudUsuario().get();
	}
	
}
