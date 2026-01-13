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
					.then(ClientCommandManager.literal("enabled")
							.then(ClientCommandManager.literal("on").executes(context -> {
								CONFIG.enabled = true;
								CONFIG.saveJson();
								context.getSource().sendFeedback(Text.literal("On"));
								return 0;
							}))
							.then(ClientCommandManager.literal("off").executes(context -> {
								CONFIG.enabled = false;
								CONFIG.saveJson();
								context.getSource().sendFeedback(Text.literal("Off"));
								return 0;
							}))
					)
					.then(ClientCommandManager.literal("playerlist")
									.then(ClientCommandManager.literal("add")
											.then(ClientCommandManager.argument("nickname", com.mojang.brigadier.arguments.StringArgumentType.word())
													.executes(context -> {
														String name = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "nickname");
														if (!CONFIG.friends.contains(name)) {
															CONFIG.friends.add(name);
															CONFIG.saveJson();
															context.getSource().sendFeedback(Text.literal("§a" + name + " added to the player list."));
														}
														return 1;
													})))
									.then(ClientCommandManager.literal("remove")
											.then(ClientCommandManager.argument("nickname", com.mojang.brigadier.arguments.StringArgumentType.word())
													.executes(context -> {
														String name = com.mojang.brigadier.arguments.StringArgumentType.getString(context, "nickname");
														if (CONFIG.friends.remove(name)) {
															CONFIG.saveJson();
															context.getSource().sendFeedback(Text.literal("§c" + name + " Deleted from the player list."));
														}
														return 1;
													})))
									.then(ClientCommandManager.literal("list").executes(context -> {
										context.getSource().sendFeedback(Text.literal("Player list: " + CONFIG.friends.toString()));

										return 0;
									}))
					)
			);
		});
		ChosablePacifism.LOGGER.info("Hello fuckingjava!");
	}
}