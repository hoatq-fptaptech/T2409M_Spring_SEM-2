package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    public String getAllStudent(Model model){
        List<Student> list = studentRepository.findAll();
        model.addAttribute("students",list);
        model.addAttribute("content","students/list");
        return "layout/main";
    }

    @GetMapping("create")
    public String formCreate(Model model){
        model.addAttribute("content","students/create_form");
        return "layout/main";
    }

    @PostMapping("create")
    public String createStudent(@ModelAttribute Student student){
        studentRepository.save(student);
        return "redirect:/students";
    }
}
