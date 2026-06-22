package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
   //private final фабрика сессий потокобезопасна и защищена от рефлексивных запросов?
   private final SessionFactory sessionFactory;
   //позволяет
   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) { //позволяет
      this.sessionFactory = sessionFactory;
   }
   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void add(Car car) {
   sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<Car> listCars() {
      TypedQuery<Car> query=sessionFactory.getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }
   @Transactional
   @Override
   public List<User> getOwner(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      Query<User> query = session.createQuery(
              "SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series",
              User.class
      );
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.list();
   }

}
