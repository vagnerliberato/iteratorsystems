package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.BAIRRO;

public class TestHibernateCrudBairro extends HibernateConfig {
	
	public static void main(String[] args) {
		new TestHibernateCrudBairro().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<BAIRRO> bairros = session.createCriteria(BAIRRO.class).list();
		
		for (BAIRRO bairro : bairros) {
			System.out.println(bairro.getBairro());
		}
	}

}
