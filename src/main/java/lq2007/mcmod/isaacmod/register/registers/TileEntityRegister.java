package lq2007.mcmod.isaacmod.register.registers;

import lq2007.mcmod.isaacmod.register.ObjectConstructor;
import lq2007.mcmod.isaacmod.register.Register;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.annotation.Nullable;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class TileEntityRegister extends BaseDeferredRegister<TileEntityType<?>, TileEntity> {

    private final Function<Class<? extends TileEntity>, Block[]> blockSupplier;

    public TileEntityRegister(Register context, String packageName, Function<Class<? extends TileEntity>, Block[]> blockSupplier) {
        super(ForgeRegistries.TILE_ENTITIES, context, TileEntity.class, packageName);
        this.blockSupplier = blockSupplier;
    }

    @Nullable
    @Override
    protected Supplier<? extends TileEntityType<?>> build(String name, Class<? extends TileEntity> aClass) throws Exception {
        Supplier<? extends TileEntity> supplier = new ObjectConstructor<>(aClass);
        return () -> TileEntityType.Builder.create(supplier, blockSupplier.apply(aClass)).build(null);
    }
}
