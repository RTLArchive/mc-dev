package net.minecraft.server;

public class ContainerPlayer extends Container {

    public InventoryCrafting a;
    public IInventory b;
    public boolean c;

    public ContainerPlayer(InventoryPlayer inventoryplayer) {
        this(inventoryplayer, true);
    }

    public ContainerPlayer(InventoryPlayer inventoryplayer, boolean flag) {
        this.a = new InventoryCrafting(this, 2, 2);
        this.b = new InventoryCraftResult();
        this.c = false;
        this.c = flag;
        this.a((Slot) (new SlotResult(inventoryplayer.d, this.a, this.b, 0, 144, 36)));

        int i;
        int j;

        for (i = 0; i < 2; ++i) {
            for (j = 0; j < 2; ++j) {
                this.a(new Slot(this.a, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }

        for (i = 0; i < 4; ++i) {
            this.a((Slot) (new SlotArmor(this, inventoryplayer, inventoryplayer.getSize() - 1 - i, 8, 8 + i * 18, i)));
        }

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.a(new Slot(inventoryplayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.a(new Slot(inventoryplayer, i, 8 + i * 18, 142));
        }

        this.a((IInventory) this.a);
    }

    public void a(IInventory iinventory) {
        this.b.setItem(0, CraftingManager.a().a(this.a));
    }

    public void a(EntityHuman entityhuman) {
        super.a(entityhuman);

        for (int i = 0; i < 4; ++i) {
            ItemStack itemstack = this.a.getItem(i);

            if (itemstack != null) {
                entityhuman.b(itemstack);
                this.a.setItem(i, (ItemStack) null);
            }
        }
    }

    public boolean b(EntityHuman entityhuman) {
        return true;
    }

    public ItemStack a(int i) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.e.get(i);

        if (slot != null && slot.b()) {
            ItemStack itemstack1 = slot.getItem();

            itemstack = itemstack1.j();
            if (i == 0) {
                this.a(itemstack1, 9, 45, true);
            } else if (i >= 9 && i < 36) {
                this.a(itemstack1, 36, 45, false);
            } else if (i >= 36 && i < 45) {
                this.a(itemstack1, 9, 36, false);
            } else {
                this.a(itemstack1, 9, 45, false);
            }

            if (itemstack1.count == 0) {
                slot.c((ItemStack) null);
            } else {
                slot.c();
            }

            if (itemstack1.count == itemstack.count) {
                return null;
            }

            slot.a(itemstack1);
        }

        return itemstack;
    }
}
