package com.rere.box;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import com.rere.user.domain.User;
import com.rere.user.domain.UserRepository;
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
    @Autowired
    SortationRepository sortations;
    @Autowired
    UserRepository users;

    private Box box;

    @BeforeEach
    void setUp() {

        User user = users.save(User.of());
        Sortation sortation = sortations.save(Sortation.of("sortation", user));
        Document document = documents.save(Document.of("document", sortation));

        box = boxes.save(Box.of("box3",document));
        items.save(Item.of(0, "text", "이름", "준호", box,0L,0L,""));
        items.save(Item.of(1, "text", "이름", "준호", box,0L,0L,""));
    }

}
