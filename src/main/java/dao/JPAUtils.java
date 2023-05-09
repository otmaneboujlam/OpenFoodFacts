/**
 * 
 */
package dao;

/**
 * @author Otmane
 *
 */
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Class
 * 
 * @author Otmane
 *
 */
public class JPAUtils {
	private final static JPAUtils INSTANCE = new JPAUtils();

	private JPAUtils() {
	}

	public static JPAUtils getInstance() {
		return INSTANCE;
	}

	private final static EntityManagerFactory EMF = Persistence.createEntityManagerFactory("FormationJPAPU");
	private final static EntityManager EM = EMF.createEntityManager();

	public EntityManager getEntityManager() {
		return EM;
	}

	public void close() {
		if (EM != null) {
			EM.close();
		}
	}
}
