package dao;

import jakarta.persistence.EntityManager;
import model.Marque;

/**
 * @author Otmane
 *
 */
public class MarqueDAO implements IDAO<Marque> {

	private final static MarqueDAO INSTANCE = new MarqueDAO();
	private MarqueDAO() {
		
	}
	public static MarqueDAO getInstance() {
		return INSTANCE;
	}
	
	public void create(Marque marque) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		//em.getTransaction().begin();
		em.persist(marque);
		//em.getTransaction().commit();
		//em.close();
	}
}