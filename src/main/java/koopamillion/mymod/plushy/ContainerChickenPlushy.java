package koopamillion.mymod.plushy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChickenPlushy extends Container {

    private TileChickenPlushy te;

    public ContainerChickenPlushy(IInventory playerInventory, TileChickenPlushy te) {
        this.te = te;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
                this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 10 + j1 * 18, 70 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlotToContainer(new Slot(playerInventory, i1, 10 + i1 * 18, 128));
        }
    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        int y = 8;


        int slotIndex = 0;

        // Add our own slots
        int x = 64;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        y += 18;
        x = 64;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
        y += 18;
        x = 64;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileChickenPlushy.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileChickenPlushy.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileChickenPlushy.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
