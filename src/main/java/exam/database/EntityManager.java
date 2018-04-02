package exam.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * EntityManager singleton pattern class
 * Allow to access all the required methods to interact with database
 * Avoid to instantiate multiple EMF or EM or ET
 */
public class EntityManager {

    /**
     * INTERN STATE - VARIABLES
     */
    private static EntityManager sInstance;
    private static EntityManagerFactory entityManagerFactory;
    private static javax.persistence.EntityManager entityManager;
    private static EntityTransaction entityTransaction;

    /**
     * Methods to get singleton instance of EntityManager custom class
     * @return EntityManager
     */
    public static EntityManager getInstance(){
        if(sInstance == null){
            sInstance = new EntityManager();
            sInstance.setEntityManagerFactory(Persistence.createEntityManagerFactory("testpostgresqllocal")); // name of the persistence unit in persistence.xml
            sInstance.setEntityManager(sInstance.getEntityManagerFactory().createEntityManager());
            sInstance.setEntityTransaction(sInstance.getEntityManager().getTransaction());
        }

        return sInstance;
    }

    /**
     * CONSTRUCTORS
     */
    public EntityManager() {
    }

    /**
     * GETTERS AND SETTERS
     * @return
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        EntityManager.entityManagerFactory = entityManagerFactory;
    }

    public static javax.persistence.EntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(javax.persistence.EntityManager entityManager) {
        EntityManager.entityManager = entityManager;
    }

    public static EntityTransaction getEntityTransaction() {
        return entityTransaction;
    }

    public static void setEntityTransaction(EntityTransaction entityTransaction) {
        EntityManager.entityTransaction = entityTransaction;
    }
}
