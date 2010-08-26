package br.iteratorsystems.cps.interfaces;

import java.io.Serializable;
import java.util.List;

public interface IDaoGeneric <T, ID extends Serializable>{
	
	 public T obter(ID id);  
	  
	 public List<T> listar();  
	  
	 public List<T> listarPorParametros(T exemplo, String... propriedadesDeExclusao);  
	  
	 public void salvar(T entidade);  
	  
	 public void excluir(T entidade);  
	  
	 public void atualizar(T entidade);  
}  