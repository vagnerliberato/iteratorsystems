package br.iteratorsystems.cps.common;

import java.util.Collection;
import java.util.List;

import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Loja;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.handler.AdministrationHandler;
import br.iteratorsystems.cps.handler.UserManagementHandler;

public class CommonOperations {

	static UserManagementHandler userHandler;
	static AdministrationHandler admHandler;

	public static boolean mailExists(String email) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<Tabelas_Usuario> list = userHandler.getAllUser();
		for (Tabelas_Usuario user : list) {
			if (user.getEmail().equalsIgnoreCase(email))
				return true;
		}
		return false;
	}

	public static boolean userExists(String username)
			throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<Tabelas_Login> list = userHandler.getAllLogin();

		for (Tabelas_Login lo : list) {
			if (lo.getNomeLogin().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cpfExists(String cpf) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<Tabelas_Usuario> list = userHandler.getAllCpf();

		for (Tabelas_Usuario user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				return true;
			}
		}
		return false;
	}

	public static boolean cnpjExists(String cnpj,Tabelas_Loja obj) throws CpsGeneralExceptions {
		admHandler = new AdministrationHandler();
		List<Tabelas_Loja> loja = admHandler.getAllCnpj();
	
		for(Tabelas_Loja l: loja){
			if( (l.getCnpj().equals(cnpj.replace(".","").replace("/","").replace("-",""))) && (obj!= null ? l.getId() != obj.getId() : true) ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean cpfExists(String cpf, String username) throws CpsGeneralExceptions {
		userHandler = new UserManagementHandler();
		Collection<Tabelas_Usuario> list = userHandler.getAllCpf();

		for (Tabelas_Usuario user : list) {
			if (user.getCpfUsuario().equals(cpf)) {
				for (Tabelas_Login login : user.getLogins()) {
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