package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Additif;

public class AdditifDAO implements IDAO<Additif> {

private final static AdditifDAO INSTANCE = new AdditifDAO();
	
	private AdditifDAO() {
		
	}

	public static AdditifDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Additif additif) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		em.persist(additif);		
	}
	
	public Additif readOne(String nom) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		TypedQuery<Additif> findAdditifByNameQuery = em.createNamedQuery("Additif.findByName", Additif.class);
		findAdditifByNameQuery.setParameter("nom", nom);
		Additif additif = null;
		try {
			additif = findAdditifByNameQuery.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e ) {
		}
		return additif;
	}
}
