package net.kuwalab.android.consumable.dao;

import net.kuwalab.android.consumable.entity.Consumable;

import java.util.List;

public interface ConsumableDao {
    long insert(Consumable consumable);

    List<Consumable> selectAll();
}
