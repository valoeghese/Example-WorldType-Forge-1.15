package tk.valoeghese.examplemod.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraftforge.common.extensions.IForgeWorldType;
import tk.valoeghese.examplemod.world.gen.ExampleBiomeProvider;

public class ExampleWorldType extends WorldType implements IForgeWorldType {
	public ExampleWorldType(String name) {
		super(name);
	}

	@Override
	public ChunkGenerator<?> createChunkGenerator(World world) {
		return ChunkGeneratorType.SURFACE.create(world, new ExampleBiomeProvider(world.getSeed()), new OverworldGenSettings());
	}
}
