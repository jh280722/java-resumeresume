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
                Item.of(2L, 0,"text", "ho","jun", Box.of(),0L,0L),
                Item.of(5L,1,"text","ho","jun", Box.of(),0L,0L),
                Item.of(4L,2,"text","ho","jun", Box.of(),0L,0L),
                Item.of(3L,3,"text","ho","jun", Box.of(),0L,0L)
        ));
        items.updateSeq(2L,3);

        assertThat(items.findById(5L).getSeq()).isEqualTo(0);
        assertThat(items.findById(4L).getSeq()).isEqualTo(1);
        assertThat(items.findById(3L).getSeq()).isEqualTo(2);
        assertThat(items.findById(2L).getSeq()).isEqualTo(3);
    }
}
