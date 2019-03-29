package koopamillion.mymod.dna;

import koopamillion.mymod.network.Messages;
import koopamillion.mymod.network.PacketSyncNBT;
import koopamillion.mymod.network.PacketSyncPower;
import koopamillion.mymod.network.PacketSyncTank;
import koopamillion.mymod.saturator.TileSaturator;
import koopamillion.mymod.tools.IEnergyContainer;
import koopamillion.mymod.tools.INBTContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerDNAExtractor extends Container implements IEnergyContainer, INBTContainer {

    private TileDNAExtractor te;
    private static final int PROGRESS_ID = 0;
    private static final int DNA_ID = 1;


    public ContainerDNAExtractor(IInventory playerInventory, TileDNAExtractor te) {
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
        int y = 8 + 17;


        int slotIndex = 0;

        // Add our own slots
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

            if (index < TileDNAExtractor.SIZE) {
                if (!this.mergeItemStack(itemstack1, TileDNAExtractor.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, TileDNAExtractor.SIZE, false)) {
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
        if (te.getDnaCount() != te.getClientDNA()){
            te.setClientDNA(te.getDnaCount());
        }
        for (IContainerListener listener: listeners){

            listener.sendWindowProperty(this, PROGRESS_ID, Math.round(te.getProgressRemaining()));
            listener.sendWindowProperty(this, DNA_ID, te.getDnaCount());
        }

        if (te.getEnergy() != te.getClientEnergy()) {
            te.setClientEnergy(te.getEnergy());
            for (IContainerListener listener : listeners) {
                if (listener instanceof EntityPlayerMP) {
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    Messages.INSTANCE.sendTo(new PacketSyncPower(te.getEnergy()), player);
                }
            }
        }
        if (te.getUpdateTag()!= te.getClienttag()){
            te.setClienttag(te.getUpdateTag());
            for (IContainerListener listener: listeners){
                if (listener instanceof EntityPlayerMP){
                    EntityPlayerMP player = (EntityPlayerMP) listener;
                    Messages.INSTANCE.sendTo(new PacketSyncNBT(te.getUpdateTag()), player);

                }
            }
        }


    }
    @Override
    public void updateProgressBar(int id, int data) {
        if (id == PROGRESS_ID) {
            te.setClientProgress(data);
        }
        if (id == DNA_ID) {
            te.setClientDNA(data);
        }
    }



    @Override
    public void syncPower(int energy) {
        te.setClientEnergy(energy);
    }

    @Override
    public void syncNBT(NBTTagCompound f) {
        te.setClienttag(f);
    }

}
