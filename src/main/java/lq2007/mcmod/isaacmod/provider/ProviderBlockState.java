package lq2007.mcmod.isaacmod.provider;

import lq2007.mcmod.isaacmod.Isaac;
import lq2007.mcmod.isaacmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiFunction;

public class ProviderBlockState extends BlockStateProvider {

    public ProviderBlockState(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Isaac.ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.FOUNDATION.get()).partialState().addModels();
    }

    protected void obj(Block block, String obj) {
        BlockModelBuilder model = models().getBuilder(block.getRegistryName().getPath())
                .customLoader((BiFunction<BlockModelBuilder, ExistingFileHelper, CustomLoaderBuilder<BlockModelBuilder>>) (blockModelBuilder, helper) -> OBJLoaderBuilder.begin(blockModelBuilder, helper).modelLocation(modLoc(obj)).flipV(true))
                .end();
        getVariantBuilder(block).partialState()
                .addModels();
    }
}
