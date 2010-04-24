package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.LISTAPRODUTO;

public class TestHibernateCrudContatoListaProduto extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoListaProduto().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<LISTAPRODUTO> listasDeProdutos = session.createCriteria(LISTAPRODUTO.class).list();
		
		for (LISTAPRODUTO listaproduto : listasDeProdutos) {
			System.out.println(listaproduto.getNomeLista());
		}
	}

}
