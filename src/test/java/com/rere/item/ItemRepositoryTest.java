package com.rere.item;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.item.domain.Items;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository items;
    @Autowired
    private BoxRepository boxes;
    @Autowired
    private DocumentRepository documents;

    @Test
    void delete() {
        assertThat(items.findAll()).hasSize(1);
        items.deleteByBoxId(1L);
        assertThat(items.findAll()).hasSize(0);
    }

    @Test
    void save() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        final Item item = items.save(Item.of(0, "text", "이름", "준호", box));
        assertThat(item.getId()).isNotNull();
        assertThat(item.getValue()).isEqualTo("준호");
    }

    @Test
    void findByType() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        items.save(Item.of(0, "textArea", "이름", "준호", box));
        final Item actual = items.findByType("textArea");
        assertThat(actual.getValue()).isEqualTo("준호");
    }

    @Test
    void identity() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        final Item item = items.save(Item.of(0, "text", "이름", "준호", box));
        final Item item1 = items.findById(item.getId()).get();
        assertThat(item1 == item).isTrue();
    }

    @Test
    void updateSeqs() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        Items itemsTest = Items.of(Arrays.asList(
                items.save(Item.of(3L, 0, "text", "ho", "jun", box)),
                items.save(Item.of(4L, 3, "text", "ho", "jun", box)),
                items.save(Item.of(5L, 2, "text", "ho", "jun", box)),
                items.save(Item.of(6L, 1, "text", "ho", "jun", box))
                ));

        itemsTest.updateSeq(3L, 3);

        assertThat(itemsTest.findById(6L).getSeq()).isEqualTo(0);
        assertThat(itemsTest.findById(5L).getSeq()).isEqualTo(1);
        assertThat(itemsTest.findById(4L).getSeq()).isEqualTo(2);
        assertThat(itemsTest.findById(3L).getSeq()).isEqualTo(3);
    }

    @Test
    void update() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        final Item item1 = items.save(Item.of(0, "textArea", "자기소개", "hi", box));
        item1.changeName("변경");
        item1.changeSeq(1);
        final Item item2 = items.findByName("변경");
        assertThat(item2).isNotNull();
        assertThat(item2.getSeq()).isEqualTo(1);
    }

    @Test
    void findByNameWithBox() {
        final Item actual = items.findByName("이름");
        assertThat(actual.getBox()).isNotNull();
        assertThat(actual.getBox().getId()).isEqualTo(1);
    }

    @Test
    void updateWithBox() {
        Document document = documents.save(Document.of("document"));
        final Item expected = items.findByName("이름");
        expected.setBox(boxes.save(Box.of("box", document)));
        items.flush();
    }
}
