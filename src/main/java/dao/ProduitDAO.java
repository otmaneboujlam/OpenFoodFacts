package dao;


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
		em.persist(produit);		
	}
}
