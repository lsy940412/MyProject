package com.itany.bookweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itany.bookapi.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookserver")
public class BookController  {
    @Reference
    private BookService bookService;

    @RequestMapping("/findDoc")
    public String findDoc(@RequestParam(name = "index",required = false) String index,
                           @RequestParam(name = "pageNo",required = false) String pageNo,
                           Model model){
        Map<String, Object> doc = bookService.findDoc(index, pageNo);
        doc.put("index",index);

        model.addAttribute("docs",doc);
       return "bookList";
    }

    @RequestMapping("/addDoc")
    public String addDoc(){
        System.out.println("------addDoc-------");
        bookService.addDoc();
        return "bookList";
    }



}
