package com.example.SpringDemo.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Custom configurations if required
        modelMapper.getConfiguration().setSkipNullEnabled(false);

        return modelMapper;
    }
}
