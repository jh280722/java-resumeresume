package com.rere.document.application;

import com.rere.box.application.BoxService;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.document.dto.DocumentRequest;
import com.rere.document.dto.DocumentResponse;
import com.rere.item.application.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    public static final long DEFAULT_ID = 0L;
    private final DocumentRepository documentRepository;
    private final BoxService boxService;
    private final ItemService itemService;

    public DocumentService(DocumentRepository documentRepository, BoxService boxService, ItemService itemService) {
        this.documentRepository = documentRepository;
        this.boxService = boxService;
        this.itemService = itemService;
    }

    public List<DocumentResponse> getDocuments() {
        return documentRepository.findAll().stream()
                .map(DocumentResponse::of)
                .collect(Collectors.toList());
    }

    public DocumentResponse findById(Long id) {
        return DocumentResponse.of(documentRepository.findById(id).orElse(Document.of()));
    }

    public void updateName(Long id, DocumentRequest documentRequest) {
        Document document = documentRepository.findById(id).orElse(Document.of());
        document.changeName(documentRequest.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        boxService.deleteByDocumentId(id);
        documentRepository.deleteById(id);
    }

    @Transactional
    public void deleteBySortationId(Long id) {
        List<Long> DocumentId = documentRepository.findBySortationId(id);
        for (Long documentId : DocumentId) {
            boxService.deleteByDocumentId(documentId);
        }
        documentRepository.deleteBySortationId(id);
    }

    public DocumentResponse save(DocumentRequest documentRequest) {
        return DocumentResponse.of(documentRepository.save(Document.of(documentRequest.getName())));
    }

}
