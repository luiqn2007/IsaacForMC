package lq2007.mcmod.isaacmod.item;

import lq2007.mcmod.isaacmod.entity.ModEntities;
import lq2007.mcmod.isaacmod.entity.RegisterEntity;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

public class ItemSpawnEgg extends SpawnEggItem {

    public static final EggDispenseBehavior EGG_BEHAVIOR = new EggDispenseBehavior();
    private final RegisterEntity.RegistryEntry<?> type;

    public ItemSpawnEgg(RegisterEntity.RegistryEntry<?> type, int primaryColorIn, int secondaryColorIn) {
        super(ModEntities.EMPTY, primaryColorIn, secondaryColorIn, new Properties().group(ModGroups.ENTITY));
        this.type = type;
        DispenserBlock.registerDispenseBehavior(this, EGG_BEHAVIOR);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        if (nbt != null && nbt.contains("EntityTag", 10)) {
            CompoundNBT compoundnbt = nbt.getCompound("EntityTag");
            if (compoundnbt.contains("id", 8)) {
                return EntityType.byKey(compoundnbt.getString("id")).orElseGet(type::getType);
            }
        }

        return type.getType();
    }

    public static class EggDispenseBehavior extends DefaultDispenseItemBehavior {

        @Override
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            Direction direction = source.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
            entityType.spawn(source.getWorld(), stack, null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
            stack.shrink(1);
            return stack;
        }
    }
}
