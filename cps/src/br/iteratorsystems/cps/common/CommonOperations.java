package br.iteratorsystems.cps.common;

import java.util.Collection;

import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class CommonOperations {

	static UserManagementHandler userHandler;
	
	public static boolean mailExists(String email) throws CpsGeneralExceptions{
		userHandler  = new UserManagementHandler();
		Collection<USUARIO> list =  userHandler.getAllUser();
		
		for(USUARIO user: list){
			if(user.getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}
	
	public static boolean userExists(String username) throws CpsGeneralExceptions {
		final String nomeusuario = username;
		
		userHandler = new UserManagementHandler();
		Collection<LOGIN> list = userHandler.getAllLogin();
		
		for(LOGIN lo : list){
			if(lo.getNomeLogin().equalsIgnoreCase(nomeusuario)) {
				return true;
			}
		}
		return false;
	}
	
}
