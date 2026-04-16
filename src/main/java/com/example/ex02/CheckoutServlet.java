package com.example.ex02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", value = "/checkout")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy lại Session cũ của khách (không tạo mới nếu không tồn tại)
        HttpSession session = request.getSession(false);

        List<String> cart = null;
        if (session != null) {
            // 2. Lấy giỏ hàng đã lưu từ các bước trước
            @SuppressWarnings("unchecked")
            List<String> tempCart = (List<String>) session.getAttribute("myCart");
            cart = tempCart;
        }

        // 3. Xử lý thông báo hiển thị
        String message;
        if (cart == null || cart.isEmpty()) {
            message = "Giỏ hàng của bạn đang trống!";
        } else {
            message = "Bạn có " + cart.size() + " sản phẩm trong giỏ.";
        }

        // 4. Đẩy thông báo này vào request để trang JSP có thể đọc được
        request.setAttribute("message", message);

        // 5. Forward (chuyển tiếp) sang file giao diện .jsp
        request.getRequestDispatcher("/checkout-page.html").forward(request, response);
    }
}