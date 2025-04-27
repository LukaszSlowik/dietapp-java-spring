package org.nutrihealthplan.dietapp.dto.meal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealCreateRequest {
    @NotNull(message = "Owner ID must not be null")
    private Long ownerId;
    @NotNull(message = "Meal date must not be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate mealDate;
    @NotEmpty
    @Valid
    private List<MealProductCreateRequest> products;
}
