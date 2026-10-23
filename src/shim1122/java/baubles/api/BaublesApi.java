package baubles.api;

import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * API facade. Handlers are empty for now; amulets/rings are not equipped and do
 * not tick. Mods that only query the API during registration keep working.
 */
public final class BaublesApi {
    private BaublesApi() {
    }

    public static IBaublesItemHandler getBaublesHandler(EntityPlayer player) {
        return EmptyHandler.INSTANCE;
    }

    public static IBaublesItemHandler getBaublesHandler(EntityPlayer player, int slot) {
        return EmptyHandler.INSTANCE;
    }

    private enum EmptyHandler implements IBaublesItemHandler {
        INSTANCE;

        @Override
        public int getSlots() {
            return 0;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return ItemStack.EMPTY;
        }

        @Override
        public void setStackInSlot(int slot, ItemStack stack) {
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 0;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public boolean isItemValidForSlot(int slot, ItemStack stack) {
            return false;
        }
    }
}
