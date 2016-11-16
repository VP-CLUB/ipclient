/**
 * Copyright 2005-2015 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.example.domain.IPAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@RestController
public class IPAddressController {
    private int counter;

    private static Logger logger = LoggerFactory.getLogger(IPAddressController.class);
    @Value("${ipclient.serviceUrl}")
    private String serviceUrl;

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "localIp")
    public IPAddress ipaddress() throws Exception {
        RestTemplate template = new RestTemplate();
        logger.info("url ist " + serviceUrl);
        return template.getForEntity(serviceUrl, IPAddress.class).getBody();
    }

    public IPAddress localIp() throws UnknownHostException {
        IPAddress address = new IPAddress();
        address.setId(++counter);
        address.setIpAddress(Inet4Address.getLocalHost().getHostAddress());
        address.setMessage("this is a local response");
        return address;
    }

}
