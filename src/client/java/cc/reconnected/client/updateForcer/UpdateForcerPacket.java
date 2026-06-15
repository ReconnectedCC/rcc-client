package cc.reconnected.client.updateForcer;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record UpdateForcerPacket(String message)  implements CustomPayload {
    public static final Identifier ID = Identifier.of("rcc-client","force_update");
    public static final CustomPayload.Id<UpdateForcerPacket> TYPE = new CustomPayload.Id<>(ID);
    public static final PacketCodec<PacketByteBuf,UpdateForcerPacket> CODEC = PacketCodec.tuple(PacketCodecs.STRING,UpdateForcerPacket::message,UpdateForcerPacket::new);



    public static Text readForceUpdateMessage(UpdateForcerPacket packet, RegistryWrapper.WrapperLookup lookup) {
        return Text.Serialization.fromJson(packet.message,lookup);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return TYPE;
    }
}
