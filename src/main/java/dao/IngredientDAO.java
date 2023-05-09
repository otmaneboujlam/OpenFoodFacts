package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;
import model.Ingredient;

public class IngredientDAO implements IDAO<Ingredient>{

private final static IngredientDAO INSTANCE = new IngredientDAO();
	
	private IngredientDAO() {
		
	}

	public static IngredientDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(Ingredient ingredient) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		em.persist(ingredient);		
	}
	
	public Ingredient readOne(String nom) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
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