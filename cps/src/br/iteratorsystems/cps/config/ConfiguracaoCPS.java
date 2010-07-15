package br.iteratorsystems.cps.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.iteratorsystems.cps.entities.PARAMETRIZACAO_CPS;

import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * Classe que carrega as configurações do cps.
 * @author André
 * 
 */
public class ConfiguracaoCPS implements ServletContextListener{

	/**
	 * Sessão do hibernate
	 */
	private static final Session SESSION = HibernateConfig.getSession();
	
	/**
	 * Variavel estática para o nome do objeto de sessão.
	 */
	private static final String PARAMETRIZACAO = "parametrizacao";
	
	/**
	 * Obtem a parametrização do sistema.
	 * @return Classe de parametrização com os dados do banco.
	 */
	private static PARAMETRIZACAO_CPS obterParametrizacaoCps() {
		PARAMETRIZACAO_CPS parametrizacaoCPS = null;
		try {
			Criteria criteria = 
				SESSION.createCriteria(PARAMETRIZACAO_CPS.class);
			parametrizacaoCPS = 
				(PARAMETRIZACAO_CPS) criteria.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parametrizacaoCPS;
	}

	/**
	 * Destroi o contexto da aplicação.
	 * @param event - Evento do servlet
	 */
	public void contextDestroyed(ServletContextEvent event) {
		try{
			event.getServletContext().removeAttribute(PARAMETRIZACAO);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicia o contexto da aplicação e insere um objeto de configuração do
	 * sistema no contexto.
	 * @param event - Evento do servlet
	 */
	public void contextInitialized(ServletContextEvent event) {
		event.getServletContext().setAttribute(
					PARAMETRIZACAO,obterParametrizacaoCps());
	}
}
