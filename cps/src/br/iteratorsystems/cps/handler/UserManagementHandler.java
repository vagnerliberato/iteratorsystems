package br.iteratorsystems.cps.handler;

import java.util.Collection;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Transaction;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.dao.Dao;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.EnderecoId;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.exceptions.CpsHandlerException;
import br.iteratorsystems.cps.interfaces.EntityAble;
import br.iteratorsystems.cps.interfaces.IDao;

public class UserManagementHandler {

	private static final Log log = LogFactory.getLog(UserManagementHandler.class);
	private static final char TIPO_USUARIO_DEFAULT = 'P';
	
	private IDao<Usuario> idaoUsuario;
	private IDao<Login> idaoLogin;
	private IDao<Endereco> idaoEndereco;
	
	public void save(final Usuario usuario,final Login login,final Endereco endereco) throws CpsHandlerException{
		final String message = "saving object with instance: "+usuario+" "+login+" "+endereco;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			
			idaoUsuario = new Dao<Usuario>();
			usuario.setDataultimamodificacao(new Date());
			Integer id = (Integer) idaoUsuario.save(usuario);
			
			idaoLogin = new Dao<Login>();
			login.setTipoUsuario(TIPO_USUARIO_DEFAULT);
			login.setIdLogin(id);
			idaoLogin.save(login);
			
			idaoEndereco = new Dao<Endereco>();
			endereco.setDataultimamodificacao(new Date());
			
			//pegando o último id da tabela endereco
			EnderecoId enderecoId = new EnderecoId(this.getLastId(endereco),id);
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
			idaoUsuario = new Dao<Usuario>();
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

	public Collection<Usuario> getAllUser() throws CpsHandlerException {
		final String message = "getting All Usuario object instances";
		log.debug(message);
		Collection<Usuario> dados= null;
		try{
			idaoUsuario = new Dao<Usuario>();
			dados = idaoUsuario.getAll(new Usuario());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}

	public Collection<Login> getAllLogin() throws CpsHandlerException {
		final String message = "getting All Login object instances";
		log.debug(message);
		Collection<Login> dados= null;
		try{
			idaoLogin = new Dao<Login>();
			dados = idaoLogin.getAll(new Login());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public Collection<Usuario> getAllCpf() throws CpsHandlerException {
		final String message = "getting All CPF object instances";
		log.debug(message);
		Collection<Usuario> dados= null;
		try{
			idaoUsuario = new Dao<Usuario>();
			dados = idaoUsuario.getAll(new Usuario());
			return dados;
		}catch (CpsDaoException e) {
			final String errMsg = "error! "+message;
			log.error(errMsg,e);
			throw new CpsHandlerException(errMsg,e);
		}
	}
	
	public void update(final Usuario usuario,final Login login,final Endereco endereco) throws CpsHandlerException {
		final String message = "merging object with instance: "+usuario+" "+login+" "+endereco;
		log.debug(message);
		Transaction transaction = null;
		try{
			transaction = HibernateConfig.getSession().beginTransaction();
			idaoUsuario = new Dao<Usuario>();
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
