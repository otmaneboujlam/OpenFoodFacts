package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Marque;

/**
 * @author Otmane
 *
 */
public class MarqueDAO implements IDAO<Marque> {
	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private final static MarqueDAO INSTANCE = new MarqueDAO();
	private MarqueDAO() {
		
	}
	public static MarqueDAO getInstance() {
		return INSTANCE;
	}
	
	public void create(Marque marque) {
		em.getTransaction().begin();
		em.persist(marque);
		em.getTransaction().commit();
	}
	
	public Marque readOne(String nom) {
		TypedQuery<Marque> findMarqueByNameQuery = em.createNamedQuery("Marque.findByName", Marque.class);
		findMarqueByNameQuery.setParameter("nom", nom);
		Marque marque = null;
		try {
			marque = findMarqueByNameQuery.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e ) {
			//C'est normal si la marque n'existe pas encore
		}
		return marque;
	}
}