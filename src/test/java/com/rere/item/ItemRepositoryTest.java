package com.rere.item;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository items;
    @Autowired
    private BoxRepository boxes;

    @Test
    void save() {
        Box box = boxes.save(new Box("box"));
        final Item item = items.save(Item.of("text", "이름", "준호", box));
        assertThat(item.getId()).isNotNull();
        assertThat(item.getValue()).isEqualTo("준호");
    }

    @Test
    void findByType() {
        Box box = boxes.save(new Box("box"));
        items.save(Item.of("textArea", "이름", "준호", box));
        final Item actual = items.findByType("textArea");
        assertThat(actual.getValue()).isEqualTo("준호");
    }

    @Test
    void identity() {
        Box box = boxes.save(new Box("box"));
        final Item item = items.save(Item.of("text", "이름", "준호", box));
        final Item item1 = items.findById(item.getId()).get();
        assertThat(item1 == item).isTrue();
    }

    @Test
    void update() {
        Box box = boxes.save(new Box("box"));
        final Item item1 = items.save(Item.of("textArea", "자기소개", "hi", box));
        item1.changeName("변경");
        final Item item2 = items.findByName("변경");
        assertThat(item2).isNotNull();
    }

    @Test
    void findByNameWithBox() {
        final Item actual = items.findByName("이름");
        assertThat(actual.getBox()).isNotNull();
        assertThat(actual.getBox().getId()).isEqualTo(1);
    }

    @Test
    void updateWithBox() {
        final Item expected = items.findByName("이름");
        expected.setBox(boxes.save(new Box("box")));
        items.flush();
    }
}
