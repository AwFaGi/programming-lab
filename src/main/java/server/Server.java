package server;
import commands.AbstractCmd;
import exceptions.CommandExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.CollectionManager;
import utils.Converter;
import utils.ManagerFiller;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Server{
    public final static int SERVICE_PORT = 50505;
    public final static SocketAddress sockaddr = new InetSocketAddress("localhost", SERVICE_PORT);
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    public static void main(String[] args) throws IOException{

        // TODO: 03.05.2022 save & exit 
        
        LOGGER.info("Initialize");
        ServerCmdManager cm = new ServerCmdManager();
        ManagerFiller.fillCommandManager(cm);

        String filepath = System.getenv("COLLECTION");
        CollectionManager.getInstance().attachFile(filepath);
        CollectionManager.getInstance().importJSON();

        try(DatagramChannel channel = DatagramChannel.open().bind(sockaddr);) {
            channel.configureBlocking(false);
            LOGGER.info("Server started at " + sockaddr);
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            SocketAddress adr;
            Scanner sc = new Scanner(System.in);
            while (true) {
                if (System.in.available() > 0) {
                    String line = sc.nextLine();
                    System.out.println(line);
                }

                adr = channel.receive(buffer);

                if (adr != null) {
                    Responce res;
                    try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                         ObjectInputStream ois = new ObjectInputStream(bais);) {

                        CmdTemplate c1 = (CmdTemplate) ois.readObject();
                        LOGGER.debug(adr + " " + c1.name + " " + c1.args);

                        if (c1.name.equals("requestArgs")) {

                            res = new Responce(false, "Your commands",
                                    cm.getCommands().stream()
                                    .map(AbstractCmd::getTemplate)
                                    .collect(Collectors.toList()));
                        }
                        else {
                            res = new Responce(false, cm.processCommand(c1), null);
                        }

                    } catch (CommandExecutionException e) {
                        res = new Responce(true, e.getMessage(), null);
                        LOGGER.error(e.getMessage());
                    }

                    buffer = Converter.convertToBB(res);
                    channel.send(buffer, adr);
                    buffer.clear();
                    adr = null;
                }


            }

        }
        catch (SocketException | ClassNotFoundException e){
            LOGGER.error(e);
//            e.printStackTrace();
        }
    }
}
