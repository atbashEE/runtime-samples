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

import be.atbash.ee.security.octopus.keys.AtbashKey;
import be.atbash.ee.security.octopus.keys.KeyManager;
import be.atbash.ee.security.octopus.keys.reader.KeyReader;
import be.atbash.ee.security.octopus.keys.selector.AsymmetricPart;
import be.atbash.ee.security.octopus.keys.selector.SelectorCriteria;

import java.util.Arrays;
import java.util.List;

public class TestKeyManager implements KeyManager {
    @Override
    public List<AtbashKey> retrieveKeys(SelectorCriteria selectorCriteria) {
        List<AtbashKey> result;

        KeyReader keyReader = new KeyReader();
        if (selectorCriteria.getAsymmetricPart() == AsymmetricPart.PRIVATE) {

            result = keyReader.readKeyResource("classpath:privateKey.pem");
            result = Arrays.asList(result.get(0));  // Take only the private key, that is the first one.
        } else {
            result = keyReader.readKeyResource("classpath:publicKey4k.pem");
        }
        return result;
    }
}
