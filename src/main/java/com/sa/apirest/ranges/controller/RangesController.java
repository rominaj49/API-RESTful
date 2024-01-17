/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sa.apirest.ranges.controller;

import com.sa.apirest.ranges.exception.BusinessException;
import com.sa.apirest.ranges.model.Ranges;
import com.sa.apirest.ranges.service.RangesServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(path="/api/v1/ranges")

public class RangesController extends BaseControllerImpl<Ranges, RangesServiceImpl>{
    
    @Autowired
    private RangesServiceImpl rangesService;
    
    @GetMapping("/search")
    public ResponseEntity<?> search  (@RequestParam String filter){
        try{
         return ResponseEntity.status(HttpStatus.OK).body(rangesService.search(filter));
        }  
        catch (Exception e){
           throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }
    
      @GetMapping("/searchPaged")
    public ResponseEntity<?> search (@RequestParam String filter, Pageable pageable){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rangesService.search(filter, pageable));
        }catch (Exception e){
           throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }
}