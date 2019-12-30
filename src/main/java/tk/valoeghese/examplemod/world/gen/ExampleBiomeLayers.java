package tk.valoeghese.examplemod.world.gen;

import java.util.function.LongFunction;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.DeepOceanLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.ShoreLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import tk.valoeghese.examplemod.world.gen.layer.AddExtraIslandsLayer;
import tk.valoeghese.examplemod.world.gen.layer.InitialLandOceanLayer;

// Vanilla registries are deprecated in forge, but we need to use them to get the raw int ids for biome layers
@SuppressWarnings("deprecation")
public final class ExampleBiomeLayers {
	private static <A extends IArea, R extends IExtendedNoiseRandom<A>> IAreaFactory<A> stackLayers(LongFunction<R> randomProvider) {
		// Initial land/sea, with random biomes
		IAreaFactory<A> factory = new InitialLandOceanLayer(8).apply(randomProvider.apply(1L));

		// Add deep ocean and more land with random biomes
		for (int i = 0; i < extraIslands; ++i) {
			factory = DeepOceanLayer.INSTANCE.apply(randomProvider.apply(2L + i), factory);
			factory = AddExtraIslandsLayer.INSTANCE.apply(randomProvider.apply(i), factory);
			factory = ZoomLayer.NORMAL.apply(randomProvider.apply(100L + 2 * i), factory);
			factory = ZoomLayer.NORMAL.apply(randomProvider.apply(100L + 2 * i + 1), factory);
		}

		factory = ZoomLayer.NORMAL.apply(randomProvider.apply(1000L), factory);
		// Add beaches and small edges
		factory = ShoreLayer.INSTANCE.apply(randomProvider.apply(10L), factory);
		factory = LayerUtil.repeat(1001L, ZoomLayer.NORMAL, factory, islandSizeMin - 1, randomProvider);

		return factory;
	}

	private static final int islandSizeMin = 4;
	private static final int extraIslands = 3;
	private static final int[] randomBiomesArray;

	static Layer build(long seed) {
		return new Layer(stackLayers(salt -> new LazyAreaLayerContext(25, seed, salt)));
	}

	public static boolean isDeepOcean(int biomeId) {
		return biomeId == DEEP_WARM_OCEAN || biomeId == DEEP_LUKEWARM_OCEAN || biomeId == DEEP_OCEAN || biomeId == DEEP_COLD_OCEAN || biomeId == DEEP_FROZEN_OCEAN;
	}

	public static int getRandomBiome(INoiseRandom rand) {
		return randomBiomesArray[rand.random(randomBiomesArray.length)];
	}

	static {
		Biome[] biomes = ExampleBiomeProvider.getBiomeSet().toArray(new Biome[0]);
		randomBiomesArray = new int[biomes.length];

		for (int index = 0; index < biomes.length; ++index) {
			if (biomes[index].getCategory() != Biome.Category.OCEAN) {
				randomBiomesArray[index] = Registry.BIOME.getId(biomes[index]);
			}
		}
	}

	private static final int DEEP_WARM_OCEAN = Registry.BIOME.getId(Biomes.DEEP_WARM_OCEAN);
	private static final int DEEP_LUKEWARM_OCEAN = Registry.BIOME.getId(Biomes.DEEP_LUKEWARM_OCEAN);
	private static final int DEEP_OCEAN = Registry.BIOME.getId(Biomes.DEEP_OCEAN);
	private static final int DEEP_COLD_OCEAN = Registry.BIOME.getId(Biomes.DEEP_COLD_OCEAN);
	private static final int DEEP_FROZEN_OCEAN = Registry.BIOME.getId(Biomes.DEEP_FROZEN_OCEAN);
}
