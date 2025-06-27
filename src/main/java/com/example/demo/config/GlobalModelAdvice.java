package com.example.demo.config;

import com.example.demo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
public class GlobalModelAdvice {
    @ModelAttribute
    public void checkLogin(HttpSession session, HttpServletResponse response,
                           Model model) throws IOException {
        User user = (User)session.getAttribute("currentUser");
        // Danh sachs route loại trừ
        List<String> freeAccess = List.of("/login","/register","/logout","/css","/js");
        // Lấy link mà đang vào
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest();
        String uri = request.getRequestURI();
        boolean canAccess = freeAccess.stream().anyMatch(uri::startsWith);
        // nếu session ko có user và trang đó phải đăng nhập mới được vào
        if(user == null && !canAccess){
            response.sendRedirect("/login");
        }
        // Nếu đã login thì cho dữ liệu user vào model
        if(user != null){
            model.addAttribute("currentUser",user);
        }
    }
}
