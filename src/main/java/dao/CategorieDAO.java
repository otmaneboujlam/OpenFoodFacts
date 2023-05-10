package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Categorie;

public class CategorieDAO implements IDAO<Categorie>{

private final static CategorieDAO INSTANCE = new CategorieDAO();

	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private CategorieDAO() {
		
	}

	public static CategorieDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Categorie categorie) {
		em.getTransaction().begin();
		em.persist(categorie);		
		em.getTransaction().commit();
	}
	
	public Categorie readOne(String nom) {
		TypedQuery<Categorie> findCategorieByNameQuery = em.createNamedQuery("Categorie.findByName", Categorie.class);
		findCategorieByNameQuery.setParameter("nom", nom);
		Categorie categorie = null;
		try {
			categorie = findCategorieByNameQuery.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e ) {
		}
		return categorie;
	}
}
