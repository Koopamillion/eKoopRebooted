package koopamillion.mymod.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Helper {
    public static void spawnItemInWorld(World world, ItemStack stack, BlockPos pos) {
        Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, stack);
        world.spawnEntity(entity);
    }

    public static void print(Object o){
        System.out.println(o);
    }

    //creates a cube mesh using coords

    public static void spawnParticleRange(double bottomleftx, double bottomy, double bottomleftz, double toprightx, double topy, double toprightz, BlockPos pos, EnumParticleTypes e, World world){
        double lx = pos.getX() + bottomleftx;
        double ly = pos.getY() + bottomy;
        double lz = pos.getZ() + bottomleftz;
        double rx = pos.getX() + toprightx;
        double ry = pos.getY() + topy;
        double rz = pos.getZ() + toprightz;

        for(double i = toprightx - bottomleftx; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx + i , ly , lz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightx - bottomleftx; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx + i , ly , rz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightx - bottomleftx; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx + i , ry , lz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightx - bottomleftx; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx + i , ry , rz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightz - bottomleftz; i > 0 - 0.05d; i = i -0.05d){
            world.spawnParticle(e, lx  , ly , lz + i ,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightz - bottomleftz; i > 0; i = i -0.05d){
            world.spawnParticle(e, rx  , ly , lz + i,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightz - bottomleftz; i > 0 - 0.05d; i = i -0.05d){
            world.spawnParticle(e, lx  , ry , lz + i,0.0D,0.0D,0.0D,50);
        }
        for(double i = toprightz - bottomleftz; i > 0; i = i -0.05d){
            world.spawnParticle(e, rx  , ry , lz + i,0.0D,0.0D,0.0D,50);
        }

        for(double i = topy - bottomy; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx , ly + i, lz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = topy - bottomy; i > 0; i = i -0.05d){
            world.spawnParticle(e, lx  , ly + i, rz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = topy - bottomy; i > 0; i = i -0.05d){
            world.spawnParticle(e, rx , ly + i , lz ,0.0D,0.0D,0.0D,50);
        }
        for(double i = topy - bottomy; i > 0; i = i -0.05d){
            world.spawnParticle(e, rx , ly + i , rz ,0.0D,0.0D,0.0D,50);
        }

    }

    public static void spawnParticleLaserToPlayer(double posx, double posy, double posz, double x, double y, double z, World world, EnumParticleTypes e){

        print("yo");

        double xdistance = x - (posx + 0.5D);
        double ydistance = y + 1.0D - (posy + 0.5D);
        double zdistance = z - (posz + 0.5D);





        double x2 = xdistance / 50;
        double y2 = ydistance / 50;
        double z2 = zdistance / 50;

        double x3 = 0;
        double y3 = 0;
        double z3 = 0;

        for(double i = 0; i < 10; i = i +0.2D){
            x3 = x3 + x2;

            y3 = y3 + y2;

            z3 = z3 + z2;

            world.spawnParticle(e, (posx + 0.5D) + x3,(posy+0.5D) +  y3,(posz+0.5D) +  z3,0.0D,0.0D,0.0D,0);




        }
    }



    }

