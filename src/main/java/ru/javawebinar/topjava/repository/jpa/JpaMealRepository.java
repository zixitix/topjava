package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;


@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if(meal.isNew()){
            User ref = em.getReference(User.class, userId);
            meal.setUser(ref);
            em.persist(meal);
            return meal;
        }else{

            // Hibernate.initialize(meal.getUser());
            if(get(meal.getId(),userId) == null){
                return null;
            }else{
                 em.merge(meal);
                return meal;
            }
        }

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {


        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;


    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = em.find(Meal.class,id);
        if(m.getUser().getId() == userId) {
            return m;
        }else{
            return null;
        }

    }

    @Override
    public List<Meal> getAll(int userId) {

        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter("start_date", startDate)
                .setParameter("end_date", endDate)
                .setParameter("user_id", userId )
                .getResultList();

    }
}