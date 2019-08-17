package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    public static final String REST_URL = "/rest/meals";
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal){
        super.update(meal,SecurityUtil.authUserId());
    }

    @Override
    @GetMapping("/all")
    public List<MealTo> getAll(){
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createOrUpdate(@RequestBody Meal meal){
        checkNew(meal);
        Meal created = super.create(meal);
        URI UriOfNewResourse = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(UriOfNewResourse).body(created);
    }


    @GetMapping(value = "/between",consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getBetween(@RequestBody @DateTimeFormat(iso = ISO.DATE) LocalDateTime startDate,@RequestBody @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate){
        return super.getBetween(startDate.toLocalDate(),startDate.toLocalTime(),endDate.toLocalDate(),endDate.toLocalTime());
    }
}