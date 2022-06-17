package gui;

import client.Client;
import client.ClientCmdManager;
import commands.AbstractCmd;
import exceptions.ServerUnavailableException;
import stored.City;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.Converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class InnerClient {
    private DatagramChannel channel;

    public InnerClient() throws IOException {
        int port;
        String potentialPort = System.getenv("LAB_PORT");
        try{
            port = Integer.parseInt(potentialPort);
        } catch (Exception ignored){
            port = 50505;
        }
        SERVICE_PORT = port;
        sockaddr = new InetSocketAddress("localhost", SERVICE_PORT);

        channel = DatagramChannel.open();
        channel.bind(null);
        channel.configureBlocking(false);
    }

    public final int SERVICE_PORT;
    public final SocketAddress sockaddr;
    public ArrayList<CmdTemplate> hehCommands = new ArrayList<>();
    public Map<String, AbstractCmd> localCommands = new LinkedHashMap<>();
    public TreeSet<City> collection = new TreeSet<>(
            Comparator.comparing(City::getId)
    );

    private ClientCmdManager clientCmdManager;

    public void requestCommands() throws IOException {
        ByteBuffer fromBuffer = ByteBuffer.allocate(1024 * 8);

        CmdTemplate firstIssue = new CmdTemplate("requestArgs", "", "", null);
        ByteBuffer toBuffer = Converter.convertToBB(firstIssue);

        channel.send(toBuffer, sockaddr);
        waitResponce(channel, fromBuffer);

        try(ByteArrayInputStream baos2 = new ByteArrayInputStream(fromBuffer.array());
            ObjectInputStream oos2 = new ObjectInputStream(baos2);){
            Responce c1 = (Responce) oos2.readObject();
            if (c1.message.equals("Your commands")){
                hehCommands.addAll(c1.arr);
            }
        }catch (Exception e){
            throw new ServerUnavailableException("Can't load commands, try again later.");
        }
    }

    public void requestObjects() throws IOException {
        ByteBuffer fromBuffer = ByteBuffer.allocate(1024 * 16);

        CmdTemplate secondIssue = new CmdTemplate("requestObjects", "", "", null);
        ByteBuffer toBuffer = Converter.convertToBB(secondIssue);

        channel.send(toBuffer, sockaddr);
        waitResponce(channel, fromBuffer);

        try(ByteArrayInputStream baos2 = new ByteArrayInputStream(fromBuffer.array());
            ObjectInputStream oos2 = new ObjectInputStream(baos2);){
            Responce c1 = (Responce) oos2.readObject();
            if (c1.message.equals("Your objects") && c1.obj != null && c1.obj.size()>0){
                collection.clear();
                collection.addAll(c1.obj);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServerUnavailableException("Can't load objects, try again later.");
        }
    }

    public void waitResponce(DatagramChannel channel, ByteBuffer buffer) throws IOException {
        long time = System.currentTimeMillis();
        SocketAddress adr;

        while (System.currentTimeMillis() - time < 3000) {
            adr = channel.receive(buffer);
            if (adr != null){
                return;
            }
        }
        throw new ServerUnavailableException("Can't connect to server for 3 seconds");

    }

    public boolean sendCommandAndCheckAnswer(CmdTemplate cmd, ByteBuffer fromBuffer) throws IOException, ClassNotFoundException{
        ByteBuffer buffer = Converter.convertToBB(cmd);
        channel.send(buffer, sockaddr);
        waitResponce(channel, fromBuffer);
        try (ByteArrayInputStream baos = new ByteArrayInputStream(fromBuffer.array());
             ObjectInputStream oos = new ObjectInputStream(baos);){
            Responce resp = (Responce) oos.readObject();
            if (resp.isError){
                System.err.println(resp.message);
            } else {
                System.out.println(resp.message);
            }
            return (!resp.isError) && resp.message.startsWith("Successfully");
        }finally {
            buffer.clear();
            fromBuffer.clear();
        }

    }

    public Responce sendCommandAndGetAnswer(CmdTemplate cmd, ByteBuffer fromBuffer) throws IOException, ClassNotFoundException{
        ByteBuffer buffer = Converter.convertToBB(cmd);
        channel.send(buffer, sockaddr);
        waitResponce(channel, fromBuffer);
        try (ByteArrayInputStream baos = new ByteArrayInputStream(fromBuffer.array());
             ObjectInputStream oos = new ObjectInputStream(baos);){
            return (Responce) oos.readObject();
        }
    }

}