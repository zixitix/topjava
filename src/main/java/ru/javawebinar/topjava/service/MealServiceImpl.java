package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal user,int userId) {
        return repository.save(user,userId);
    }

    @Override
    public void delete(int id,int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id,userId), id);
    }

    @Override
    public MealTo get(int id, int userId) throws NotFoundException {
        Meal meal = checkNotFoundWithId(repository.get(id,userId), id);
        for(MealTo mealTo : getAll(userId)){
            if(mealTo.getId() == meal.getId()){
                return mealTo;
            }
        }
        return null;

    }

    @Override
    public void update(Meal user,int userId) {
        checkNotFoundWithId(repository.save(user,userId), user.getId());
    }

    @Override
    public List<MealTo> getAll(int userId) {
        List<Meal> meals = repository.getAll(userId);
        Map<LocalDate, List<Meal>> map = new HashMap<>();
        List<MealTo>  mealToList = new ArrayList<>();
        for(Meal meal : meals){
            if(map.containsKey(meal.getDate())){
                map.get(meal.getDate()).add(meal);
            }else {
                List<Meal> newMeal = new ArrayList<>();
                newMeal.add(meal);
                map.put(meal.getDate(),newMeal);
            }
        }
        for(Map.Entry<LocalDate,List<Meal>> entry : map.entrySet()){
            int calories = 0;
            for(Meal meal : entry.getValue()){
                calories = meal.getCalories() + calories;
            }
            if(calories > MealsUtil.DEFAULT_CALORIES_PER_DAY){
                for(Meal meal : entry.getValue()){
                 mealToList.add(new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),true));
                }
            } else{
                for(Meal meal : entry.getValue()){
                    mealToList.add(new MealTo(meal.getId(),meal.getDateTime(),meal.getDescription(),meal.getCalories(),false));
                }

            }
        }
        return mealToList;
    }
}