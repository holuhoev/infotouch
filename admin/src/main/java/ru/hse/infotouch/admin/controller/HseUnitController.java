package ru.hse.infotouch.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import ru.hse.infotouch.domain.dto.request.HseUnitPointsRequest;
import ru.hse.infotouch.domain.dto.request.HseUnitElementRequest;
import ru.hse.infotouch.domain.dto.request.HseUnitRequest;
import ru.hse.infotouch.domain.models.admin.HseUnit;
import ru.hse.infotouch.domain.service.HseUnitService;

import java.util.List;

@RestController
@RequestMapping("api/hseUnit")
public class HseUnitController {

    private final HseUnitService hseUnitService;

    @Autowired
    public HseUnitController(HseUnitService hseUnitService) {
        this.hseUnitService = hseUnitService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HseUnit> findOne(@PathVariable("id") int id) {

        return ResponseEntity.ok(hseUnitService.getOneById(id));
    }

    @GetMapping
    public ResponseEntity<List<HseUnit>> findAll(@RequestParam(value = "buildingId", required = false) Integer buildingId) {

        return ResponseEntity.ok(hseUnitService.findAll(buildingId));
    }

    @PostMapping
    public ResponseEntity<HseUnit> createHseUnit(@RequestBody HseUnitRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(hseUnitService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HseUnit> updateHseUnit(@PathVariable("id") int id, @RequestBody HseUnitRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(hseUnitService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHseUnit(@PathVariable("id") Integer id) {

        this.hseUnitService.delete(id);
    }


    @PutMapping("/element")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setElement(@RequestBody HseUnitElementRequest request) {
        this.hseUnitService.setElements(request);
    }

    @DeleteMapping("/element/{elementId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteElement(@PathVariable("elementId") int elementId) {
        this.hseUnitService.removeUnitsFromElement(elementId);
    }
}
