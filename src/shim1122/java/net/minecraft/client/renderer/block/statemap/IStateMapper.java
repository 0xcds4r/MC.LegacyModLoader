package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;

public interface IStateMapper {
    com.google.common.collect.ImmutableMap<net.minecraft.block.state.IBlockState,
            net.minecraft.client.renderer.block.model.ModelResourceLocation> putStateModelLocations(Block block);
}
