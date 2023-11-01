//package com.example.baitap.exception;
//
//import javassist.NotFoundException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    public ModelAndView exception(Exception ex) {
//        System.out.println(ex.getMessage());
//        ModelAndView modelAndView = new ModelAndView("customer/error");
//        return modelAndView;
//    }
//
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ModelAndView exception(ResourceNotFoundException ex) {
//        System.out.println(ex.getMessage());
//        ModelAndView modelAndView = new ModelAndView("banking/404");
//        return modelAndView;
//    }
//
//
//
//}