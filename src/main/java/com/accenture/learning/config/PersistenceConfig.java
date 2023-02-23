package com.accenture.learning.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages ="com.accenture.learning.repository")
public class PersistenceConfig {
}
