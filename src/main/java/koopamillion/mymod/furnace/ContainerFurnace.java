package koopamillion.mymod.furnace;


import koopamillion.mymod.network.Messages;
import koopamillion.mymod.network.PacketSyncPower;
import koopamillion.mymod.tools.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFurnace extends Container implements IEnergyContainer {

    private TileFurnace te;

    private static final int PROGRESS_ID = 0;
    private static final int TIME_ID = 1;

    public ContainerFurnace(IInventory playerInventory, TileFurnace te) {
        this.te = te;

        // This container references items out of our own inventory (the 9 slots we hold ourselves)
        // as well as the slots from the player inventory so that the user can transfer items between
        // both inventories. The two calls below make sure that slots are defined for both inventories.
        addOwnSlots();
        addPlayerSlots(playerInventory);
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
        int x = 10;
        int y = 25;


        int slotIndex = 0;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));    x += 18;
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, x, y));

        // Add our own slots
        x = 118;
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

            if (index < TileFurnace.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileFurnace.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileFurnace.SIZE, false)) {
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
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }

    //send stuff to the server

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if (te.getProgressRemaining() != te.getClientProgress()){
            te.setClientProgress(te.getProgressRemaining());
        }
        //makes sure gui time is always the same as time
        if (te.getTime() != te.getGuiTime()){
            te.setGuiTime(te.getTime());
        }
        //sends vars to a packet
        for (IContainerListener listener: listeners){
            listener.sendWindowProperty(this, PROGRESS_ID, Math.round(te.getProgressRemaining()));
            listener.sendWindowProperty(this, TIME_ID, Math.round(te.getTime()));
        }
        if (te.getEnergy()!= te.getClientEnergy()){
            te.setClientEnergy(te.getEnergy());
            for (IContainerListener listener: listeners){
                if (listener instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    Messages.INSTANCE.sendTo(new PacketSyncPower(te.getEnergy()), player);
                }
            }
        }
    }


    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */

    @Override
    public void updateProgressBar(int id, int data) {
        if(id == PROGRESS_ID){
            te.setClientProgress(data);
        }
        if(id == TIME_ID){
            te.setGuiTime(data);
        }

    }

    @Override
    public void syncPower(int energy) {
        te.setClientEnergy(energy);
    }
}
