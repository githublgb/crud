package test.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {

    public static Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            //获取客户端的的流信息
            InputStream inputStream =socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是服务器，客户端说：" + info);
            }
            //向客户端输出流信息
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("欢迎您！");
            printWriter.flush();


            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        //服务器代码
        ServerSocket serverSocket = new ServerSocket(10086);
        int count = 0;//记录客户端的数量
        while (true) {
            socket = serverSocket.accept();//连接到一个新socket客户端，开启一个线程
            Runnable serverThread = new ServerThread(socket);
            Thread thread = new Thread(serverThread); //开启一个线程
            thread.start();
            count++;
            System.out.println("客户端连接的数量：" + count);
        }
    }


}
