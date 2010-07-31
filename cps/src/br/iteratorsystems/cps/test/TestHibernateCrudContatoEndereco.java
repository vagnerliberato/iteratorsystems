package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;

public class TestHibernateCrudContatoEndereco extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoEndereco().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {
		Session session = getSession();
		List<Tabelas_Endereco> enderecos = session.createCriteria(Tabelas_Endereco.class).list();
		for (Tabelas_Endereco ronald : enderecos) {
		System.out.println(ronald);
		}
	}
}
