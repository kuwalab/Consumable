package net.kuwalab.android.consumable.dao;

import android.support.annotation.Nullable;

import net.kuwalab.android.consumable.entity.ConsumablePic;

public interface ConsumablePicDao {
    @Nullable
    ConsumablePic selectByKey(long consumablePicId);
}
