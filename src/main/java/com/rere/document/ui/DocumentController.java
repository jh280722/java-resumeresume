package com.rere.document.ui;

import com.rere.document.application.DocumentService;
import com.rere.document.dto.DocumentRequest;
import com.rere.document.dto.DocumentResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<DocumentResponse> createDocument(@RequestBody DocumentRequest documentRequest) {
        DocumentResponse documentResponse = documentService.save(documentRequest);
        return ResponseEntity.created(URI.create("/documents/" + documentResponse.getId()))
                .body(documentResponse);
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getDocument() {
        return ResponseEntity.ok().body(documentService.getDocuments());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentResponse> showDocument(@PathVariable Long id) {
        return ResponseEntity.ok().body(documentService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updateDocument(@RequestBody DocumentRequest documentRequest, @PathVariable Long id) {
        documentService.updateName(id, documentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
