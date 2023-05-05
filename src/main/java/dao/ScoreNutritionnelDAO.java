package dao;

import jakarta.persistence.EntityManager;
import model.Categorie;
import model.ScoreNutritionnel;

public class ScoreNutritionnelDAO implements IDAO<ScoreNutritionnel>{

private final static ScoreNutritionnelDAO INSTANCE = new ScoreNutritionnelDAO();
	
	private ScoreNutritionnelDAO() {
		
	}

	public static ScoreNutritionnelDAO getInstance() {
		return INSTANCE;
	}
		
	public void create(ScoreNutritionnel scoreNutritionnel) {
		EntityManager em = JPAUtils.getInstance().getEntityManager();
		//em.getTransaction().begin();
		em.persist(scoreNutritionnel);
		//em.getTransaction().commit();
		//em.close();
		
	}
}
