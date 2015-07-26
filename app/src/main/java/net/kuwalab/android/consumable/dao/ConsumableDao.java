package net.kuwalab.android.consumable.dao;

import net.kuwalab.android.consumable.entity.Consumable;

import java.util.List;

public interface ConsumableDao {
    long insert(Consumable consumable);

    /**
     * 初期のinsert。<br>
     * 消耗品名と備考のみ登録する
     *
     * @param consumable
     * @return
     */
    long insertInit(Consumable consumable);

    List<Consumable> selectAll();
}
