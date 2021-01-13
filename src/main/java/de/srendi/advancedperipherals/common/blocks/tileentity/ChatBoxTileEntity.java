package de.srendi.advancedperipherals.common.blocks.tileentity;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import de.srendi.advancedperipherals.AdvancedPeripherals;
import de.srendi.advancedperipherals.common.addons.computercraft.peripheral.ChatBoxPeripheral;
import de.srendi.advancedperipherals.common.setup.ModTileEntityTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;

@Mod.EventBusSubscriber(modid = AdvancedPeripherals.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChatBoxTileEntity extends TileEntity implements ITickableTileEntity {

    public ChatBoxTileEntity() {
        this(ModTileEntityTypes.CHAT_BOX.get());
    }

    public ChatBoxTileEntity(final TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    private ChatBoxPeripheral peripheral;
    private LazyOptional<IPeripheral> peripheralCap;

    private int tick;

    public List<IComputerAccess> getConnectedComputers() {
        return peripheral.getConnectedComputers();
    }

    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        if (cap == CAPABILITY_PERIPHERAL) {
            if (peripheralCap == null) {
                peripheralCap = LazyOptional.of(()->peripheral);
            }
            return peripheralCap.cast();
        }

        return super.getCapability(cap, direction);
    }

    @Override
    public void validate() {
        peripheral = new ChatBoxPeripheral("chatBox");
        super.validate();
    }

    @Override
    public void tick() {
        if(peripheral.getTick() == 0) {
            tick = 0;
        }
        tick++;
        peripheral.setTick(tick);
    }
}