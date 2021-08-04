package com.core.api.security.service;

import com.core.api.cliente.domain.ClienteRepository;
import com.core.api.security.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassWord(String email) {
        var cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ObjectNotFoundException("Email não encontrado.");
        }
        String novaSenha = newPassword();
        cliente.setSenha(pe.encode(novaSenha));

        clienteRepository.save(cliente);

        emailService.sendNewPasswordEmail(cliente, novaSenha);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0) {     //gera um digito
            return (char) (rand.nextInt(10) + 48);
        } else if (opt == 1) {       //gera letra maiuscula
            return (char) (rand.nextInt(26) + 65);
        } else {      //gera letra minuscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
