package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByCar(String model, int series) {
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User WHERE userCar.model = :model and userCar.series = :series");
            query.setParameter("model", model).setParameter("series", series);
            return query.setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            System.out.print("У автомобиля модели " + model + " и серии" + series + " нет владельца! - ");
            return null;
        }
    }
}
