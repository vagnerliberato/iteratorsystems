package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Localidade;

public class TestHibernateCrudContatoLocalidade extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoLocalidade().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<Tabelas_Localidade> localidades = session.createCriteria(Tabelas_Localidade.class).list();
		
		for (Tabelas_Localidade localidade : localidades) {
			System.out.println(localidade.getLocalidade());
		}
	}

}
