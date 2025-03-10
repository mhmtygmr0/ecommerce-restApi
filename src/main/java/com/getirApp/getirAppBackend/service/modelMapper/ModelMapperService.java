package com.getirApp.getirAppBackend.service.modelMapper;

public interface ModelMapperService {

    org.modelmapper.ModelMapper forRequest();

    org.modelmapper.ModelMapper forResponse();
}
