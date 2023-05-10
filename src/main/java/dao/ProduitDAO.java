package dao;


import jakarta.persistence.EntityManager;
import model.Produit;

/**
 * @author Otmane
 *
 */
public class ProduitDAO implements IDAO<Produit> {
	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private final static ProduitDAO INSTANCE = new ProduitDAO();
	
	private ProduitDAO() {
		
	}

	public static ProduitDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Produit produit) {
		em.getTransaction().begin();
		em.persist(produit);	
		em.getTransaction().commit();
	}
}
