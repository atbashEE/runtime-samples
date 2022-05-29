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
