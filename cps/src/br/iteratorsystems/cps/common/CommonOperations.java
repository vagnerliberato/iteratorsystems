package br.iteratorsystems.cps.common;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.entities.LOJA;
import br.iteratorsystems.cps.entities.USUARIO;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.AdministrationHandler;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class CommonOperations {

	static UserManagementHandler userHandler;
	static AdministrationHandler admHandler;

	public static boolean mailExists(String email) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<USUARIO> list = userHandler.getAllUser();
		for (USUARIO user : list) {
			if (user.getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}

	public static boolean userExists(String username)
			throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<LOGIN> list = userHandler.getAllLogin();

		for (LOGIN lo : list) {
			if (lo.getNomeLogin().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cpfExists(String cpf) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<USUARIO> list = userHandler.getAllCpf();

		for (USUARIO user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cnpjExists(String cnpj) throws CpsGeneralExceptions {
		admHandler = new AdministrationHandler();
		List<LOJA> loja = admHandler.getAllCnpj();
	
		for(LOJA l: loja){
			if(l.getCnpj().equals(cnpj)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean cpfExists(String cpf, String username) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<USUARIO> list = userHandler.getAllCpf();

		for (USUARIO user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				for (LOGIN login : user.getLogins()) {
					if (login.getNomeLogin().equals(username)) {
						return false;
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}
}