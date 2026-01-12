package yud0o0.main;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import yud0o0.main.config.CpacifismConfigClass;

public class ChosablePacifismClient implements ClientModInitializer {
	public static final CpacifismConfigClass CONFIG = new CpacifismConfigClass("config/ChoosablePacifism.json");
	@Override
	public void onInitializeClient() {
		CONFIG.loadOrCreate();
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("cpacifism")
					.then(ClientCommandManager.literal("entity")
							.then(ClientCommandManager.literal("on").executes(context -> {
								CONFIG.enabled = true;
								CONFIG.saveJson();
								context.getSource().sendFeedback(Text.literal("включено"));
								return 0;
							}))
							.then(ClientCommandManager.literal("off").executes(context -> {
								CONFIG.enabled = false;
								CONFIG.saveJson();
								context.getSource().sendFeedback(Text.literal("выключено"));
								return 0;
							}))
							));
			});
		ChosablePacifism.LOGGER.info("Hello fuckingjava!");
	}
}