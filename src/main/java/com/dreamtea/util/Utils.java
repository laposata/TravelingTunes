package com.dreamtea.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.structure.Structure;

public class Utils {
  public static boolean inStructure(ServerWorld world, BlockPos pos, RegistryKey<Structure> structure){
    return world.getStructureAccessor().getStructureContaining(pos, structure).hasChildren();
  }

  public static StructureStart inWhichStructure(ServerWorld world, BlockPos pos, RegistryKey<Structure> structure){
    return world.getStructureAccessor().getStructureContaining(pos, structure);
  }

  public static boolean inStructurePiece(BlockPos pos, StructureStart structure) {
    return structure.getBoundingBox().contains(pos) && structure.getChildren().stream().anyMatch(piece -> piece.getBoundingBox().contains(pos));
  }

}
