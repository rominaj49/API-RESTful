package com.sa.apirest.ranges.controller;

import com.sa.apirest.ranges.exception.BusinessException;
import com.sa.apirest.ranges.interfaces.BaseController;
import com.sa.apirest.ranges.model.Base;
import com.sa.apirest.ranges.service.BaseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


public abstract class BaseControllerImpl <E extends Base, S extends BaseServiceImpl<E, Integer>> implements BaseController<E, Integer>{
    
    @Autowired
    public S service;
    
    @Override
    @GetMapping("")
    @Operation(
      description = "Obtener rangos",
       responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
        }
    )
    public ResponseEntity<?> getAllRecord() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        }catch (Exception e){
        throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage()); //manda la excepcion y mensaje    
        }
    }
    
    @Override
    @GetMapping("/{id}")
    @Operation(
        description = "Buscar rango",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    public ResponseEntity<?> getRecordById(@PathVariable Integer id) {
        
       try {   
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));            
       }
             
       catch(EntityNotFoundException e){
        throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage()); //manda la excepcion y mensaje    
       }
        
       catch(Exception e){ //error general 500
       throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());     
       }
        
    }

    @Override
    @PostMapping("")
    @Operation(
        description = "Crear rango",
        responses = {
            @ApiResponse(responseCode = "201", ref = "created"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    ) 
    public ResponseEntity<?> save(@Valid @RequestBody E entity) {
        
        try {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
        }
        
        catch(DataIntegrityViolationException e){
        throw new BusinessException(HttpStatus.BAD_REQUEST,e.getMessage());  
        }
        catch(Exception e){
        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }

    //UPDATE
    @Override
    @PutMapping("/{id}")
    @Operation(
        description = "Actualizar rango",
        responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "400", ref = "badRequest"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody E entity) {
        try {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
        }
        
       catch(BadRequestException e){
        throw new BusinessException(HttpStatus.BAD_REQUEST,e.getMessage());  
        }
        catch(EntityNotFoundException e){
        throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage());
        }
        
        catch(Exception e){
        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
          
    }

    //DELETE
    @Override
    @DeleteMapping("/{id}")
    @Operation(
        description = "Eliminar rango",
       responses = {
            @ApiResponse(responseCode = "204", ref = "noContent"),
            @ApiResponse(responseCode = "404", ref = "notFound"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    ) 
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        
        try {
         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        }
         catch(EntityNotFoundException e){
         throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage()); 
        }
        catch (Exception e) {
        throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }  
}
