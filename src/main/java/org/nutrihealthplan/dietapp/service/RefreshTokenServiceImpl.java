package org.nutrihealthplan.dietapp.service;


import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.model.RefreshTokenEntity;
import org.nutrihealthplan.dietapp.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    public final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveOrUpdateRefreshToken(String email, String refreshToken) {

        RefreshTokenEntity refreshTokenEntityToSave = RefreshTokenEntity.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build();
        refreshTokenRepository.save(refreshTokenEntityToSave);
    }

    @Override
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteById(email);

    }

    @Override
    public String getRefreshToken(String email) {
        return refreshTokenRepository.findById(email)
                .map(RefreshTokenEntity::getRefreshToken)
                .orElse(null);
    }
}
