/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sa.apirest.ranges.exception;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BusinessException extends RuntimeException{

    private HttpStatus httpStatus;
    
    public BusinessException(HttpStatus httpStatus, String message){
        super (message);
        this.httpStatus = httpStatus;
        
    }
    
}
