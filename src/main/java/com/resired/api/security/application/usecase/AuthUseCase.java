package com.resired.api.security.application.usecase;

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
        validateUser(auth, user);
        String jwt;
        if (user.getRoles().size() == 1) {
            Rol rol = user.getRoles().get(0);
            jwt = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
                rol.getHomeId(), user.getId(), ONE_YEAR_DURATION);
        } else {
            jwt = jwtService.generateToken(user.getEmail(), ONE_YEAR_DURATION);
        }
        return new AuthenticationResponse(jwt, user.getRoles(), user.getUserName(), user.getEmail(),
            user.getDocumentId(), user.isMandatoryChangePassword(), null);
    }

    private static void validateUser(AuthenticationRequest auth, User user) {
        if (user == null || user.getRoles()
            .stream()
            .filter(userOrm -> !userOrm.getUserType().equals(UserType.ADMIN))
            .toList().isEmpty()) {
            throw new InvalidCredentialException(auth.email());
        }
        if (!user.isActive()) {
            throw new InactiveUserException(user.getDocumentId());
        }
    }


    public AuthenticationAdminResponse authAdmin(AuthenticationRequest auth) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(auth.password());
        User user = userPort.getUserByCredentials(auth.email(), encryptPass);
        if (user == null || user.getRoles().stream().noneMatch(n -> UserType.ADMIN.equals(n.getUserType()))) {
            throw new InvalidCredentialException(auth.email());
        }
        Rol rol = user.getRoles().get(0);
        String accessToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 2);
        String refreshToken = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(),
            rol.getHomeId(), user.getId(), 4);

        return new AuthenticationAdminResponse(accessToken, refreshToken, user.getRoles(), user.getUserName(), user.getEmail(),
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
}
