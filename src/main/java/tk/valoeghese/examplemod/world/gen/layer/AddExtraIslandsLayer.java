package tk.valoeghese.examplemod.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import tk.valoeghese.examplemod.world.gen.ExampleBiomeLayers;

public enum AddExtraIslandsLayer implements ICastleTransformer {
	INSTANCE;

	@Override
	public int apply(INoiseRandom rand, int n, int e, int s, int w, int centre) {
		if (ExampleBiomeLayers.isDeepOcean(centre)) {
			if (ExampleBiomeLayers.isDeepOcean(n) && ExampleBiomeLayers.isDeepOcean(e) && ExampleBiomeLayers.isDeepOcean(s) && ExampleBiomeLayers.isDeepOcean(w)) {
				if (rand.random(8) == 0) {
					return ExampleBiomeLayers.getRandomBiome(rand);
				}
			}
		}

		return centre;
	}

}
