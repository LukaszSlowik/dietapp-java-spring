package org.nutrihealthplan.dietapp.service;

public interface RefreshTokenService {

    void saveOrUpdateRefreshToken(String email, String refreshToken);
    void deleteRefreshToken(String email);
    String getRefreshToken(String email);
}
