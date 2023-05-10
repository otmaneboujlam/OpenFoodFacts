package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Allergene;

public class AllergeneDAO implements IDAO<Allergene>{

private final static AllergeneDAO INSTANCE = new AllergeneDAO();
	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private AllergeneDAO() {
		
	}

	public static AllergeneDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Allergene allergene) {
		em.getTransaction().begin();
		em.persist(allergene);		
		em.getTransaction().commit();
	}
	
	public Allergene readOneByName(String nom) {
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