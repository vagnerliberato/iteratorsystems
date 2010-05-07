package br.iteratorsystems.cps.config;

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
		//configuration.setProperty("show_sql", "true");
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
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.BAIRRO.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.BAIRROID.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.CEP.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.CONTATOLOJA.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.CONTATOLOJAID.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.ENDERECO.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LISTAPRODUTO.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LISTAPRODUTOITEM.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LOCALIDADE.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LOGIN.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LOJA.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.LOJAID.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.PRECO.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.PRECOID.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.PRODUTO.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.PRODUTOGERAL.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.PRODUTOID.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.REDE.class);
		configuration.addAnnotatedClass(br.iteratorsystems.cps.entities.USUARIO.class);
	}

	public static void closeSession() {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
}
