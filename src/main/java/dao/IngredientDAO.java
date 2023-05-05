package dao;

import jakarta.persistence.EntityManager;
import model.Categorie;
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
		//em.getTransaction().begin();
		em.persist(ingredient);
		//em.getTransaction().commit();
		//em.close();
		
	}
}