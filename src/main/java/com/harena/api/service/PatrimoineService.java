package com.harena.api.service;

import com.harena.api.utils.StringNormalizer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.serialisation.Serialiseur;

@Log
@Service
@RequiredArgsConstructor
public class PatrimoineService {
  private final String PATRIMOINE_KEY = "patrimoines.db";
  private final BucketProvider bucketProvider;
  private final Serialiseur<HashMap<String, Patrimoine>> serializer = new Serialiseur<>();

  @SneakyThrows
  public Optional<Patrimoine> getPatrimone(String patrimoineName) {
    String id = StringNormalizer.apply(patrimoineName);
    File patrimoineFile = bucketProvider.getBucket().download(PATRIMOINE_KEY);
    if (!patrimoineFile.exists()) {
      return Optional.empty();
    }

    HashMap<String, Patrimoine> patrimoine =
        serializer.deserialise(Files.readString(patrimoineFile.toPath()));
    log.info("Getting patrimoine of Id: " + id);
    return Optional.ofNullable(patrimoine.get(id));
  }

  @SneakyThrows
  public List<Patrimoine> savePatrimoines(List<Patrimoine> toSavePatrimoines) {
    try {
      File oldPatrimoinesFile = bucketProvider.getBucket().download(PATRIMOINE_KEY);
      String oldPatrimoineString = Files.readString(oldPatrimoinesFile.toPath());
      HashMap<String, Patrimoine> patrimoines = serializer.deserialise(oldPatrimoineString);
      updatePatrimoine(toSavePatrimoines, patrimoines);
      return patrimoines.values().stream().toList();
    } catch (Exception e) {
      Path tempFile = Files.createTempFile(PATRIMOINE_KEY, null);
      HashMap<String, Patrimoine> tempPatrimoine = new HashMap<>();
      Files.writeString(tempFile, serializer.serialise(tempPatrimoine));
      updatePatrimoine(toSavePatrimoines, tempPatrimoine);
      return toSavePatrimoines;
    }
  }

  private void updatePatrimoine(
      List<Patrimoine> toSavePatrimoines, HashMap<String, Patrimoine> currentPatrimoine)
      throws IOException {
    toSavePatrimoines.forEach(
        patrimoine -> {
          currentPatrimoine.put(StringNormalizer.apply(patrimoine.nom()), patrimoine);
        });
    String newPatrimoineString = serializer.serialise(currentPatrimoine);
    Path tmpFile = Files.createTempFile(PATRIMOINE_KEY, null);
    File serializedPatrimoine = Files.writeString(tmpFile, newPatrimoineString).toFile();
    bucketProvider.getBucket().upload(serializedPatrimoine, PATRIMOINE_KEY);
  }

  @SneakyThrows
  public List<Patrimoine> getAllPatrimoine() {
    try {
      File patrimoinesFile = bucketProvider.getBucket().download(PATRIMOINE_KEY);
      HashMap<String, Patrimoine> patrimoines =
          serializer.deserialise(Files.readString(patrimoinesFile.toPath()));
      return patrimoines.values().stream().toList();
    } catch (Exception e) {
      return List.of();
    }
  }
}
