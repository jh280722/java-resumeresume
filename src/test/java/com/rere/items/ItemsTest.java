package com.rere.items;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;
import com.rere.item.domain.Items;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemsTest {
    @Test
    void updateSeqs() {
        Items items = Items.of(Arrays.asList(
                Item.of(2L, 0,"text", "ho","jun", Box.of()),
                Item.of(5L,1,"text","ho","jun", Box.of()),
                Item.of(4L,2,"text","ho","jun", Box.of()),
                Item.of(3L,3,"text","ho","jun", Box.of())
        ));
        items.updateSeq(2L,3);

        assertThat(items.findBySeq(0).getId()).isEqualTo(5L);
        assertThat(items.findBySeq(1).getId()).isEqualTo(4L);
        assertThat(items.findBySeq(2).getId()).isEqualTo(3L);
        assertThat(items.findBySeq(3).getId()).isEqualTo(2L);
    }
}
