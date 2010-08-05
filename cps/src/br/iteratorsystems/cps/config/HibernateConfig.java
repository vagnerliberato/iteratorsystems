package br.iteratorsystems.cps.config;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public abstract class HibernateConfig {

	private static SessionFactory sessionFactory;
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	public static Session getSession() {
		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
			if(sessionFactory == null){
				rebuildSession();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}

	private static void rebuildSession() {
		AnnotationConfiguration configuration = new AnnotationConfiguration();
		// configuração padrão do hibernate
		configuration.setProperty("hibernate.connection.url","jdbc:postgresql://localhost:5432/cps");
		configuration.setProperty("hibernate.connection.driver_class","org.postgresql.Driver");
		configuration.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
		configuration.setProperty("hibernate.connection.username", "cps");
		configuration.setProperty("hibernate.connection.password", "cps2010");
		configuration.setProperty("hibernate.show_sql", "false");
		configuration.setProperty("hibernate.format_sql", "false");
		configuration.setProperty("hibernate.c3p0.min_size", "2");
		configuration.setProperty("hibernate.c3p0.max_size", "10");
		configuration.setProperty("hibernate.c3p0.timeout", "200");
		configuration.setProperty("hibernate.c3p0.idle_test_period", "100");
		// método para adicionar as entidades
		addMappedEntities(configuration);
		// da um build na sessionfactory
		sessionFactory = configuration.buildSessionFactory();
	}

	private static void addMappedEntities(AnnotationConfiguration configuration) {
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Bairro.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_BairroId.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Cep.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Endereco.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_ListaProduto.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_ListaProdutoItem.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Localidade.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Login.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Loja.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_LojaId.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Preco.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_PrecoId.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Produto.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_ProdutoGeral.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_ProdutoId.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Rede.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Usuario.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.Tabelas_Parametrizacao.class);
	}

	public static void closeSession() {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public static Connection getConnection() {
		return getSession().connection();
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
