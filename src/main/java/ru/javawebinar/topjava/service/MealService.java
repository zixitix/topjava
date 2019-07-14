package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface MealService {
    Meal create(Meal user,int userId);

    void delete(int id,int userId) throws NotFoundException;

    MealTo get(int id, int userId) throws NotFoundException;

    void update(Meal user,int userId);

    List<MealTo> getAll(int userId);
}