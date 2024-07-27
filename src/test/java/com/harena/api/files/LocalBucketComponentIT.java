package com.harena.api.files;

import static org.junit.jupiter.api.Assertions.*;

import com.harena.api.conf.FacadeIT;
import com.harena.api.file.LocalBucketComponent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LocalBucketComponentIT extends FacadeIT {
  @Autowired LocalBucketComponent subject;
  private final String TEST_FILE_KEY = "test";

  @Test
  void local_bucket_component() throws IOException {
    Path testFile = Files.createTempFile(TEST_FILE_KEY, null);
    Files.writeString(testFile, "TEST_FILE");
    assertNotNull(subject.getBucketName());
    assertDoesNotThrow(() -> subject.upload(testFile.toFile(), TEST_FILE_KEY));
    assertTrue(subject.download(TEST_FILE_KEY).exists());
  }
}
