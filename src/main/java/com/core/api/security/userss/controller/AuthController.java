package com.core.api.security.userss.controller;

import com.core.api.security.dto.EmailDTO;
import com.core.api.security.JWTUtil;
import com.core.api.security.userss.UserSS;
import com.core.api.security.service.AuthService;
import com.core.api.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api(value = "autenticação user")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @PostMapping(value="/refresh_token")
    @ApiOperation(value = "Gera novo token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value="/forgot")
    @ApiOperation(value = "Recupera a senha")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDTO) {
        authService.sendNewPassWord(objDTO.getEmail());

        return ResponseEntity.noContent().build();
    }

}
