package br.iteratorsystems.cps.integrationTest;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Endereco;

public class TestHibernateCrudContatoEndereco extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoEndereco().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {
		Session session = getSession();
		List<Endereco> enderecos = session.createCriteria(Endereco.class).list();
		for (Endereco ronald : enderecos) {
		System.out.println(ronald);
		}
	}
}
