package client;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

import exceptions.CommandExecutionException;
import exceptions.ServerUnavailableException;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.Converter;
import utils.InputProcessor;
import utils.ManagerFiller;

public class Client{
    /* Порт сервера, к которому собирается
  подключиться клиентский сокет */
    public final static int SERVICE_PORT = 50505;
    public final static SocketAddress sockaddr = new InetSocketAddress("localhost", SERVICE_PORT);
    public static ArrayList<CmdTemplate> hehCommands = new ArrayList<>();

    public static void waitResponce(DatagramChannel channel, ByteBuffer buffer) throws IOException {
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

    public static void main(String[] args) throws IOException{

// TODO: 03.05.2022 execute_script and exit 

        try (DatagramChannel channel = DatagramChannel.open();){

//            channel.socket().setSoTimeout(3000);
            channel.bind(null);
            channel.configureBlocking(false);
            ByteBuffer fromBuffer = ByteBuffer.allocate(1024 * 8);

            CmdTemplate firstIssue = new CmdTemplate("requestArgs", "", "", null);
            ByteBuffer toBuffer = Converter.convertToBB(firstIssue);

            channel.send(toBuffer, Client.sockaddr);
            waitResponce(channel, fromBuffer);
//            channel.receive(fromBuffer);

            try(ByteArrayInputStream baos2 = new ByteArrayInputStream(fromBuffer.array());
                ObjectInputStream oos2 = new ObjectInputStream(baos2);){
                Responce c1 = (Responce) oos2.readObject();
                if (c1.message.equals("Your commands")){
                    hehCommands.addAll(c1.arr);
                }
            }catch (Exception e){
                System.err.println("Can't load commands, try again later.");
                System.exit(1);
            }

            fromBuffer.clear();

            ClientCmdManager cm = new ClientCmdManager(false);
            ManagerFiller.fillCommandManager(cm);

            System.out.println("Contact is here!");


            while (true) {
                String command = InputProcessor.inputString();
                try {
                    CmdTemplate cmd = cm.processCommand(command);
                    ByteBuffer buffer = Converter.convertToBB(cmd);
                    channel.send(buffer, Client.sockaddr);
                    waitResponce(channel, fromBuffer);
                    try (ByteArrayInputStream baos = new ByteArrayInputStream(fromBuffer.array());
                         ObjectInputStream oos = new ObjectInputStream(baos);){
                        Responce resp = (Responce) oos.readObject();
                        if (resp.isError){
                            System.err.println(resp.message);
                        } else {
                            System.out.println(resp.message);
                        }
                    }
                    buffer.clear();
                    fromBuffer.clear();

                }catch (CommandExecutionException e){
                    System.err.println(e.getMessage());
                }
            }


        }
        catch (ServerUnavailableException e){
            System.err.println(e.getMessage());
        }
        catch (java.io.EOFException e){
            System.err.println("Buffer overflow!");
        }
        catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            System.err.println("\nShutting down)");
            e.printStackTrace();
        }
    }
}
