package dao;

import jakarta.persistence.EntityManager;
import model.Allergene;
import model.Categorie;

public class AllergeneDAO implements IDAO<Allergene>{

private final static AllergeneDAO INSTANCE = new AllergeneDAO();
	
	private AllergeneDAO() {
		
	}

	public static AllergeneDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Allergene allergene) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		//em.getTransaction().begin();
		em.persist(allergene);
		//em.getTransaction().commit();
		//em.close();
		
	}
}