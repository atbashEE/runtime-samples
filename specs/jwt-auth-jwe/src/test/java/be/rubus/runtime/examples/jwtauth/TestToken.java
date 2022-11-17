/*
 * Copyright 2022 Rudy De Busscher (https://www.atbash.be)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.rubus.runtime.examples.jwtauth;

import be.atbash.ee.security.octopus.jwt.JWTEncoding;
import be.atbash.ee.security.octopus.jwt.encoder.JWTEncoder;
import be.atbash.ee.security.octopus.jwt.parameter.JWTParameters;
import be.atbash.ee.security.octopus.jwt.parameter.JWTParametersBuilder;
import be.atbash.ee.security.octopus.keys.AtbashKey;
import be.atbash.ee.security.octopus.keys.reader.KeyReader;
import be.atbash.ee.security.octopus.nimbus.jwt.JWTClaimsSet;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestToken {

    public static String createJWE() {
        JWTEncoder jwtEncoder = new JWTEncoder();

        KeyReader keyReader = new KeyReader();
        List<AtbashKey> signKeyList = keyReader.readKeyResource("classpath:privateKey4k.pem");
        List<AtbashKey> encryptKeyList = keyReader.readKeyResource("classpath:publicKey.pem");

        LocalDateTime exp = LocalDateTime.now().plusSeconds(30);

        JWTClaimsSet payload = new JWTClaimsSet.Builder()
                .audience("targetService")
                .issuer("https://server.example.com")  // Must match the expected issues configuration values
                .jwtID(UUID.randomUUID().toString())
                .subject("Jessie")
                .issueTime(new Date())
                .expirationTime(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
                .claim("custom-value", "Jessie specific value")
                .claim("groups", List.of("Tester"))
                .build();

        JWTParameters parameters = JWTParametersBuilder.newBuilderFor(JWTEncoding.JWE)
                .withSecretKeyForSigning(signKeyList.get(0))
                .withSecretKeyForEncryption(encryptKeyList.get(0))
                .build();

        return jwtEncoder.encode(payload, parameters);
    }
}
