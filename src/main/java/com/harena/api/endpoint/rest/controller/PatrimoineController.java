package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PatrimoineMapper;
import com.harena.api.endpoint.rest.model.ListPayload;
import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.exception.NotFoundException;
import com.harena.api.service.PatrimoineService;
import com.harena.api.utils.Page;
import com.harena.api.utils.PageRequest;
import com.harena.api.utils.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    return mapper.toRestModel(
        patrimoineService.getPatrimone(id).orElseThrow(NotFoundException::new));
  }
}
