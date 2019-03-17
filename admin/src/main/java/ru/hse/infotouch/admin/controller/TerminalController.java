package ru.hse.infotouch.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.infotouch.domain.dto.request.TerminalRequest;
import ru.hse.infotouch.domain.models.admin.Terminal;
import ru.hse.infotouch.domain.service.TerminalService;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("api/terminal")
public class TerminalController {

    private final TerminalService terminalService;

    public TerminalController(TerminalService terminalService) {
        this.terminalService = terminalService;
    }

    @GetMapping
    public ResponseEntity<List<Terminal>> findAll(@RequestParam(value = "searchString", required = false) String searchString,
                                                  @RequestParam(value = "page", required = false) Integer page) {

        return ResponseEntity.ok(terminalService.findAll(
                searchString,
                isNull(page) ? 0 : page
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Terminal> getOneById(@PathVariable("id") int id) {

        return ResponseEntity.ok(terminalService.getOneById(id));
    }

    @PostMapping
    public ResponseEntity<Terminal> createTerminal(TerminalRequest request) {

        return ResponseEntity.ok(terminalService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terminal> updateTerminal(@PathVariable("id") int id,
                                                   TerminalRequest request) {

        return ResponseEntity.ok(terminalService.update(id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTerminal(@PathVariable("id") int id) {

        terminalService.delete(id);
    }
}
