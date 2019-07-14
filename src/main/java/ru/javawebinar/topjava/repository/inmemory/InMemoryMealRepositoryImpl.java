package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((p)->this.save(p,p.getUserId()));
    }

    @Override
    public Meal save(Meal meal,int userId) {
        if (meal.getUserId() == userId) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // treat case: update, but absent in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }else{
            return null;
        }
    }

    @Override
    public boolean delete(int id,int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        }else {
            return false;
        }
    }

    @Override
    public Meal get(int id,int userId) {
        if (repository.get(id).getUserId() == userId) {
            return repository.get(id);
        }else
            return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = new ArrayList();
        for(Meal meal : repository.values()){
            if (meal.getUserId() == userId) {
                meals.add(meal);
            }
        }
         Collections.sort(meals,(o1,o2)-> o2.getDateTime().compareTo(o1.getDateTime()));
        return meals;
    }
}

