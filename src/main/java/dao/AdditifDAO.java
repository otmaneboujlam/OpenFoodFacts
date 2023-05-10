package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Additif;

public class AdditifDAO implements IDAO<Additif> {

private final static AdditifDAO INSTANCE = new AdditifDAO();

	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private AdditifDAO() {
		
	}

	public static AdditifDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Additif additif) {
		em.getTransaction().begin();
		em.persist(additif);
		em.getTransaction().commit();
	}
	
	public Additif readOne(String nom) {
		
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
