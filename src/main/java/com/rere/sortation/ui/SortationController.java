package com.rere.sortation.ui;

import com.rere.auth.domain.AuthenticationPrincipal;
import com.rere.sortation.application.SortationService;
import com.rere.sortation.dto.SortationRequest;
import com.rere.sortation.dto.SortationResponse;
import com.rere.sortation.exception.InvalidSortationException;
import com.rere.user.domain.LoginUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sortations")
public class SortationController {
    private final SortationService sortationService;

    public SortationController(SortationService sortationService) {
        this.sortationService = sortationService;
    }

    @PostMapping
    public ResponseEntity<SortationResponse> createSortation(@RequestBody SortationRequest sortationRequest) {
        SortationResponse sortationResponse = sortationService.save(sortationRequest);
        return ResponseEntity.created(URI.create("/sortations/" + sortationResponse.getId()))
                .body(sortationResponse);
    }

    @GetMapping
    public ResponseEntity<List<SortationResponse>> getSortation() {
        return ResponseEntity.ok().body(sortationService.getSortations());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SortationResponse> showSortation(@PathVariable Long id) {
        return ResponseEntity.ok().body(sortationService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateSortation(@RequestBody SortationRequest sortationRequest, @PathVariable Long id) {
        sortationService.updateName(id, sortationRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSortation(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long id) {
        sortationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
