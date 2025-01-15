package com.example.SpringBoot_Angular.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class GenerateKey {

    //após gerar a chave deve ser colada na application.properties
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Gera uma chave segura para HS256
        String base64EncodedKey = Encoders.BASE64.encode(key.getEncoded()); // Codifica em Base64
        System.out.println(base64EncodedKey); // Imprima a chave para usar na configuração
    }
}
