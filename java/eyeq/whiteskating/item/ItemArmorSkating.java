package eyeq.whiteskating.item;

import eyeq.util.UItemArmor;
import eyeq.whiteskating.WhiteSkating;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorSkating extends UItemArmor {
    public static final ArmorMaterial aromorMaterialSkating = EnumHelper.addArmorMaterial("skating", "", 99, new int[]{3, 8, 6, 3}, 10, null, 0.0F);

    private static final ResourceLocation armorName = new ResourceLocation(WhiteSkating.MOD_ID, "skating");

    public ItemArmorSkating(int renderIndex, EntityEquipmentSlot slot) {
        super(aromorMaterialSkating, renderIndex, slot, armorName);
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EntityEquipmentSlot slot, String type) {
        if(type == null && slot == EntityEquipmentSlot.FEET) {
            return super.getArmorTexture(itemStack, entity, slot, type);
        }
        return null;
    }

    @Override
    protected void onArmorTickFeet(World world, EntityPlayer player, ItemStack itemStack) {
        BlockPos pos = player.getPosition();
        for(int x = -2; x < 3; x++) {
            for(int y = -2; y < 1; y++) {
                for(int z = -2; z < 3; z++) {
                    BlockPos pos1 = pos.add(x, y, z);
                    if(world.getBlockState(pos1).getMaterial().isLiquid()) {
                        player.motionY *= 0.8F;
                        world.setBlockState(pos1, Blocks.ICE.getDefaultState());
                        itemStack.damageItem(1, player);
                    }
                }
            }
        }
        Material material = world.getBlockState(pos.down()).getMaterial();
        if(material == Material.ICE || material == Material.SNOW) {
            float d = (float) (player.motionX * player.motionX + player.motionZ * player.motionZ);
            if(player.isSneaking()) {
                player.motionX *= 0.9;
                player.motionZ *= 0.9;
            } else if(0.01F < d && d < 1.0F) {
                player.motionX *= 1.6;
                player.motionZ *= 1.6;
            }
        }
    }
}
