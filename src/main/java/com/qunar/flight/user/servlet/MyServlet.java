package com.qunar.flight.user.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/servlet", asyncSupported = true)
public class MyServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // 2.开启异步，获取异步上下文 System.out.println("---begin serlvet----");
        final AsyncContext asyncContext = req.startAsync();
        System.out.println("---start servlet----");
        // 3.提交异步任务
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                // 3.2设置响应结果
                res.setContentType("text/html");
                PrintWriter out = null;
                try {
                    out = asyncContext.getResponse().getWriter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Hello World</title>");
                out.println("</head>");
                out.println("<body>");
                String s = "system time : " + System.currentTimeMillis() + "????????";
                out.println("<h1>welcome this is myServlet  " + s + "</h1>");
                out.println("</body>");
                out.println("</html>");
                System.out.println("---async res end----");
                asyncContext.complete();
            }
        });

        System.out.println("---end servlet----");

    }
}
