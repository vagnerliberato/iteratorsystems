package br.iteratorsystems.cps.test;

import java.util.List;

import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_ListaProduto;

public class TestHibernateCrudContatoListaProduto extends HibernateConfig {

	public static void main(String[] args) {
		new TestHibernateCrudContatoListaProduto().get();
	}

	@SuppressWarnings("unchecked")
	private void get() {

		Session session = getSession();
		List<Tabelas_ListaProduto> listasDeProdutos = session.createCriteria(Tabelas_ListaProduto.class).list();
		
		for (Tabelas_ListaProduto listaproduto : listasDeProdutos) {
			System.out.println(listaproduto.getNomeLista());
		}
	}

}
