package com.example.gyrodraw.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the shop of the app. It contains a List of items that represent
 * the items that can be bought in the shop.
 */
public class Shop {

    private List<ShopItem> itemList;

    Shop() {
        itemList = new ArrayList<>();
    }

    void removeItem(ShopItem shopItem) {
        itemList.remove(shopItem);
    }

    void addItem(ShopItem shopItem) {
        itemList.add(shopItem);
    }

    List<ShopItem> getItemList() {
        return new LinkedList<>(itemList);
    }

    /**
     * Converts an {@link HashMap} into a list of shop items.
     *
     * @param map Map of the colors and prices
     * @return List of ShopItems
     */
    public static List<ShopItem> firebaseToListShopItem(HashMap<String, String> map) {
        List<ShopItem> listItem = new ArrayList<>();

        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                int value = Integer.parseInt(String.valueOf(entry.getValue()));
                listItem.add(new ShopItem(
                        ColorsShop.getColorFromString(entry.getKey()), value));
            }
        }

        return listItem;
    }
}
