package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.ListaProduto;

public class TestHibernateCrudContatoListaProduto extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoListaProduto().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<ListaProduto> listasDeProdutos = session.createCriteria(ListaProduto.class).list();
		
		for (ListaProduto listaproduto : listasDeProdutos) {
			System.out.println(listaproduto.getNomeLista());
		}
	}

}
