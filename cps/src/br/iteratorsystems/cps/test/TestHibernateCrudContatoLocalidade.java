package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.LOCALIDADE;

public class TestHibernateCrudContatoLocalidade extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoLocalidade().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<LOCALIDADE> localidades = session.createCriteria(LOCALIDADE.class).list();
		
		for (LOCALIDADE localidade : localidades) {
			System.out.println(localidade.getLocalidade());
		}
	}

}
