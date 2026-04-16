package com.example.ex02;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddToCartServlet", value = "/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy mã sản phẩm từ form gửi lên
        String productId = request.getParameter("productId");

        // 2. Mở Session (phiên làm việc) của khách hàng
        HttpSession session = request.getSession();

        // 3. Lấy giỏ hàng từ Session ra
        @SuppressWarnings("unchecked")
        List<String> cart = (List<String>) session.getAttribute("myCart");

        // 4. Nếu khách chưa có giỏ (lần đầu thêm), tạo mới một cái ArrayList
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 5. Thêm sản phẩm vào danh sách
        if (productId != null && !productId.trim().isEmpty()) {
            cart.add(productId);
        }

        // 6. Cất giỏ hàng ngược lại vào Session (Quan trọng: Lưu trữ liên Request)
        session.setAttribute("myCart", cart);

        // 7. Chuyển hướng sang trang checkout
        response.sendRedirect(request.getContextPath() + "/checkout");
    }
}