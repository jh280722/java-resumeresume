package com.rere.box;

import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoxRepositoryTest {
    @Autowired
    private BoxRepository boxes;

    @Autowired
    private ItemRepository items;

    @Test
    void findById() {
        Box box = boxes.findByName("box");
        Item item = items.save(Item.of("text", "이름", "준호", box));
        assertThat(box.getItems().size()).isEqualTo(2);
    }
}
