package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.file.BucketConf;
import com.harena.api.file.LocalBucketComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BucketProvider {
  private final String method;
  private final BucketConf bucketConf;

  public BucketProvider(BucketConf bucketConf, @Value("${storage.method}") String method) {
    this.bucketConf = bucketConf;
    this.method = method;
  }

  public BucketComponent getBucket() {
    if (method.equals("LOCAL")) {
      return new LocalBucketComponent(bucketConf);
    } else {
      return new BucketComponent(bucketConf);
    }
  }
}
