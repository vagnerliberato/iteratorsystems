package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.CONTATOLOJA;

public class TestHibernateCrudContatoLoja extends HibernateConfig{

	public static void main(String[] args) {
		new TestHibernateCrudContatoLoja().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<CONTATOLOJA> contatosLoja = session.createCriteria(CONTATOLOJA.class).list();
		
		for (CONTATOLOJA contatoLoja : contatosLoja) {
			System.out.println("Loja: " + contatoLoja.getLoja().getRazaosocial() + "Tel.(com.) " + contatoLoja.getTelCom());
		}
	}
}
