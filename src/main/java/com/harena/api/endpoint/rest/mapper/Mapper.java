package com.harena.api.endpoint.rest.mapper;

public interface Mapper<T, R> {
  public R toRestModel(T objectModel);

  public T toObjectModel(R restModel);
}
