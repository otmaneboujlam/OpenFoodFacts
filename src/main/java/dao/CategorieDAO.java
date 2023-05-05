package dao;

import jakarta.persistence.EntityManager;
import model.Categorie;
import model.Produit;

public class CategorieDAO implements IDAO<Categorie>{

private final static CategorieDAO INSTANCE = new CategorieDAO();
	
	private CategorieDAO() {
		
	}

	public static CategorieDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Categorie categorie) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		em.getTransaction().begin();
		em.persist(categorie);
		em.getTransaction().commit();
		em.close();
		
	}
}
