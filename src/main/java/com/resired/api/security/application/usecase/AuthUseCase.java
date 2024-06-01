package com.resired.api.security.application.usecase;

import com.resired.api.security.application.dto.AuthenticationRequest;
import com.resired.api.security.application.dto.AuthenticationResponse;
import com.resired.api.security.domain.entity.Rol;
import com.resired.api.security.domain.exception.InactiveUserException;
import com.resired.api.security.application.exception.InvalidCredentialException;
import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.repository.UserPort;
import com.resired.api.security.domain.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;

@Service
@AllArgsConstructor
public class AuthUseCase {

    private final AuthenticationService authService;
    private final UserPort userPort;
    private final JwtService jwtService;

    public AuthenticationResponse authUser(AuthenticationRequest auth) throws GeneralSecurityException {
        String encryptPass = authService.encrypt(auth.password());
        User user = userPort.getUserByCredentials(auth.email(), encryptPass);
        if (user == null) {
            throw new InvalidCredentialException(auth.email());
        }
        if (!user.isActive()) {
            throw new InactiveUserException(user.getDocumentId());
        }
        String jwt;
        if (user.getRoles().size() == 1) {
            Rol rol = user.getRoles().get(0);
            jwt = jwtService.generateToken(user.getEmail(), rol.getUserType().name(), rol.getNeighborhoodId(), rol.getHomeId());
        } else {
            jwt = jwtService.generateToken(user.getEmail());
        }
        return new AuthenticationResponse(jwt, user.getRoles(), user.getUserName(),
            user.getDocumentId(), user.isMandatoryChangePassword());
    }

}
