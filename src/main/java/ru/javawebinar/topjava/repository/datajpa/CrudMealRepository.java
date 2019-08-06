package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    @Query("SELECT m FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    Meal get(@Param("id") int id, @Param("userId") int userId);


    @Query("DELETE  FROM Meal m WHERE m.id=:id and m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    //List<Meal> getAllByUser(User user);


    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime  ")
    List<Meal> getBetween (@Param("startDate") LocalDateTime startDate,@Param("endDate")  LocalDateTime endDate, @Param("userId") int userId);

}
