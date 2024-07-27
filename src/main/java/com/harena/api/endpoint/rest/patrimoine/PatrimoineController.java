package com.harena.api.endpoint.rest.patrimoine;

import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import com.harena.api.service.mappers.PatrimoineMapper;
import com.harena.api.utils.Page;
import com.harena.api.utils.PageRequest;
import com.harena.api.utils.Pageable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("patrimoines")
public class PatrimoineController {
  private final PatrimoineService patrimoineService;
  private final PatrimoineMapper mapper;

  @PutMapping
  public List<Patrimoine> crupdatePatrimoines(@RequestBody ListPayload<Patrimoine> entity) {
    List<school.hei.patrimoine.modele.Patrimoine> patrimoines =
        entity.data().stream().map(mapper::toObjectModel).toList();
    return patrimoineService.savePatrimoines(patrimoines).stream()
        .map(mapper::toRestModel)
        .toList();
  }

  @GetMapping
  public Page<Patrimoine> getPatrimoines(
      @RequestParam("page") int pageNumber, @RequestParam("page_size") int pageSize) {
    Pageable<Patrimoine> patrimoinePageable =
        new Pageable<>(
            patrimoineService.getAllPatrimoine().stream().map(mapper::toRestModel).toList());
    return patrimoinePageable.getPage(PageRequest.of(pageNumber, pageSize));
  }

  @GetMapping("/{nom_patrimoine}")
  public Patrimoine getPatrimoine(@PathVariable(name = "nom_patrimoine") String id) {
    Optional<school.hei.patrimoine.modele.Patrimoine> patrimoine =
        patrimoineService.getPatrimone(id);
    if (patrimoine.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return mapper.toRestModel(patrimoine.get());
  }
}
