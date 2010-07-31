package br.iteratorsystems.cps.handler;

import java.util.Collection;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.entities.Tabelas_Endereco;
import br.iteratorsystems.cps.entities.Tabelas_EnderecoId;
import br.iteratorsystems.cps.entities.Tabelas_Login;
import br.iteratorsystems.cps.entities.Tabelas_Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.EntityAble;
import br.iteratorsystems.cps.interfaces.IDao;

public class UserManagementHandler {

	private static final Log log = LogFactory.getLog(UserManagementHandler.class);
	private static final char TIPO_USUARIO_DEFAULT = 'P';
	
	private IDao<Tabelas_Usuario> idaoUsuario;
	private IDao<Tabelas_Login> idaoLogin;
	private IDao<Tabelas_Endereco> idaoEndereco;
	
	public void save(final Tabelas_Usuario usuario,final Tabelas_Login login,final Tabelas_Endereco endereco) throws CpsHandlerException{
		final String message = "saving object with instance: "+usuario+" "+login+" "+endereco;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			
			idaoUsuario = new Dao<Tabelas_Usuario>();
			usuario.setDataultimamodificacao(new Date());
			Integer id = (Integer) idaoUsuario.save(usuario);
			
			idaoLogin = new Dao<Tabelas_Login>();
			login.setTipoUsuario(TIPO_USUARIO_DEFAULT);
			login.setIdLogin(id);
			idaoLogin.save(login);
			
			idaoEndereco = new Dao<Tabelas_Endereco>();
			endereco.setDataultimamodificacao(new Date());
			
			//pegando o último id da tabela endereco
			Tabelas_EnderecoId enderecoId = new Tabelas_EnderecoId(this.getLastId(endereco),id);
			endereco.setId(enderecoId);
			idaoEndereco.save(endereco);
			
			transaction.commit();
			log.debug("success!");
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			transaction.rollback();
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public Integer getLastId(EntityAble entity) throws CpsHandlerException{
		final String message = "getting last id of entity: "+entity;
		log.debug(message);
		Integer id = null;
		try{
			idaoUsuario = new Dao<Tabelas_Usuario>();
			id = idaoUsuario.getLastIdFrom(entity);
			return id;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public void delete(Object instance) throws CpsHandlerException {
	}

	public Collection<Tabelas_Usuario> getAllUser() throws CpsHandlerException {
		final String message = "getting All Tabelas_Usuario object instances";
		log.debug(message);
		Collection<Tabelas_Usuario> dados= null;
		try{
			idaoUsuario = new Dao<Tabelas_Usuario>();
			dados = idaoUsuario.getAll(new Tabelas_Usuario());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}

	public Collection<Tabelas_Login> getAllLogin() throws CpsHandlerException {
		final String message = "getting All Tabelas_Login object instances";
		log.debug(message);
		Collection<Tabelas_Login> dados= null;
		try{
			idaoLogin = new Dao<Tabelas_Login>();
			dados = idaoLogin.getAll(new Tabelas_Login());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public Collection<Tabelas_Usuario> getAllCpf() throws CpsHandlerException {
		final String message = "getting All CPF object instances";
		log.debug(message);
		Collection<Tabelas_Usuario> dados= null;
		try{
			idaoUsuario = new Dao<Tabelas_Usuario>();
			dados = idaoUsuario.getAll(new Tabelas_Usuario());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public void update(final Tabelas_Usuario usuario,final Tabelas_Login login,final Tabelas_Endereco endereco) throws CpsHandlerException {
		final String message = "merging object with instance: "+usuario+" "+login+" "+endereco;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			idaoUsuario = new Dao<Tabelas_Usuario>();
			usuario.setDataultimamodificacao(new Date());
			idaoUsuario.update(usuario);
			transaction.commit();
			log.debug("success!");
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			transaction.rollback();
			throw new CpsHandlerException(errMsg,e);
		}
	}
}
