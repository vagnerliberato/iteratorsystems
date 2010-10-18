package br.iteratorsystems.cps.integrationTest;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Localidade;

public class TestHibernateCrudContatoLocalidade extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoLocalidade().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<Localidade> localidades = session.createCriteria(Localidade.class).list();
		
		for (Localidade localidade : localidades) {
			System.out.println(localidade.getLocalidade());
		}
	}

}
