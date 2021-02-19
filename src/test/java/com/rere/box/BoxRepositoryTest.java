package com.rere.box;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BoxRepositoryTest {
    @Autowired
    private ItemRepository items;
    @Autowired
    private BoxRepository boxes;
    @Autowired
    private DocumentRepository documents;

    private Box box;

    @BeforeEach
    void setUp() {
        Document document = documents.save(Document.of("document"));

        box = boxes.save(Box.of("box3",document));
        items.save(Item.of(0, "text", "이름", "준호", box));
        items.save(Item.of(1, "text", "이름", "준호", box));
    }

}
