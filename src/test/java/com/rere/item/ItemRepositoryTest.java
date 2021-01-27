package com.rere.item;

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

    @Test
    void save() {
        final Item item = items.save(Item.of("text", "이름", "준호", 1L));
        assertThat(item.getId()).isNotNull();
        assertThat(item.getValue()).isEqualTo("준호");
    }

    @Test
    void findByType() {
        items.save(Item.of("text", "이름", "준호", 1L));
        final Item actual = items.findByType("text");
        assertThat(actual.getValue()).isEqualTo("준호");
    }

    @Test
    void identity() {
        final Item item = items.save(Item.of("text", "이름", "준호", 1L));
        final Item item1 = items.findById(item.getId()).get();
        assertThat(item1 == item).isTrue();
    }

    @Test
    void update() {
        final Item item1 = items.save(Item.of("textArea", "자기소개", "hi", 1L));
        item1.changeName("변경");
        final Item item2 = items.findByName("변경");
        assertThat(item2).isNotNull();
    }
}
