package com.harena.api.endpoint.rest.model;

import java.util.List;

public record ListPayload<T>(List<T> data) {}
