package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Allergene;

public class AllergeneDAO implements IDAO<Allergene>{

private final static AllergeneDAO INSTANCE = new AllergeneDAO();
	
	private AllergeneDAO() {
		
	}

	public static AllergeneDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Allergene allergene) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		em.persist(allergene);		
	}
	
	public Allergene readOne(String nom) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		TypedQuery<Allergene> findAllergeneByNameQuery = em.createNamedQuery("Allergene.findByName", Allergene.class);
		findAllergeneByNameQuery.setParameter("nom", nom);
		Allergene allergene = null;
		try {
			allergene = findAllergeneByNameQuery.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e ) {
		}
		return allergene;
	}
}