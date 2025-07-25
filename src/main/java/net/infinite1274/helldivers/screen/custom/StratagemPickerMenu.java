package net.infinite1274.helldivers.screen.custom;

import net.infinite1274.helldivers.item.ModItems;
import net.infinite1274.helldivers.item.inventory.StratagemPickerInventory;
import net.infinite1274.helldivers.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StratagemPickerMenu extends AbstractContainerMenu {
    private final StratagemPickerInventory inventory;
    private final ItemStack stack;

    // Constructor for server-side
    public StratagemPickerMenu(int containerId, Inventory playerInventory, ItemStack stack) {
        super(ModMenuTypes.STRATAGEM_PICKER.get(), containerId);
        this.stack = stack;
        this.inventory = new StratagemPickerInventory(stack, StratagemPickerInventory.INVENTORY_SIZE);

        // Add the stratagem inventory slots
        for (int i = 0; i < StratagemPickerInventory.INVENTORY_SIZE; i++) {
            this.addSlot(new Slot(inventory, i, 44 + i * 34, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return inventory.isStratagemItem(stack);
                }
            });
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    // Constructor for client-side
    public StratagemPickerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        super(ModMenuTypes.STRATAGEM_PICKER.get(), containerId);
        this.stack = extraData.readItem();
        this.inventory = new StratagemPickerInventory(this.stack, StratagemPickerInventory.INVENTORY_SIZE);

        // Add the stratagem inventory slots
        for (int i = 0; i < StratagemPickerInventory.INVENTORY_SIZE; i++) {
            this.addSlot(new Slot(inventory, i, 44 + i * 24, 35));
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }



    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        boolean hasStratagemPicker = player.getMainHandItem().is(ModItems.STRATAGEM_PICKER.get()) ||
                player.getOffhandItem().is(ModItems.STRATAGEM_PICKER.get());

        return hasStratagemPicker && !stack.isEmpty();
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        // Only allow stratagem items to be placed in the stratagem slots
        if (slot.container == inventory) {
            return inventory.isStratagemItem(stack);
        }
        return super.canTakeItemForPickAll(stack, slot);
    }
}
