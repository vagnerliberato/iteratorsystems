package br.iteratorsystems.cps.dao;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.iteratorsystems.cps.config.HibernateConfig;
import br.iteratorsystems.cps.entities.Endereco;
import br.iteratorsystems.cps.entities.ListaProduto;
import br.iteratorsystems.cps.entities.ListaProdutoItem;
import br.iteratorsystems.cps.entities.Login;
import br.iteratorsystems.cps.entities.Usuario;
import br.iteratorsystems.cps.exceptions.CpsDaoException;
import br.iteratorsystems.cps.interfaces.EntityAble;

/**
 * Classe DAO responsável por operações do login
 * @author André
 *
 */
public class DaoLogin extends Dao<EntityAble> {
	
	/**
	 * log
	 */
	private static final Log log = LogFactory.getLog(DaoLogin.class);
	
	//TODO melhorar este metodo similar ao da classe pai!
	public Login get(final String username,final String password) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Login.class);
		criteria.add(Restrictions.eq("nomeLogin", username));
		criteria.add(Restrictions.eq("senha",password));
		return (Login) criteria.uniqueResult();
	}
	
	public Login get(final Integer id) throws CpsDaoException{
		Criteria criteria = HibernateConfig.getSession().createCriteria(Login.class);
		criteria.add(Restrictions.eq("idLogin", id));
		return (Login) criteria.uniqueResult();
	}
	
	/**
	 * Obtem um usuário com base em seu Id.
	 * @param idLogin - Id do usuário
	 * @return Usuário populado.
	 * @throws CpsDaoException Se ocorrer algum erro nas camadas abaixo.
	 */
	public Usuario getUsuarioRelated(final Integer idLogin) throws CpsDaoException{
		log.debug("getting user with id "+idLogin);
		Usuario usuario = new Usuario();
		Session session = HibernateConfig.getSession();
		try{
			usuario = (Usuario)
				session.get(usuario.getClass(),idLogin);
			
			if(usuario != null && (usuario.getListaProdutos() == null 
					|| usuario.getListaProdutos().isEmpty())) {
				
				usuario.setListaProdutos(
						new HashSet<ListaProduto>(
								obterListaProdutoUsuario(session, idLogin)));
			}
		}catch (HibernateException e) {
			throw new CpsDaoException(e);
		}
		return usuario;
	}
	
	/**
	 * Obtem uma lista de produtos com seus items com base no usuário.
	 * @param session - Sessão
	 * @param idUsuario - id do usuário
	 * @return Lista de produtos.
	 * @throws CpsDaoException Se ocorrer algum erro nas camadas abaixo. 
	 */
	@SuppressWarnings("unchecked")
	private List<ListaProduto> obterListaProdutoUsuario(final Session session,final Integer idUsuario) throws CpsDaoException {
		log.debug("getting lista_produto with id "+idUsuario);
		List<ListaProduto> listaProduto = null;
		try{
			Criteria criteria = session.createCriteria(ListaProduto.class);
			criteria.add(Restrictions.eq("usuario.idUsuario",idUsuario));
			listaProduto = (List<ListaProduto>) criteria.list();
			
			for(ListaProduto lista : listaProduto) {
				lista.setListaProdutoItems(
						new HashSet<ListaProdutoItem>(
								obterListaProdutoItem(session,lista.getId())));
			}
		}catch (HibernateException e) {
			throw new CpsDaoException(e);
		}
		return listaProduto;
	}
	
	/**
	 * Obtém uma lista de items da lista de produto.
	 * @param session - Sessão
	 * @param idLista - id da lista
	 * @return Lista de items de produto
	 * @throws CpsDaoException Se ocorrer algum erro nas camadas abaixo.
	 */
	@SuppressWarnings("unchecked")
	private List<ListaProdutoItem> obterListaProdutoItem(final Session session, final Integer idLista) throws CpsDaoException {
		log.debug("getting lista_produto_item with idLista "+idLista);
		List<ListaProdutoItem> listaItem = null;
		try{
			Criteria criteria = session.createCriteria(ListaProdutoItem.class);
			criteria.add(Restrictions.eq("listaProduto.id",idLista));
			listaItem = (List<ListaProdutoItem>) criteria.list();
		}catch (HibernateException e) {
			throw new CpsDaoException(e);
		}
		return listaItem;
	}
	
	//TODO melhorar este metodo similar ao da classe pai!
	public Endereco getEnderecoRelated(final Integer idUsuario) throws CpsDaoException {
		Criteria criteria = HibernateConfig.getSession().createCriteria(Endereco.class);
		criteria.add(Restrictions.eq("usuario.idUsuario",idUsuario));
		return (Endereco) criteria.uniqueResult();
	}

	public boolean checkPassword(String pass) throws CpsDaoException{
		Login result = null;
		Criteria criteria = HibernateConfig.getSession().createCriteria(Login.class);
		criteria.add(Restrictions.eq("senha",pass));
		result = (Login) criteria.uniqueResult();
		return result == null ? false : true;
	}
}
