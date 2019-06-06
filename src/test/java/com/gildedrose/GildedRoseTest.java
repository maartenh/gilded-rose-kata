package com.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest {

    @Test
    public void normalItem() {
        Item[] items = new Item[] { new Item("foo", 4, 8) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals("foo", app.items[0].name);
        assertEquals(7, app.items[0].quality);
        assertEquals(3, app.items[0].sellIn);

        // decreases quality 1 per day until expired
        for (int i = 0; i < 3; i++) {
            app.updateQuality();
        }
        assertEquals("foo", app.items[0].name);
        assertEquals(4, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);

        // decreases quality twice as fast after expiration
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
        assertEquals(2, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);

        // quality does not decrease below 0
        for (int i = 0; i < 6; i++) {
            app.updateQuality();
        }
        assertEquals("foo", app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }

    @Test
    public void agedBrie_IncreasesQuality() {
        Item[] items = new Item[] { new Item("Aged Brie", 4, 6) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(7, app.items[0].quality);
        assertEquals(3, app.items[0].sellIn);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals("Aged Brie", app.items[0].name);
        assertEquals(24, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }

    @Test
    public void sulfuras_RemainsTheSame() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", 44, 66) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
        assertEquals(66, app.items[0].quality);
        assertEquals(44, app.items[0].sellIn);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals("Sulfuras, Hand of Ragnaros", app.items[0].name);
        assertEquals(66, app.items[0].quality);
        assertEquals(44, app.items[0].sellIn);
    }

    @Test
    public void backstagePasses() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 12, 8) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(10, app.items[0].quality);
        assertEquals(10, app.items[0].sellIn);

        // increases quality 2 per day once less than 10 days before expiring
        for (int i = 0; i < 5; i++) {
            app.updateQuality();
        }
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(20, app.items[0].quality);
        assertEquals(5, app.items[0].sellIn);

        // increases quality 3 per day once less than 5 days before expiring
        for (int i = 0; i < 5; i++) {
            app.updateQuality();
        }
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(35, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);

        // quality drops to 0 once expired
        app.updateQuality();
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);

        // quality does not decrease below 0
        for (int i = 0; i < 6; i++) {
            app.updateQuality();
        }
        assertEquals("Backstage passes to a TAFKAL80ETC concert", app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }
}
