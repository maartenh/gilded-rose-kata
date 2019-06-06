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
    public void conjuredItem() {
        Item[] items = new Item[] { new Item("Conjured foo", 4, 20) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals("Conjured foo", app.items[0].name);
        assertEquals(18, app.items[0].quality);
        assertEquals(3, app.items[0].sellIn);

        // decreases quality 1 per day until expired
        for (int i = 0; i < 3; i++) {
            app.updateQuality();
        }
        assertEquals("Conjured foo", app.items[0].name);
        assertEquals(12, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);

        // decreases quality twice as fast after expiration
        app.updateQuality();
        assertEquals("Conjured foo", app.items[0].name);
        assertEquals(8, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);

        // quality does not decrease below 0
        for (int i = 0; i < 6; i++) {
            app.updateQuality();
        }
        assertEquals("Conjured foo", app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }


    @Test
    public void agedBrie_IncreasesQuality() {
        Item[] items = new Item[] { new Item(SpecialItems.AGED_BRIE, 4, 6) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals(SpecialItems.AGED_BRIE, app.items[0].name);
        assertEquals(7, app.items[0].quality);
        assertEquals(3, app.items[0].sellIn);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.AGED_BRIE, app.items[0].name);
        assertEquals(24, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }

    @Test
    public void sulfuras_RemainsTheSame() {
        Item[] items = new Item[] { new Item(SpecialItems.SULFURAS, 44, 66) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        assertEquals(SpecialItems.SULFURAS, app.items[0].name);
        assertEquals(66, app.items[0].quality);
        assertEquals(44, app.items[0].sellIn);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.SULFURAS, app.items[0].name);
        assertEquals(66, app.items[0].quality);
        assertEquals(44, app.items[0].sellIn);
    }

    @Test
    public void backstagePasses() {
        Item[] items = new Item[] { new Item(SpecialItems.BACKSTAGE_PASSES, 12, 8) };
        GildedRose app = new GildedRose(items);

        app.updateQuality();
        app.updateQuality();
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(10, app.items[0].quality);
        assertEquals(10, app.items[0].sellIn);

        // increases quality 2 per day once less than 10 days before expiring
        for (int i = 0; i < 5; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(20, app.items[0].quality);
        assertEquals(5, app.items[0].sellIn);

        // increases quality 3 per day once less than 5 days before expiring
        for (int i = 0; i < 5; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(35, app.items[0].quality);
        assertEquals(0, app.items[0].sellIn);

        // quality drops to 0 once expired
        app.updateQuality();
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-1, app.items[0].sellIn);

        // quality does not decrease below 0
        for (int i = 0; i < 6; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(0, app.items[0].quality);
        assertEquals(-7, app.items[0].sellIn);
    }


    // The tests below are not based on the requirements document but on pre-existing behaviour

    @Test
    public void agedBrie_MaxQuality() {
        Item[] items = new Item[] { new Item(SpecialItems.AGED_BRIE, 60, 6) };
        GildedRose app = new GildedRose(items);

        // quality is limited to at most 50
        for (int i = 0; i < 50; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.AGED_BRIE, app.items[0].name);
        assertEquals(50, app.items[0].quality);
        assertEquals(10, app.items[0].sellIn);
    }

    @Test
    public void backstagePasses_MaxQuality() {
        Item[] items = new Item[] { new Item(SpecialItems.BACKSTAGE_PASSES, 60, 6) };
        GildedRose app = new GildedRose(items);

        // quality is limited to at most 50
        for (int i = 0; i < 50; i++) {
            app.updateQuality();
        }
        assertEquals(SpecialItems.BACKSTAGE_PASSES, app.items[0].name);
        assertEquals(50, app.items[0].quality);
        assertEquals(10, app.items[0].sellIn);
    }


}
