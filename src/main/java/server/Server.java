package server;
import commands.AbstractCmd;
import exceptions.CommandExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.CollectionManager;
import utils.Converter;
import utils.DBManager;
import utils.ManagerFiller;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * class for server running: work with collection
 */
public class Server{
    public final static int SERVICE_PORT = 50505;
    public final static SocketAddress sockaddr = new InetSocketAddress("localhost", SERVICE_PORT);
    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private static final ExecutorService cachedPool = Executors.newCachedThreadPool();
//    private static final ExecutorService cachedPool = new ThreadPoolExecutor(0, 2048,
//        2L, TimeUnit.SECONDS,
//        new SynchronousQueue<Runnable>());
    private static final ExecutorService fixedPool = Executors.newFixedThreadPool(10);

    private static ServerCmdManager cm;

    public static void main(String[] args) throws IOException{
        
        LOGGER.info("Initialize");

        cm = new ServerCmdManager();
        ManagerFiller.fillCommandManager(cm);

//        String filepath = System.getenv("COLLECTION");
//        CollectionManager.getInstance().attachFile(filepath);
//        CollectionManager.getInstance().importJSON();

        DBManager s = DBManager.getInstance();
        CollectionManager smt = CollectionManager.getInstance();
//            smt.insertCity(CollectionManager.getInstance().getMin());
        LOGGER.info("\n" + CollectionManager.getInstance().getInfo());


        try(DatagramChannel channel = DatagramChannel.open().bind(sockaddr);) {
            channel.configureBlocking(false);
            LOGGER.info("Server started at " + sockaddr);
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
//            ByteBuffer buffer = ByteBuffer.allocate(1024);
//            SocketAddress adr;
            Scanner sc = new Scanner(System.in);
            while (true) {
                if (System.in.available() > 0) {
                    String line = sc.nextLine();
                    switch (line){
//                        case "save":
//                            CollectionManager.getInstance().toJSON();
//                            LOGGER.info("Saved!");
//                            break;
                        case "exit":
//                            CollectionManager.getInstance().toJSON();
                            LOGGER.info("Shutting down.");
                            System.exit(0);
                            break;
                        default:
                            LOGGER.error("I don't know '" + line + "'. I can only 'save' and 'exit'");
                    }
                }

//                adr = channel.receive(buffer);
                Runnable task = () -> listen(channel, ByteBuffer.allocate(1024*4));
                cachedPool.submit(task);
                Thread.sleep(100);
            }

        }
        catch (SocketException e){
            LOGGER.error(e);
//            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void listen (DatagramChannel channel, ByteBuffer buffer){
        try {
            SocketAddress adr = channel.receive(buffer);
            if (adr != null) {
                Runnable task = () -> process(buffer, adr, channel);
                fixedPool.submit(task);
            }
        }catch (IOException e){
            LOGGER.error(e.getMessage());
        }
    }

    private static void process(ByteBuffer buffer, SocketAddress adr, DatagramChannel channel){
        Responce res;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais);) {

            CmdTemplate c1 = (CmdTemplate) ois.readObject();
            LOGGER.debug(adr + " " + c1.getUsername() + " " + c1.name + " " + c1.args);

            switch (c1.name){
                case "requestArgs":
                    res = new Responce(false, "Your commands",
                            cm.getCommands().stream()
                                    .map(AbstractCmd::getTemplate)
                                    .collect(Collectors.toList()));
                    break;
                case "login":
                case "register":
                    res = processAuth(c1);
                    break;
                default:
                    if (!DBManager.getInstance().login(c1.getUsername(), c1.getPassword())){
                        res = new Responce(true, "You are not logged in!", null);
                    }else {
                        res = new Responce(false, cm.processCommand(c1), null);
                    }
            }

        } catch (CommandExecutionException |IOException | ClassNotFoundException e) {
            res = new Responce(true, e.getMessage(), null);
            LOGGER.error(e.getMessage());
        }
        Responce finalRes = res;
        Runnable task = () -> sendAnswer(finalRes, adr, channel);
        Thread someThread = new Thread(task);
        someThread.start();
    }

    private static void sendAnswer(Responce res, SocketAddress adr, DatagramChannel channel){
        try {
            ByteBuffer buffer = Converter.convertToBB(res);
            channel.send(buffer, adr);
            buffer.clear();
        }catch (IOException e){
            LOGGER.error(e.getMessage());
        }
    }

    private static Responce processAuth(CmdTemplate cmdTemplate){
        if (cmdTemplate.getUsername() == null || cmdTemplate.getPassword() == null){
            return new Responce(true, "MyNullPointerException", null);
        }
        if (cmdTemplate.name.equals("register") &&
                DBManager.getInstance().register(cmdTemplate.getUsername(), cmdTemplate.getPassword())){
            return new Responce(false, "Successfully registered!", null);

        }else if (cmdTemplate.name.equals("login") &&
                DBManager.getInstance().login(cmdTemplate.getUsername(), cmdTemplate.getPassword())){

            return new Responce(false, "Successfully logged in!", null);

        }else {
            return new Responce(true, "Authentication failed!", null);
        }
    }
}
