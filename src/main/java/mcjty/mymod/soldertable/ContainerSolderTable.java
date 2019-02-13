package mcjty.mymod.soldertable;


import mcjty.mymod.network.Messages;
import mcjty.mymod.network.PacketSyncPower;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSolderTable extends Container {

    private TileSolderTable te;
    private static final int PROGRESS_ID= 0;
    private static final int FLUID_ID = 1;

    public ContainerSolderTable(IInventory playerInventory, TileSolderTable te) {
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
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 10 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 10 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
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
        y = 18+8;


        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 64-27, y));
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex++, 64+81, y));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < TileSolderTable.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileSolderTable.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileSolderTable.SIZE, false)) {
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
        if (te.getFluidAmount() != te.getClientFluidAmount()){
            te.setClientFluidAmount(te.getFluidAmount());
        }
        if (te.getProgressRemaining() != te.getClientProgress()){
            te.setClientProgress(te.getProgressRemaining());
        }
        //makes sure gui time is always the same as time

        //sends vars to a packet
        for (IContainerListener listener: listeners){
            listener.sendWindowProperty(this, FLUID_ID, te.getFluidAmount());
            listener.sendWindowProperty(this, PROGRESS_ID, Math.round(te.getProgressRemaining()));

        }

    }
    @Override
    public void updateProgressBar(int id, int data) {
        if(id == PROGRESS_ID){
            te.setClientProgress(data);
        }
        if(id == FLUID_ID){
            te.setClientFluidAmount(data);
        }
    }
    /*public void updateFluidBar(FluidStack fluid, int id){
        if(id == FLUID_ID){
            te.setClientFluid(fluid);

        }
    }*/


}
