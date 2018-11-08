package com.github.maxopoly.angeliacore.connection.play.packets.in;

import com.github.maxopoly.angeliacore.binary.EndOfPacketException;
import com.github.maxopoly.angeliacore.binary.ReadOnlyPacket;
import com.github.maxopoly.angeliacore.block.Chunk;
import com.github.maxopoly.angeliacore.connection.ServerConnection;
import com.github.maxopoly.angeliacore.event.events.ChunkUnloadEvent;

public class UnloadChunkPacketHandler extends AbstractIncomingPacketHandler {

	public UnloadChunkPacketHandler(ServerConnection connection) {
		super(connection, 0x1D);
	}

	@Override
	public void handlePacket(ReadOnlyPacket packet) {
		try {
			int x = packet.readInt();
			int z = packet.readInt();
			Chunk chunk = connection.getChunkHolder().getChunk(x, z);
			connection.getEventHandler().broadcast(new ChunkUnloadEvent(chunk));
			connection.getChunkHolder().removeChunk(x, z);
		} catch (EndOfPacketException e) {
			connection.getLogger().error("Failed to parse break animation packet", e);
		}
	}

}