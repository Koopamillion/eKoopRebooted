package koopamillion.mymod.tools;

import com.sun.jna.platform.win32.Winspool;
import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.ModItems;
import koopamillion.mymod.acidbath.TileAcidBath;
import koopamillion.mymod.crafting.AcidManager;
import koopamillion.mymod.crafting.AcidRecipe;
import koopamillion.mymod.simpleblocks.BlockMachineFrame;
import net.minecraft.advancements.critereon.UsedTotemTrigger;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class EventHandler {




    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event){

        if(!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND){
            ItemStack equipped = event.getEntityPlayer().getHeldItem(event.getHand());
            Block block = event.getWorld().getBlockState(event.getPos()).getBlock();
            if(block != null && !equipped.isEmpty()){
                if(block == ModBlocks.blockAcidbath){
                    ItemStack item = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
                   TileEntity te = event.getWorld().getTileEntity(event.getPos());
                   if(te instanceof TileAcidBath){
                           AcidRecipe recipe = AcidManager.getRecipe(item, ((TileAcidBath) te).getTank().getFluid(), ((TileAcidBath) te).getAcidFire(), ((TileAcidBath)te).getElectricity(), null, null);
                           if(recipe != null && ((TileAcidBath) te).getTank().getFluid().getFluid() == recipe.getInput2().getFluid() && ((TileAcidBath) te).getTank().getFluidAmount() >= recipe.getInput2().amount && ((TileAcidBath) te).getAcidFire() == recipe.getBool()&&((TileAcidBath)te).getElectricity() == recipe.getBool2()){
                               if(recipe.getOutput()!=null) {
                                   Helper.spawnItemInWorld(event.getWorld(), recipe.getOutput().copy(), event.getPos().up());
                               }
                               if(((TileAcidBath) te).getElectricity() == true){
                                   if(((TileAcidBath) te).getEnergy() >= ((TileAcidBath) te).RF_PER_USE){
                                       if(!((TileAcidBath) te).getRedstone()){
                                           ((TileAcidBath) te).useEnergy();
                                       }
                                   }
                               }
                                item.shrink(1);
                                if(item.getCount() == 0){
                                    event.getEntityPlayer().setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                                }
                                if(((TileAcidBath) te).getTank().getFluid() == recipe.getOutput2()){

                                }else if(recipe.getOutput2() == null){((TileAcidBath) te).getTank().drain(recipe.getInput2().amount, true);}else{
                                    ((TileAcidBath) te).getTank().drain(recipe.getInput2().amount, true);
                                    ((TileAcidBath) te).getTank().fill(recipe.getOutput2(), true);
                                }

                           }

                   }

                }
            }

        }
    }
}
