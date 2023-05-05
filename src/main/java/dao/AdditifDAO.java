package dao;

import jakarta.persistence.EntityManager;
import model.Additif;
import model.Categorie;

public class AdditifDAO implements IDAO<Additif> {

private final static AdditifDAO INSTANCE = new AdditifDAO();
	
	private AdditifDAO() {
		
	}

	public static AdditifDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Additif additif) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		//em.getTransaction().begin();
		em.persist(additif);
		//em.getTransaction().commit();
		//em.close();
		
	}
}
