package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.ENDERECO;
import br.iteratorsystems.cps.entities.USUARIO;

public class TestHibernateCrudContatoEndereco extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoEndereco().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<ENDERECO> enderecos = session.createCriteria(ENDERECO.class).list();
		
		for (ENDERECO endereco : enderecos) {
		//	System.out.println(endereco.getComplemeto());
		}
	}

}
