package alantheknight.lab6.server;

import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.network.UDPShared;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

import static alantheknight.lab6.server.Main.commandManager;
import static alantheknight.lab6.server.Main.logger;
import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;

/**
 * A UDP server that handles incoming requests from clients.
 */
public class UDPServer extends UDPShared {
    /**
     * A map of selection keys to byte buffers, where keys are used to identify
     * the client and buffers are used to store the incoming data.
     */
    private static final Map<SelectionKey, ByteBuffer> buffers = new HashMap<>();

    public UDPServer() {
        super();
    }

    /**
     * Starts the UDP server and begins listening for incoming requests.
     */
    public void start() {
        try (Selector selector = Selector.open(); DatagramChannel channel = createChannel()) {
            logger.log(Level.INFO, "Server started on " + channel.getLocalAddress());
            channel.register(selector, SelectionKey.OP_READ);

            while (true) {
                selector.select();
                processSelectedKeys(selector.selectedKeys());
            }
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }

    /**
     * Processes the selected keys returned by the selector.
     *
     * @param selectedKeys The set of selected keys.
     * @throws IOException If an I/O error occurs.
     */
    private void processSelectedKeys(Set<SelectionKey> selectedKeys) throws IOException {
        Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

        while (keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            keyIterator.remove();

            if (key.isReadable()) {
                handleRead(key);
            }
        }
    }

    /**
     * Handles read events for the given selection key.
     *
     * @param key The selection key to handle.
     * @throws IOException If an I/O error occurs.
     */
    private void handleRead(SelectionKey key) throws IOException {
        DatagramChannel selectedChannel = (DatagramChannel) key.channel();

        if (!buffers.containsKey(key)) {
            handleHeaderRead(key, selectedChannel);
        } else {
            handleDataRead(key, selectedChannel);
        }
    }

    /**
     * Handles reading the header data for the given selection key.
     *
     * @param key             The selection key.
     * @param selectedChannel The DatagramChannel associated with the key.
     * @throws IOException If an I/O error occurs.
     */
    private void handleHeaderRead(SelectionKey key, DatagramChannel selectedChannel) throws IOException {
        ByteBuffer headerBuffer = ByteBuffer.allocate(Integer.BYTES);
        InetSocketAddress clientAddress = (InetSocketAddress) selectedChannel.receive(headerBuffer);

        if (clientAddress != null) {
            logger.log(Level.INFO, "Received header from client at " + clientAddress);

            headerBuffer.flip();
            final int dataSize = headerBuffer.getInt();
            headerBuffer.clear();

            ByteBuffer buffer = ByteBuffer.allocate(dataSize);
            buffers.put(key, buffer);

            logger.log(Level.DEBUG, "Expecting data of size " + dataSize + " bytes from client at " + clientAddress);
        }
    }

    /**
     * Handles reading the data for the given selection key.
     *
     * @param key             The selection key.
     * @param selectedChannel The DatagramChannel associated with the key.
     * @throws IOException If an I/O error occurs.
     */
    private void handleDataRead(SelectionKey key, DatagramChannel selectedChannel) throws IOException {
        ByteBuffer buffer = buffers.get(key);
        InetSocketAddress clientAddress = (InetSocketAddress) selectedChannel.receive(buffer);

        if (clientAddress != null) {
            logger.log(Level.INFO, "Received data from client at " + clientAddress);

            buffer.flip();
            Request request = deserialize(buffer.array());
            buffer.clear();

            logger.log(Level.INFO, "Executing command: " + request.getCommand());

            var response = commandManager.getCommand(request.getCommand()).execute(request);

            sendResponse(selectedChannel, clientAddress, response);

            buffers.remove(key);
        }
    }

    private void sendResponse(DatagramChannel channel, InetSocketAddress clientAddress, Response<?> response) throws IOException {
        byte[] responseData = serialize(response);

        // Send the header containing the size of the response data.
        byte[] header = new byte[Integer.BYTES];
        ByteBuffer.wrap(header).putInt(responseData.length);
        channel.send(ByteBuffer.wrap(header), clientAddress);

        // Split response data into chunks of size bufferSize and
        // send chunks to the client.
        var bufferSize = config.bufferSize();
        for (int i = 0; i < responseData.length; i += bufferSize) {
            var chunk = Arrays.copyOfRange(responseData, i, Math.min(i + bufferSize, responseData.length));
            channel.send(ByteBuffer.wrap(chunk), clientAddress);
        }

        logger.log(Level.INFO, "Sent response of " + (int) Math.ceil((double) responseData.length / bufferSize) + " chunks to client at " + clientAddress);
    }

    /**
     * Creates and configures a DatagramChannel for the server.
     *
     * @return The configured DatagramChannel.
     * @throws IOException If an I/O error occurs.
     */
    private DatagramChannel createChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(address);
        datagramChannel.configureBlocking(false);
        return datagramChannel;
    }
}
