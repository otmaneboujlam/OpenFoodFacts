package dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import model.Produit;

/**
 * @author Otmane
 *
 */
public class ProduitDAO implements IDAO<Produit> {
	
	private final static ProduitDAO INSTANCE = new ProduitDAO();
	
	private ProduitDAO() {
		
	}

	public static ProduitDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Produit produit) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		//em.getTransaction().begin();
		em.persist(produit);
		em.getTransaction().commit();
		em.close();
		
	}
}
