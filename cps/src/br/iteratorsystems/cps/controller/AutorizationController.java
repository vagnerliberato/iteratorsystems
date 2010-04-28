package br.iteratorsystems.cps.controller;

import javax.el.ELResolver;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.iteratorsystems.cps.beans.LoginUserBean;

public class AutorizationController implements PhaseListener{

	private static final long serialVersionUID = -6821434785199313719L;
	private static final String LOGINBEANEL = "loginUserBean";
	private static final String NAVLOGIN = "toLoginPage";
	
	private static final String[] pagesOk = {"/view/default.jsf","/view/pages/myCar.jsf",
											   "/view/pages/addFilters.jsf","/view/pages/userAccess.jsf"};
	
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		
		for(String s : pagesOk){
			if (context.getViewRoot().getViewId().equals(s))
				return;
		}
		
		ELResolver el = context.getApplication().getELResolver();
		LoginUserBean loginBean = (LoginUserBean) el.getValue(context.getELContext(),null,LOGINBEANEL); 
		
		if(loginBean == null || !loginBean.isLogado()){
			NavigationHandler navigation = context.getApplication().getNavigationHandler();
			navigation.handleNavigation(context,null,NAVLOGIN);
			context.renderResponse();
		}
	}

	public void beforePhase(PhaseEvent arg0) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
