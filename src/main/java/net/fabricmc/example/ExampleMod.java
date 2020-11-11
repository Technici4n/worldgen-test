package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ExampleMod implements ModInitializer {
	@Override
	public void onInitialize() {

		Block block = Blocks.DIAMOND_BLOCK;
		ConfiguredFeature<?, ?> oreGenerator = Feature.ORE
				.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, block.getDefaultState(),
						15)) // vein size
				.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, // bottom offset
						0, // min y level
						30))) // max y level
				.spreadHorizontally().repeat(120); // number of veins per chunk
		Identifier oregenId = new Identifier("worldgen:ore_generator_diamond_blocks");
		RegistryKey<ConfiguredFeature<?, ?>> featureKey = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, oregenId);
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, featureKey.getValue(), oreGenerator);
		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, featureKey);
	}
}
