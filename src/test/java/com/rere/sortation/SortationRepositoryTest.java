package com.rere.sortation;

import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SortationRepositoryTest {
    @Autowired
    private DocumentRepository documents;


    @Autowired
    private SortationRepository sortations;

    @Test
    void getDocuments() {
        Sortation sortation = sortations.findByName("sortation");
        Document document = documents.save(Document.of("documents", sortation));
        assertThat(sortation.getDocuments().size()).isEqualTo(2);
    }
}
