package alantheknight.lab6.client;

import alantheknight.lab6.common.network.Request;
import alantheknight.lab6.common.network.Response;
import alantheknight.lab6.common.network.UDPShared;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;

import static org.apache.commons.lang3.SerializationUtils.deserialize;
import static org.apache.commons.lang3.SerializationUtils.serialize;

public class UDPClient extends UDPShared {
    DatagramSocket socket;

    public UDPClient() {
        super();
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendChunk(byte[] data) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, address);
            socket.send(packet);
        } catch (Exception e) {
            System.err.println("Failed to send chunk: " + e.getMessage());
        }
    }

    public void sendRequest(Request request) {
        byte[] requestData = serialize(request);
        var header = getDataSizeHeader(requestData.length);
        sendChunk(header);
        sendChunk(requestData);
    }

    public <T> Response<T> receiveResponse() {
        try {
            System.out.println("Waiting for response...");

            byte[] header = new byte[Integer.BYTES];
            DatagramPacket packet = new DatagramPacket(header, header.length, address);
            socket.receive(packet);
            int dataSize = readDataSizeHeader(header);
            System.out.println("Received data size: " + dataSize);

            ByteBuffer buffer = ByteBuffer.allocate(dataSize);
            while (buffer.hasRemaining()) {
                packet = new DatagramPacket(buffer.array(), buffer.position(), buffer.remaining(), address);
                socket.receive(packet);
                buffer.position(buffer.position() + packet.getLength());
            }

            return deserialize(buffer.array());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> Response<T> sendCommand(Request request) {
        sendRequest(request);
        return receiveResponse();
    }
}