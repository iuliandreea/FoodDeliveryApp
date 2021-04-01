package Repository;

import App.HibernateService;
import Model.CustomerEntity;
import Model.ProductEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class ProductRepository implements Repository<ProductEntity> {
    @Override
    public ProductEntity save(ProductEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("FROM ProductEntity X WHERE X.name = :name");
        query.setParameter("name", entity.getName());
        List<ProductEntity> result = query.list();

        if(result.size() == 0){
            databaseSession.saveOrUpdate(entity);
            databaseSession.save(entity);
            databaseSession.flush();
            databaseSession.getTransaction().commit();
        }
        else{
            return null;
        }
        return entity;
    }

    @Override
    public ProductEntity update(ProductEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("UPDATE ProductEntity X SET X.name = :name, X.price = :price, " +
                "X.description = :description, X.deleted = :deleted WHERE X.idproduct = :idproduct");

        query.setParameter("idproduct", entity.getIdproduct());
        query.setParameter("name", entity.getName());
        query.setParameter("price", entity.getPrice());
        query.setParameter("description", entity.getDescription());
        query.setParameter("deleted", entity.isDeleted());

        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;
    }

    @Override
    public ProductEntity findById(int idproduct) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM ProductEntity X WHERE X.idproduct = :idproduct");
        query.setParameter("idproduct", idproduct);
        List<ProductEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public ProductEntity findByName(String name) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM ProductEntity X WHERE X.name = :name");
        query.setParameter("name", name);
        List<ProductEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<ProductEntity> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM ProductEntity");
        List<ProductEntity> result = query.list();
        return result;
    }

    @Override
    public boolean delete(ProductEntity entity) {
        return false;
    }

    @Override
    public String validate(ProductEntity entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ProductEntity>> constraintViolations = validator.validate(entity);
        String result = "";
        if(constraintViolations.size() > 0) {
            for(ConstraintViolation<ProductEntity> message : constraintViolations){
                result += message.getMessageTemplate() + "\n";
            }
        }
        return result;
    }
}
