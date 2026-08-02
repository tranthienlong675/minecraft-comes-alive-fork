package net.conczin.mca.network.c2s;

import net.conczin.mca.MCA;
import net.conczin.mca.entity.VillagerEntityMCA;
import net.conczin.mca.network.HandleablePayload;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public record VillagerRevealRequest(UUID uuid) implements HandleablePayload {
    public static final CustomPacketPayload.Type<VillagerRevealRequest> TYPE = new CustomPacketPayload.Type<>(MCA.locate("villager_reveal_request"));
    public static final StreamCodec<FriendlyByteBuf, VillagerRevealRequest> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, VillagerRevealRequest::uuid,
            VillagerRevealRequest::new
    );

    @Override
    public void handleServer(ServerPlayer player) {
        Entity entity = player.serverLevel().getEntity(uuid);

        if (entity instanceof VillagerEntityMCA villager) {
            villager.setNoAi(false);
            villager.setHidden(false);
        }
    }

    @Override
    public CustomPacketPayload.Type<VillagerRevealRequest> type() {
        return TYPE;
    }
}
