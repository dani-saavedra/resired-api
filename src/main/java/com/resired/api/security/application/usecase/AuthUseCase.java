package com.resired.api.security.application.usecase;

import com.resired.api.admin.application.exception.BusinessException;
import com.resired.api.security.application.dto.*;
import com.resired.api.security.application.exception.ExpiredTokenException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.entity.Rol;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
@Slf4j
public class AuthUseCase {

    public static final int ONE_YEAR_DURATION = 8760;
    private final AuthenticationService authService;
    private final UserPort userPort;
    private final JwtService jwtService;

    public AuthenticationResponse authUser(AuthenticationRequest auth) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(auth.password());
        User user = userPort.getUserByCredentials(auth.email(), encryptPass);
        List<Rol> rols = validateUser(auth, user, userType -> !userType.equals(UserType.ADMIN));
        String jwt;
        if (rols.size() == 1) {
            Rol rol = rols.get(0);
            jwt = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
                rol.getHomeId(), user.getId(), ONE_YEAR_DURATION);
        } else {
            jwt = jwtService.generateToken(user.getEmail(), ONE_YEAR_DURATION);
        }
        return new AuthenticationResponse(jwt, rols, user.getUserName(), user.getEmail(),
            user.getDocumentId(), user.isMandatoryChangePassword(), null);
    }

    public AuthenticationAdminResponse authAdmin(AuthenticationRequest auth) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(auth.password());
        User user = userPort.getUserByCredentials(auth.email(), encryptPass);
        List<Rol> rols = validateUser(auth, user, userType -> !userType.equals(UserType.RESIDENT) && !userType.equals(UserType.GUARD));
        if (rols.isEmpty()) {
            throw new InvalidCredentialException(auth.email());
        }
        Rol rol = rols.get(0);
        String accessToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 2);
        String refreshToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 4);

        return new AuthenticationAdminResponse(accessToken, refreshToken, rols, user.getUserName(), user.getEmail(),
            user.getDocumentId(), user.isMandatoryChangePassword());
    }


    public RefreshResponse refreshToken(RefreshRequest token) {
        try {
            String accessToken = jwtService.regenerateToken(token.refreshToken(), 2);
            String refreshToken = jwtService.regenerateToken(token.refreshToken(), 4);
            return new RefreshResponse(accessToken, refreshToken);
        } catch (ExpiredJwtException e) {
            log.error("Refresh token expired", e);
            throw new ExpiredTokenException();
        }

    }

    public RefreshResponse chooseNeighborhood(Integer neighborhoodId, Integer integer) {
        User user = userPort.getUserById(integer);

        Rol rol = user.getRoles().stream()
            .filter(n -> UserType.ADMIN.equals(n.getUserType()) && Objects.equals(n.getNeighborhoodId(), neighborhoodId))
            .findFirst()
            .orElseThrow(() -> new BusinessException("Neighborhood not found", "GENERAL_BAD_REQUEST"));

        String accessToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 2);
        String refreshToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 4);

        return new RefreshResponse(accessToken, refreshToken);
    }

    private static List<Rol> validateUser(AuthenticationRequest auth, User user, Predicate<UserType> userTypeFilter) {
        if (user == null || user.getRoles()
            .stream()
            .filter(userOrm -> userTypeFilter.test(userOrm.getUserType()))
            .toList().isEmpty()) {
            throw new InvalidCredentialException(auth.email());
        }
        if (!user.isActive()) {
            throw new InactiveUserException(user.getDocumentId());
        }
        return user.getRoles()
            .stream()
            .filter(userOrm -> userTypeFilter.test(userOrm.getUserType()))
            .toList();
    }
}
