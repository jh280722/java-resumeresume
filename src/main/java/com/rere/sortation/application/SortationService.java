package com.rere.sortation.application;

import com.rere.box.application.BoxService;
import com.rere.document.application.DocumentService;
import com.rere.item.application.ItemService;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import com.rere.sortation.dto.SortationRequest;
import com.rere.sortation.dto.SortationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SortationService {
    private final SortationRepository sortationRepository;
    private final DocumentService documentService;
    private final BoxService boxService;
    private final ItemService itemService;

    public SortationService(SortationRepository sortationRepository, DocumentService documentService, BoxService boxService, ItemService itemService) {
        this.sortationRepository = sortationRepository;
        this.documentService = documentService;
        this.boxService = boxService;
        this.itemService = itemService;
    }

    public List<SortationResponse> getSortations() {
        return sortationRepository.findAll().stream()
                .map(SortationResponse::of)
                .collect(Collectors.toList());
    }

    public SortationResponse findById(Long id) {
        return SortationResponse.of(sortationRepository.findById(id).orElse(Sortation.of()));
    }

    public void updateName(Long id, SortationRequest sortationRequest) {
        Sortation sortation = sortationRepository.findById(id).orElse(Sortation.of());
        sortation.changeName(sortationRequest.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        documentService.deleteBySortationId(id);
        sortationRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUserId(Long id) {
        List<Long> DocumentId = sortationRepository.findbyUserId(id);
        for (Long documentId : DocumentId) {
            documentService.deleteBySortationId(documentId);
        }
        sortationRepository.deleteByUserId(id);
    }

    public SortationResponse save(SortationRequest sortationRequest) {
        return SortationResponse.of(sortationRepository.save(Sortation.of(sortationRequest.getName(), sortationRequest.getUser())));
    }

}
