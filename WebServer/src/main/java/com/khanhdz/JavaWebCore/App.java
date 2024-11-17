/*
 * This source file was generated by the Gradle 'init' task
 */
package com.khanhdz.JavaWebCore;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    static {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            System.setErr(new PrintStream(System.err, true, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(new App().getGreeting());

        // Tạo một HttpServer và lắng nghe tại cổng 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // Đăng ký một handler để xử lý yêu cầu tại đường dẫn "/"
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                // Lấy đường dẫn tệp từ yêu cầu
                String requestPath = exchange.getRequestURI().getPath();

                String path = "E:\\DEV\\PROJECT_WEB\\JavaWebCore\\WebServer\\www\\grrs" + requestPath;
                System.out.println("path: " + path);
                Path filePath = Paths.get(path);

                File file = filePath.toFile();

                if (file.exists()) {
                    // Đọc toàn bộ nội dung tệp vào byte[]
                    byte[] fileContent = Files.readAllBytes(filePath);

                    // Xác định loại nội dung (MIME type) dựa trên phần mở rộng tệp
                    String contentType = "text/html"; // Mặc định là HTML
                    if (requestPath.endsWith(".js")) {
                        contentType = "application/javascript"; // JavaScript
                    } else if (requestPath.endsWith(".wasm")) {
                        contentType = "application/wasm"; // WebAssembly
                    } else if (requestPath.endsWith(".json")) {
                        contentType = "application/json"; // JSON
                    } else if (requestPath.endsWith(".jpg") || requestPath.endsWith(".jpeg")) {
                        contentType = "image/jpeg"; // Hình ảnh JPG
                    } else if (requestPath.endsWith(".png")) {
                        contentType = "image/png"; // Hình ảnh PNG
                    } else if (requestPath.endsWith(".css")) {
                        contentType = "text/css"; // CSS
                    }

                    exchange.getResponseHeaders().set("Content-Type", contentType);

                    // Thiết lập header Content-Type
//                    exchange.getResponseHeaders().add("Content-Type", contentType);
                    // Gửi mã phản hồi HTTP 200 và chiều dài nội dung tệp
                    exchange.sendResponseHeaders(200, fileContent.length);

                    // Gửi nội dung tệp về phía client
                    OutputStream os = exchange.getResponseBody();
                    os.write(fileContent);
                    os.close();
                } else {
                    // Nếu tệp không tồn tại, trả về lỗi 404
                    String response = "<h1>File not found</h1>";
                    exchange.sendResponseHeaders(404, response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            }
        });

        // Bắt đầu máy chủ
        server.start();
        System.out.println("Server started on port 8000...");

    }
}
