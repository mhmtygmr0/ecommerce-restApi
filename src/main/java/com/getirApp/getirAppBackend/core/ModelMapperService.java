package com.getirApp.getirAppBackend.core;

public interface ModelMapperService {

    org.modelmapper.ModelMapper forRequest();

    org.modelmapper.ModelMapper forResponse();
}
