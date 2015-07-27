package net.kuwalab.android.consumable.entity;

public class ConsumablePic {
    /** テーブル名 */
    public static final String NAME = "consumable_pic";
    /** 消耗品写真ID */
    public static final String CONSUMABLE_PIC_ID = "consumable_pic_id";
    /** 消耗品ID */
    public static final String CONSUMABLE_ID = "consumable_id";
    /** 消耗品写真 */
    public static final String CONSUMABLE_PIC = "consumable_pic";

    private int consumablePicId;
    private int consumableId;
    private byte[] consumablePic;

    public int getConsumablePicId() {
        return consumablePicId;
    }

    public void setConsumablePicId(int consumablePicId) {
        this.consumablePicId = consumablePicId;
    }

    public int getConsumableId() {
        return consumableId;
    }

    public void setConsumableId(int consumableId) {
        this.consumableId = consumableId;
    }

    public byte[] getConsumablePic() {
        return consumablePic;
    }

    public void setConsumablePic(byte[] consumablePic) {
        this.consumablePic = consumablePic;
    }
}
