package com.rere.item;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.item.domain.Items;
import org.junit.jupiter.api.BeforeEach;
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

    private Item item;
    private Items itemsTest;
    
    @BeforeEach
    public void setUp() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));

        item = items.save(Item.of(0, "text", "이름", "준호", box));
    }

    @Test
    void delete() {
        assertThat(items.findAll()).hasSize(3);
        items.deleteByBoxId(item.getBox().getId());
        assertThat(items.findAll()).hasSize(2);
    }

    @Test
    void save() {
        assertThat(item.getId()).isNotNull();
        assertThat(item.getValue()).isEqualTo("준호");
    }

    @Test
    void findByType() {
        Long itemId = item.getId();
        final Item actual = items.findById(itemId).orElse(null);
        assertThat(actual.getValue()).isEqualTo("준호");
    }

    @Test
    void identity() {
        final Item item1 = items.findById(item.getId()).get();
        assertThat(item1 == item).isTrue();
    }

    @Test
    void updateSeqs() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));

        Item item0 = items.save(Item.of(0, "text", "ho", "jun", box));
        Item item1 = items.save(Item.of(1, "text", "ho", "jun", box));
        Item item2 = items.save(Item.of(2, "text", "ho", "jun", box));
        Item item3 = items.save(Item.of(3, "text", "ho", "jun", box));
        itemsTest = Items.of(Arrays.asList(
                item0, item3, item2, item1));

        assertThat(itemsTest.size()).isEqualTo(4);
        itemsTest.updateSeq(item0.getId(), 3);

        assertThat(itemsTest.findById(item0.getId()).getSeq()).isEqualTo(3);
        assertThat(itemsTest.findById(item1.getId()).getSeq()).isEqualTo(0);
        assertThat(itemsTest.findById(item2.getId()).getSeq()).isEqualTo(1);
        assertThat(itemsTest.findById(item3.getId()).getSeq()).isEqualTo(2);
    }

    @Test
    void update() {
        item.changeName("변경");
        item.changeSeq(1);
        final Item item2 = items.findById(item.getId()).orElse(Item.of());
        assertThat(item2).isNotNull();
        assertThat(item2.getName()).isEqualTo("변경");
        assertThat(item2.getSeq()).isEqualTo(1);
    }

    @Test
    void updateWithBox() {
        Document document = documents.save(Document.of("document"));
        Box box = boxes.save(Box.of("box", document));
        final Item expected = items.save(
                Item.of(0, "textArea", "자기소개", "hi", box));
        expected.setBox(boxes.save(Box.of("updateBox", document)));
        items.flush();
        assertThat(expected.getBox().getName()).isEqualTo("updateBox");
    }
}
