/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lifenjoy51.latin;

import me.lifenjoy51.latin.domain.User;
import me.lifenjoy51.latin.service.GoogleSheetService;
import me.lifenjoy51.latin.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class LatinApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(LatinApplication.class);
        app.addListeners(new ApplicationListener<EmbeddedServletContainerInitializedEvent>() {
            @Override
            public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
                GoogleSheetService googleSheetService = event.getApplicationContext().getBean(GoogleSheetService.class);
                googleSheetService.sync();
            }
        });
        app.run(args);
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

}
