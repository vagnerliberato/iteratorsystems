package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Bairro;

public class TestHibernateCrudBairro extends HibernateConfig {
	
	public static void main(String[] args) {
		new TestHibernateCrudBairro().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<Tabelas_Bairro> bairros = session.createCriteria(Tabelas_Bairro.class).list();
		
		for (Tabelas_Bairro bairro : bairros) {
			System.out.println(bairro.getBairro());
		}
	}

}
