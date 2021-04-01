package Repository;

import App.HibernateService;
import Model.AdminEntity;
import Model.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AdminRepository {

    public AdminEntity findById(int idadmin) {
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM AdminEntity X WHERE X.idadmin = :idadmin");
        query.setParameter("idadmin", idadmin);
        List<AdminEntity> result = query.list();
        return result.get(0);
    }

    public AdminEntity findByUser(String user){
        Session databaseSession = HibernateService.getSessionFactory().openSession();
        Query query = databaseSession.createQuery("FROM AdminEntity X WHERE X.user = :user");
        query.setParameter("user", user);
        List<AdminEntity> result = query.list();
        if(!result.isEmpty()){
            return result.get(0);
        }
        return null;
    }
}
