package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Ingredient;

public class IngredientDAO implements IDAO<Ingredient>{

private final static IngredientDAO INSTANCE = new IngredientDAO();
	
	EntityManager em = JPAUtils.getInstance().getEntityManager();
	private IngredientDAO() {
		
	}

	public static IngredientDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Ingredient ingredient) {
		em.getTransaction().begin();
		em.persist(ingredient);		
		em.getTransaction().commit();
	}
	
	public Ingredient readOneByName(String nom) {
		TypedQuery<Ingredient> findIngredientByNameQuery = em.createNamedQuery("Ingredient.findByName", Ingredient.class);
		findIngredientByNameQuery.setParameter("nom", nom);
		Ingredient ingredient = null;
		try {
			ingredient = findIngredientByNameQuery.getSingleResult();
		} catch (NoResultException | NonUniqueResultException e ) {
		}
		return ingredient;
	}
}