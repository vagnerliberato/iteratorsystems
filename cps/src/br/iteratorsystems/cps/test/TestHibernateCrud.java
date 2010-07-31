package br.iteratorsystems.cps.test;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_Rede;

public class TestHibernateCrud extends HibernateConfig{

	public void get(){
		
		Session s = getSession();
		Criteria c = s.createCriteria(Tabelas_Rede.class);
		Tabelas_Rede l = (Tabelas_Rede) c.uniqueResult();
		System.out.println(l);
		
		Collection<Tabelas_Loja> lojas = l.getLojas();
		for(Tabelas_Loja lo : lojas){
			System.out.println(lo.toString());
		}
	}
	
	public static void main(String[] args) {
		new TestHibernateCrud().get();
	}
}
