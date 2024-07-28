package com.harena.api.endpoint.rest.mapper;

public interface Mapper<T, R> {
  R toRestModel(T objectModel);

  T toObjectModel(R restModel);
}
