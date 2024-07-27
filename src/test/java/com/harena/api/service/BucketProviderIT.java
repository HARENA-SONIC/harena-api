package com.harena.api.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.harena.api.conf.FacadeIT;
import com.harena.api.file.BucketComponent;
import com.harena.api.file.BucketConf;
import com.harena.api.file.LocalBucketComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BucketProviderIT extends FacadeIT {
  @Autowired private BucketConf conf;

  @Test
  void bucket_provider() {
    BucketProvider subjectLocal = new BucketProvider(conf, "LOCAL");

    BucketProvider subjectS3 = new BucketProvider(conf, "S3");

    assertInstanceOf(LocalBucketComponent.class, subjectLocal.getBucket());
    assertInstanceOf(BucketComponent.class, subjectS3.getBucket());
  }
}
