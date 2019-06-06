package com.gildedrose;

public abstract class ItemUpdateStrategy {
    public static ItemUpdateStrategy determineFor(Item item) {
        switch (item.name) {
            case SpecialItems.AGED_BRIE:
                return agedBrie;
            case SpecialItems.SULFURAS:
                return sulfuras;
            case SpecialItems.BACKSTAGE_PASSES:
                return backstagePasses;
            default:
                if (item.name.startsWith(SpecialItems.CONJURED)) {
                    return conjured;
                } else {
                    return normal;
                }
        }
    }

    public void update(Item item) {
        int oldQuality = item.quality;

        updateQuality(item);
        updateSellIn(item);

        // quality never drops below 0
        item.quality = Math.max(item.quality, 0);

        // quality never increases to above 50
        if (item.quality > oldQuality) {
            item.quality = Math.min(item.quality, 50);
        }
    }

    abstract void updateQuality(Item item);

    void updateSellIn(Item item) {
        item.sellIn -= 1;
    }

    private static ItemUpdateStrategy normal = new ItemUpdateStrategy() {
        @Override
        public void updateQuality(Item item) {
            if (item.sellIn > 0) {
                item.quality -= 1;
            } else {
                item.quality -= 2;
            }
        }
    };

    private static ItemUpdateStrategy conjured = new ItemUpdateStrategy() {
        @Override
        public void updateQuality(Item item) {
            if (item.sellIn > 0) {
                item.quality -= 2;
            } else {
                item.quality -= 4;
            }
        }
    };

    private static ItemUpdateStrategy agedBrie = new ItemUpdateStrategy() {
        @Override
        public void updateQuality(Item item) {
            if (item.sellIn > 0) {
                item.quality += 1;
            } else {
                item.quality += 2;
            }
        }
    };

    private static ItemUpdateStrategy sulfuras = new ItemUpdateStrategy() {
        @Override
        public void updateQuality(Item item) {
            // never changes quality
        }

        @Override
        void updateSellIn(Item item) {
            // never changes sellIn
        }
    };

    private static ItemUpdateStrategy backstagePasses = new ItemUpdateStrategy() {
        @Override
        public void updateQuality(Item item) {
            if (item.sellIn > 10) {
                item.quality += 1;
            } else if (item.sellIn > 5) {
                item.quality += 2;
            } else if (item.sellIn > 0) {
                item.quality += 3;
            } else {
                item.quality = 0;
            }
        }
    };


}
