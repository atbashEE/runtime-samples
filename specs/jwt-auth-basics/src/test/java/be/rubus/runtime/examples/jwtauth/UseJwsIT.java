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
import be.atbash.ee.security.octopus.keys.ListKeyManager;
import be.atbash.ee.security.octopus.keys.reader.KeyReader;
import be.atbash.ee.security.octopus.keys.reader.KeyResourceType;
import be.atbash.ee.security.octopus.keys.selector.AsymmetricPart;
import be.atbash.ee.security.octopus.keys.selector.SelectorCriteria;
import be.atbash.ee.security.octopus.nimbus.jwt.JWTClaimsSet;
import be.atbash.runtime.testing.AbstractAtbashTest;
import be.atbash.runtime.testing.jupiter.AtbashContainerTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AtbashContainerTest(startupParameters = "-m +mp-jwt")
public class UseJwsIT extends AbstractAtbashTest {

    @Test
    public void testJWT() {

        String result = getClientWebTargetApplication(atbash)
                .path("/endp/custom")
                .request()
                .header("Authorization", "Bearer " + generateToken())
                .get(String.class);
        Assertions.assertThat(result).isEqualTo("Jessie specific value");


    }

    private String generateToken() {
        KeyReader keyReader = new KeyReader();
        List<AtbashKey> keys = keyReader.readKeyResource(KeyResourceType.PEM, "classpath:privateKey.pem");

        ListKeyManager keyManager = new ListKeyManager(keys);

        SelectorCriteria criteria = SelectorCriteria.newBuilder().withId(keys.get(0).getKeyId()).withAsymmetricPart(AsymmetricPart.PRIVATE).build();
        List<AtbashKey> signKeyList = keyManager.retrieveKeys(criteria);

        Assertions.assertThat(signKeyList).as("We should have 1 Private key for signing").hasSize(1);

        JWTParameters parameters = JWTParametersBuilder.newBuilderFor(JWTEncoding.JWS)
                .withSecretKeyForSigning(signKeyList.get(0))
                .build();

        return new JWTEncoder().encode(getClaims(), parameters);

    }

    private JWTClaimsSet getClaims() {
        LocalDateTime exp = LocalDateTime.now().plusSeconds(30);
        return new JWTClaimsSet.Builder()
                .audience("targetService")
                .issuer("https://server.example.com")  // Must match the expected issues configuration values
                .jwtID(UUID.randomUUID().toString())
                .subject("Jessie")
                .issueTime(new Date())
                .expirationTime(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
                .claim("custom-value", "Jessie specific value")
                .claim("groups", List.of("Tester"))
                .build();
    }

}