package cc.reconnected.client.updateForcer;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UpdateForcerPacket {
    public static final Identifier ID = Identifier.of("rcc-client","force_update");

    public static PacketByteBuf serialize(Text message) {
        PacketByteBuf buf = new PacketByteBuf(io.netty.buffer.Unpooled.buffer());
        buf.writeText(message);
        return buf;
    }

    public static Text readForceUpdateMessage(PacketByteBuf buf) {
        return buf.readText();
    }

}
