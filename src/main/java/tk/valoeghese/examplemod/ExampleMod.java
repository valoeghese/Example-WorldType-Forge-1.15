package tk.valoeghese.examplemod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tk.valoeghese.examplemod.world.ExampleWorldType;

@Mod("examplemod")
public class ExampleMod {
	private static final Logger LOGGER = LogManager.getLogger();

	public ExampleMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Setting up example world gen mod");
		new ExampleWorldType("example");
	}
}
