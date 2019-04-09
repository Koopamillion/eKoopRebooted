package koopamillion.mymod.ballmill;

import koopamillion.mymod.centrifuge.TileCentrifuge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerBallmill extends Container   {

    private TileBallmill te;
    private static final int PROGRESS_ID = 0;
    private static final int RECIEVED_ID = 1;
    private static final int ROTATION_ID = 2;
    private static final int CRAFT_ID = 3;



    public ContainerBallmill(IInventory playerInventory, TileBallmill te) {
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

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 10 + j1 * 18, 87 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(playerInventory, i1, 10 + i1 * 18, 145));
        }

    }

    private void addOwnSlots() {
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);



        int slotIndex = 0;

        // Add our own slots
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 81, 13));
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 10, 58));
        int i = 10 + 3*18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, i, 58));
        i += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, i, 58));
        i += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, i, 58));



    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileBallmill.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileBallmill.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileBallmill.SIZE, false)) {
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


    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        if (te.getProgressRemaining() != te.getClientProgress()){
            te.setClientProgress(te.getProgressRemaining());
        }
        if (te.getIdleRPM() != te.getClientIdleRPM()){
            te.setClientIdleRPM(te.getIdleRPM());
        }
        if (te.getRot() != te.getClientGUIRPM()){
            te.setClientGUIRPM(te.getRot());
        }
        if (te.getCraftRPM() != te.getClientCraftRPM()){
            te.setClientCraftRPM(te.getCraftRPM());
        }

        for (IContainerListener listener: listeners){

            listener.sendWindowProperty(this, PROGRESS_ID, Math.round(te.getProgressRemaining()));
            listener.sendWindowProperty(this, RECIEVED_ID, te.getIdleRPM());
            listener.sendWindowProperty(this, ROTATION_ID, te.getRot());
            listener.sendWindowProperty(this, CRAFT_ID, te.getCraftRPM());

        }



    }
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID) {
            te.setClientProgress(data);
        }
        if (id == RECIEVED_ID) {
            te.setClientIdleRPM(data);
        }
        if (id == ROTATION_ID) {
            te.setClientGUIRPM(data);
        }
        if (id == CRAFT_ID) {
            te.setClientCraftRPM(data);
        }

    }





}
