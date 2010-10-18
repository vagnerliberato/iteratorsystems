package br.iteratorsystems.cps.integrationTest;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Bairro;

public class TestHibernateCrudBairro extends HibernateConfig {
	
	public static void main(String[] args) {
		new TestHibernateCrudBairro().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<Bairro> bairros = session.createCriteria(Bairro.class).list();
		
		for (Bairro bairro : bairros) {
			System.out.println(bairro.getBairro());
		}
	}

}
