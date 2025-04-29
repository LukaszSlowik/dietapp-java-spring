package org.nutrihealthplan.dietapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@RedisHash("refresh_token_entity")
public class RefreshTokenEntity implements Serializable {
    @Id // org.springframework.data.annotation.Id
    private String email;
    private String refreshToken;
}
