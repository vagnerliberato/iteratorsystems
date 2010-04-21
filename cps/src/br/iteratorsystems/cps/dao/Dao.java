package br.iteratorsystems.cps.dao;

import java.util.Collection;

import br.iteratorsystems.cps.interfaces.EntityAble;

public class Dao<T extends EntityAble> {

	T entity;

	public Dao(){super();}
	
	public void save() {

	}

	public T get() {
		return null;
	}

	public Collection<T> getAll() {
		return null;
	}
	
	public void update(){
		
	}

	public void delete(){
		
	}
}
