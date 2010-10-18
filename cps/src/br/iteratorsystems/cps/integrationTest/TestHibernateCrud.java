package br.iteratorsystems.cps.integrationTest;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.Rede;

public class TestHibernateCrud extends HibernateConfig{

	public void get(){
		
		Session s = getSession();
		Criteria c = s.createCriteria(Rede.class);
		Rede l = (Rede) c.uniqueResult();
		System.out.println(l);
		
		Collection<Loja> lojas = l.getLojas();
		for(Loja lo : lojas){
			System.out.println(lo.toString());
		}
	}
	
	public static void main(String[] args) {
		new TestHibernateCrud().get();
	}
}
