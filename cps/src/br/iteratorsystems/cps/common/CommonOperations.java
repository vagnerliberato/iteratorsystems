package br.iteratorsystems.cps.common;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Loja;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsExceptions;
import br.iteratorsystems.cps.handler.AdministrationHandler;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class CommonOperations {

	static UserManagementHandler userHandler;
	static AdministrationHandler admHandler;

	public static boolean mailExists(String email) throws CpsExceptions {
		userHandler = new UserManagementHandler();
		Collection<Usuario> list = userHandler.getAllUser();
		for (Usuario user : list) {
			if (user.getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}

	public static boolean userExists(String username)
			throws CpsExceptions {
		userHandler = new UserManagementHandler();
		Collection<Login> list = userHandler.getAllLogin();

		for (Login lo : list) {
			if (lo.getNomeLogin().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cpfExists(String cpf) throws CpsExceptions {
		userHandler = new UserManagementHandler();
		Collection<Usuario> list = userHandler.getAllCpf();

		for (Usuario user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cnpjExists(String cnpj,Loja obj) throws CpsExceptions {
		admHandler = new AdministrationHandler();
		List<Loja> loja = admHandler.getAllCnpj();
	
		for(Loja l: loja){
			if( (l.getCnpj().equals(cnpj.replace(".","").replace("/","").replace("-",""))) && (obj!= null ? l.getId() != obj.getId() : true) ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean cpfExists(String cpf, String username) throws CpsExceptions {
		userHandler = new UserManagementHandler();
		Collection<Usuario> list = userHandler.getAllCpf();

		for (Usuario user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				for (Login login : user.getLogins()) {
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