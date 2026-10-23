package baubles.api.render;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Client-side bauble rendering API. Rendering is not implemented; the type is
 * present so client addons resolve their method descriptors.
 */
public interface IRenderBauble {
    void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks);

    enum RenderType {
        HEAD,
        BODY,
        BELT
    }

    final class Helper {
        private Helper() {
        }

        public static void rotateIfSneaking(EntityPlayer player) {
        }

        public static void translateToHeadLevel(EntityPlayer player) {
        }

        public static void translateToFace() {
        }

        public static void defaultTransforms() {
        }

        public static void translateToChest() {
        }
    }
}
