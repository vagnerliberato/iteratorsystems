package br.iteratorsystems.cps.controller;

import javax.el.ELResolver;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.iteratorsystems.cps.beans.LoginUserBean;

/**
 * Classe de controle de acesso das páginas.
 * @author André
 *
 */
public class AutorizationController implements PhaseListener{

	private static final long serialVersionUID = -6821434785199313719L;
	
	private static final String LOGIN_BEAN_EL = "loginUserBean";
	private static final String NAV_LOGIN = "toLoginPage";
	private static final String DIRETORIO_IMAGENS = "/view/images/";
	private static final String[] NO_FILTERS_PAGES = {"/view/default.jsf","/view/pages/myCar.jsf",
											   "/view/pages/addFilters.jsf","/view/pages/userAccess.jsf",
											   "/view/pages/lists/myLists.jsf"};
	
	/**
	 *After phase.
	 * @param event - Evento Jsf.
	 */
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		
		for(String s : NO_FILTERS_PAGES){
			if (context.getViewRoot().getViewId().equals(s)
					|| context.getViewRoot().getViewId().contains(DIRETORIO_IMAGENS)) {
				
				return;
			}
		}
		
		ELResolver el = context.getApplication().getELResolver();
		LoginUserBean loginBean = (LoginUserBean) el.getValue(context.getELContext(),null,LOGIN_BEAN_EL); 

		if(loginBean == null || !loginBean.isLogado() && !loginBean.isFirstAccess()){
			NavigationHandler navigation = context.getApplication().getNavigationHandler();
			navigation.handleNavigation(context,null,NAV_LOGIN);
			context.renderResponse();
		}
	}

	/**
	 *Before phase.
	 * @param phase - Evento Jsf.
	 */
	public void beforePhase(PhaseEvent phase) {
	}
	
	/**
	 * Retorna um Phase Id
	 * @return PhaseId Phase id.
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
