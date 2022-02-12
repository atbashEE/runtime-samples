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
package be.rubus.runtime.examples.mpconfig;

import be.atbash.runtime.testing.AbstractAtbashTest;
import be.atbash.runtime.testing.jupiter.AtbashContainerTest;
import jakarta.ws.rs.client.WebTarget;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AtbashContainerTest
public class ConfigResourceIT extends AbstractAtbashTest {

    @Test
    public void testConfigCachedAndProperties() {

        WebTarget path = getClientWebTargetApplication(atbash).path("/data/hello/info");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Config Equals true, properties PATH,awt.toolkit,java.specification.version,sun.cpu.isalist,sun.jnu.encoding,java.class.path,language,java.vm.vendor,home.dir,ld.library.path,sun.arch.data.model,path,hostname,java.vendor.url,injected.value,user.timezone,LD_LIBRARY_PATH,jvm.args,os.name,java.vm.specification.version,PWD,stateless,sun.java.launcher,user.country,sun.boot.library.path,LANGUAGE,sun.java.command,MEM_MAX_RAM_PERCENTAGE,jdk.debug,mem.max.ram.percentage,sun.cpu.endian,user.home,user.language,shlvl,java.specification.vendor,java.version.date,java.home,file.separator,java.vm.compressedOopsMode,line.separator,HOME_DIR,java.specification.name,java.vm.specification.vendor,jdk.vendor.version,LC_ALL,DEPLOYMENT_DIR,java.awt.graphicsenv,pwd,SHLVL,ATBASH_ARGS,STATELESS,sun.management.compiler,CONFIG_FILE_LOCATION,java.runtime.version,runtime.logging.console,JAVA_HOME,mem.xss,user.name,MEM_XSS,path.separator,LANG,os.version,config.file.location,java.runtime.name,file.encoding,java.vm.name,runtime.logging.file,java.vendor.version,lang,value,java.vendor.url.bug,jetty.git.hash,java.io.tmpdir,java.version,user.dir,os.arch,java.vm.specification.name,atbash.args,java.awt.printerjob,runtime.logging.verbose,sun.os.patch.level,mp.config.profile,home,java.util.logging.manager,java.library.path,java.vm.info,java.vendor,HOSTNAME,java.vm.version,JVM_ARGS,deployment.dir,sun.io.unicode.encoding,java.class.version,HOME,lc.all");

    }

    @Test
    public void testConfigValueInjected() {
        WebTarget path = getClientWebTargetApplication(atbash).path("/data/config/injected");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Config value as Injected by CDI Developer value");

    }

    @Test
    public void testConfigLookupValue() {
        WebTarget path = getClientWebTargetApplication(atbash).path("/data/config/lookup");
        String result = path.request().get(String.class);
        assertThat(result).isEqualTo("Config value from ConfigProvider lookup value");

    }

}
