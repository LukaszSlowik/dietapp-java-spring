package org.nutrihealthplan.dietapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.dto.meal.MealCreateRequest;
import org.nutrihealthplan.dietapp.dto.meal.MealProductCreateResponse;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.service.MealProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealProductService mealProductService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseApi<MealProductCreateResponse>> createMeal( @Valid @RequestBody MealCreateRequest mealCreateRequest,

                                                                             HttpServletRequest httpServletRequest){
        String path = httpServletRequest.getRequestURI();

        MealProductCreateResponse mealProductCreateResponse = mealProductService.createMeal(mealCreateRequest);

        return ResponseEntity.ok(ResponseApiFactory.success(mealProductCreateResponse,path));

    }
};




