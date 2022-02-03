package com.flock.bankManager.utils;

import com.antkorwin.xsync.XSync;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XSyncConfig {

    @Bean
    public XSync<String> xSync() {
        return new XSync<>();
    }
}
