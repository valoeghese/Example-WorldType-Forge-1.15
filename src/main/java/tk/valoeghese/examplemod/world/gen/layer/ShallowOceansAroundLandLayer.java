package tk.valoeghese.examplemod.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;
import tk.valoeghese.examplemod.world.gen.ExampleBiomeLayers;

public enum ShallowOceansAroundLandLayer implements ICastleTransformer {
	INSTANCE;

	@Override
	public int apply(INoiseRandom rand, int n, int e, int s, int w, int centre) {
		if (ExampleBiomeLayers.isDeepOcean(centre)) {
			if (!ExampleBiomeLayers.isOcean(n) || !ExampleBiomeLayers.isOcean(e) || !ExampleBiomeLayers.isOcean(s) || !ExampleBiomeLayers.isOcean(w)) {
				return ExampleBiomeLayers.OCEAN;
			}
		}

		return centre;
	}

}
