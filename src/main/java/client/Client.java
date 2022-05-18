package client;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import commands.AbstractCmd;
import commands.Command;
import commands.LogInCommand;
import exceptions.CommandExecutionException;
import exceptions.ServerUnavailableException;
import transfer.CmdTemplate;
import transfer.Responce;
import utils.Converter;
import utils.InputProcessor;
import utils.ManagerFiller;


/**
 * Class for client's command execution
 */
public class Client{

    public final static int SERVICE_PORT = 50505;
    public final static SocketAddress sockaddr = new InetSocketAddress("localhost", SERVICE_PORT);
    public static ArrayList<CmdTemplate> hehCommands = new ArrayList<>();
    public static Map<String, AbstractCmd> localCommands = new LinkedHashMap<>();

    public static Map<String, AbstractCmd> preLoggedCommands = new LinkedHashMap<>();
    public static String login;
    public static String password;
    private static boolean loggedIn = false;

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

            channel.bind(null);
            channel.configureBlocking(false);
            ByteBuffer fromBuffer = ByteBuffer.allocate(1024 * 8);

            CmdTemplate firstIssue = new CmdTemplate("requestArgs", "", "", null);
            ByteBuffer toBuffer = Converter.convertToBB(firstIssue);

            channel.send(toBuffer, Client.sockaddr);
            waitResponce(channel, fromBuffer);

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

            AbstractCmd ecs_3 = new HelpCommand();
            AbstractCmd ecs = new ExecuteScriptCommand();
            AbstractCmd ecs_2 = new ExitCommand();

            localCommands.put(ecs_3.getName(), ecs_3);
            localCommands.put(ecs.getName(), ecs);
            localCommands.put(ecs_2.getName(), ecs_2);

            System.out.println("Contact is here!");

            while (!loggedIn) {
                String command = InputProcessor.inputString();
                try {
                    if (command.split(" ")[0].equals("login") || command.split(" ")[0].equals("register")){
                        if (command.split(" ").length != 3){
                            System.err.println("Must be like: '{login|register} username password'");
                            continue;
                        }
                        CmdTemplate cmdTemplate = new CmdTemplate(command.split(" ")[0], command.split(" ")[1], command.split(" ")[2]);
                        loggedIn = sendCommandAndCheckAnswer(cmdTemplate, channel, fromBuffer);
                        if (loggedIn){
                            Client.login = command.split(" ")[1];
                            Client.password = command.split(" ")[2];
                        }
                    } else {
                        System.err.println("You're not privileged to do anything. To become, use 'login' or 'register'");
                    }
                } finally {
                    fromBuffer.clear();
                }
            }

            while (true) {
                String command = InputProcessor.inputString();
                try {

                    if (localCommands.containsKey(command.split(" ")[0])){
                        cm.executeLocalCommand(command);
                        continue;
                    }

                    CmdTemplate cmd = cm.processCommand(command);
                    cmd.updateAuth(Client.login, Client.password);
                    sendCommandAndGetAnswer(cmd, channel, fromBuffer);

                }catch (CommandExecutionException e){
                    System.err.println(e.getMessage());
                }finally {
                    fromBuffer.clear();
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

    public static boolean sendCommandAndCheckAnswer(CmdTemplate cmd, DatagramChannel channel, ByteBuffer fromBuffer) throws IOException, ClassNotFoundException{
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
            return (!resp.isError) && resp.message.startsWith("Successfully");
        }finally {
            buffer.clear();
            fromBuffer.clear();
        }

    }

    public static void sendCommandAndGetAnswer(CmdTemplate cmd, DatagramChannel channel, ByteBuffer fromBuffer) throws IOException, ClassNotFoundException{
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
    }

}
