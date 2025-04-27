package org.nutrihealthplan.dietapp.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
@Data
@AllArgsConstructor
@Builder
@RedisHash("refresh_token_entity")
public class RefreshTokenEntity {
    @Id
    private String email;
    private String refreshToken;
}
