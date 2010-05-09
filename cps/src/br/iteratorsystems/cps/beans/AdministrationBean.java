package br.iteratorsystems.cps.beans;

import java.util.List;

import org.richfaces.component.html.HtmlDataTable;

import br.iteratorsystems.cps.entities.LOGIN;
import br.iteratorsystems.cps.exceptions.CpsGeneralExceptions;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.handler.AdministrationHandler;

public class AdministrationBean {

	private LOGIN loginEntity = new LOGIN();
	private List<LOGIN> allLogins;
	private AdministrationHandler administrationHandler;
	private HtmlDataTable richDataTable;
	
	public AdministrationBean() {}

	public void findUsers() throws CpsGeneralExceptions{
		try{
			administrationHandler = new AdministrationHandler();
			allLogins = administrationHandler.getAllLogins(this.getLoginEntity().getNomeLogin());
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void deleteLogin() throws CpsGeneralExceptions {
		try{
			LOGIN newLogin = (LOGIN) this.getRichDataTable().getRowData();
			administrationHandler = new AdministrationHandler();
			this.allLogins.remove(newLogin);
			administrationHandler.deleteLogin(newLogin);
		}catch (CpsHandlerException e) {
			throw new CpsGeneralExceptions(e);
		}
	}
	
	public void setLoginEntity(LOGIN loginEntity) {
		this.loginEntity = loginEntity;
	}

	public LOGIN getLoginEntity() {
		return loginEntity;
	}

	public void setAllLogins(List<LOGIN> allLogins) {
		this.allLogins = allLogins;
	}

	public List<LOGIN> getAllLogins() {
		return allLogins;
	}

	public void setRichDataTable(HtmlDataTable richDataTable) {
		this.richDataTable = richDataTable;
	}

	public HtmlDataTable getRichDataTable() {
		return richDataTable;
	}
}
