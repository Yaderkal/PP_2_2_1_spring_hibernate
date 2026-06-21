package hiber.service;

import hiber.dao.UserDao;
import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   @Autowired
   private SessionFactory sessionFactory;
   @Autowired
   private UserDao userDao;

   @Transactional
   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional
   @Override
   public void add(Car car) {
      userDao.add(car);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional(readOnly = true)
   @Override
   public List<Car> listCars() {
      return userDao.listCars();
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
