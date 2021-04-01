package Repository;

import App.HibernateService;
import Model.CustomerEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

public class CustomerRepository implements Repository<CustomerEntity> {


    @Override
    public CustomerEntity save(CustomerEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("FROM CustomerEntity X WHERE X.email = :email");
        query.setParameter("email", entity.getEmail());
        List<CustomerEntity> result = query.list();

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
    public CustomerEntity update(CustomerEntity entity) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        databaseSession.beginTransaction();

        Query query = databaseSession.createQuery("UPDATE CustomerEntity X SET X.name = :name, X.email = :email, " +
                "X.password = :password, X.address = :address, X.loyalty = :loyalty, X.deleted = :deleted WHERE X.idcustomer = :idcustomer");

        query.setParameter("idcustomer", entity.getIdcustomer());
        query.setParameter("name", entity.getName());
        query.setParameter("email", entity.getEmail());
        query.setParameter("password", entity.getPassword());
        query.setParameter("address", entity.getAddress());
        query.setParameter("loyalty", entity.isLoyalty());
        query.setParameter("deleted", entity.isDeleted());

        query.executeUpdate();
        databaseSession.flush();
        databaseSession.getTransaction().commit();
        return entity;

    }

    @Override
    public CustomerEntity findById(int idcustomer) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CustomerEntity X WHERE X.idcustomer = :idcustomer");
        query.setParameter("idcustomer", idcustomer);
        List<CustomerEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    public CustomerEntity findByEmail(String email){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CustomerEntity X WHERE X.email = :email");
        query.setParameter("email", email);
        List<CustomerEntity> result = query.list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<CustomerEntity> findAll() {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM CustomerEntity");
        List<CustomerEntity> result = query.list();
        return result;
    }

    @Override
    public boolean delete(CustomerEntity entity) {
        return false;
    }

    @Override
    public String validate(CustomerEntity entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<CustomerEntity>> constraintViolations = validator.validate(entity);
        String result = "";
        if(constraintViolations.size() > 0) {
            for(ConstraintViolation<CustomerEntity> message : constraintViolations){
                result += message.getMessageTemplate() + "\n";
            }
        }
        return result;
    }
}
