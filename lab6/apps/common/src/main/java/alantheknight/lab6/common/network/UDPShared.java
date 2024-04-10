package alantheknight.lab6.common.network;

import alantheknight.lab6.common.utils.Config;
import alantheknight.lab6.common.utils.ConfigReader;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * Base class for UDP client and server.
 */
public abstract class UDPShared {
    /**
     * The configuration.
     */
    protected final Config config;
    /**
     * The address.
     */
    protected final InetSocketAddress address;

    public UDPShared() {
        this.config = ConfigReader.getConfig();
        this.address = new InetSocketAddress("localhost", config.port());
    }

    protected byte[] getDataSizeHeader(Integer dataSize) {
        byte[] header = new byte[Integer.BYTES];
        ByteBuffer.wrap(header).putInt(dataSize);
        return header;
    }

    protected Integer readDataSizeHeader(byte[] header) {
        return ByteBuffer.wrap(header).getInt();
    }
}
