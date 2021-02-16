package com.rere.document;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DocumentRepositoryTest {
    @Autowired
    private BoxRepository boxes;


    @Autowired
    private DocumentRepository documents;

    @Test
    void getBoxes() {
        Document document = documents.findByName("document");
        Box box = boxes.save(Box.of("box2",document));
        assertThat(document.getBoxes().size()).isEqualTo(2);
    }
}
