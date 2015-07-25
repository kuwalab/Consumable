package net.kuwalab.android.consumable.dao;

import net.kuwalab.android.consumable.entity.Shop;

import java.util.List;

public interface ShopDao {
    long insert(Shop shop);

    List<Shop> selectAll();
}
