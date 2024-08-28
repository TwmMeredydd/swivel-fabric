package twmmeredydd.swivel.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwivelClient implements ClientModInitializer {
	public static final String MOD_ID = "swivel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyMapping swivelLeft;
	private static KeyMapping swivelRight;
	private static KeyMapping swivelUp;
	private static KeyMapping swivelDown;
	private static KeyMapping zeroX;
	private static KeyMapping zeroY;

	@Override
	public void onInitializeClient() {
		swivelLeft = registerKeyBind("swivel_left", InputConstants.Type.KEYSYM, InputConstants.KEY_LEFT);
		swivelRight = registerKeyBind("swivel_right", InputConstants.Type.KEYSYM, InputConstants.KEY_RIGHT);
		swivelUp = registerKeyBind("swivel_up", InputConstants.Type.KEYSYM, InputConstants.KEY_UP);
		swivelDown = registerKeyBind("swivel_down", InputConstants.Type.KEYSYM, InputConstants.KEY_DOWN);
		zeroX = registerKeyBind("zero_x", InputConstants.Type.KEYSYM, InputConstants.KEY_LBRACKET);
		zeroY = registerKeyBind("zero_y", InputConstants.Type.KEYSYM, InputConstants.KEY_RBRACKET);

		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
	}

	private void tick(Minecraft client) {
		while (swivelLeft.consumeClick()) {
			client.player.turn(-300, 0);
		}
		while (swivelRight.consumeClick()) {
			client.player.turn(300, 0);
		}
		while (swivelUp.consumeClick()) {
			client.player.turn(0, -300);
		}
		while (swivelDown.consumeClick()) {
			client.player.turn(0, 300);
		}
		while (zeroX.consumeClick()) {
			client.player.turn(0, -client.player.getXRot()/0.15);
		}
		while (zeroY.consumeClick()) {
			client.player.turn(-client.player.getYRot()/0.15, 0);
		}
	}

	private static KeyMapping registerKeyBind(String name, InputConstants.Type inputType, int defaultKeyCode) {
		return KeyBindingHelper.registerKeyBinding(new KeyMapping(MOD_ID + ".key." + name, inputType, defaultKeyCode, MOD_ID + ".key.category"));
	}
}