package com.example.SpringDemo.Utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MappingUtils {

	
    @Autowired
    private ModelMapper modelMapper;

    // Map single object from source to destination type
    public <S, D> D mapToObject(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    // Map a list of objects from source to destination type
    public <S, D> List<D> mapToList(List<S> source, Class<D> destinationClass) {
        return source.stream()
                .map(item -> modelMapper.map(item, destinationClass))
                .collect(Collectors.toList());
    }
}
