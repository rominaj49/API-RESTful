/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sa.apirest.ranges.service;

import com.sa.apirest.ranges.interfaces.BaseService;
import com.sa.apirest.ranges.model.Base;
import com.sa.apirest.ranges.repository.BaseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 * @author HP
 */
public abstract class BaseServiceImpl <E extends Base, ID extends Serializable> implements BaseService <E,ID>{
    
    protected BaseRepository<E, ID > baseRepository;
    
    public BaseServiceImpl (BaseRepository<E, ID > baseRepository){
        this.baseRepository= baseRepository;
    }
    
    @Override
    public List<E> findAll() throws Exception {
        
        try{
            List<E> entities = baseRepository.findAll();
            return entities;
        }catch (Exception e){
            throw new Exception (e.getMessage());
        }
    }
    
    @Override
    public E findById(ID id) throws Exception {
        
        try {        
            Optional<E> entityOptional = baseRepository.findById(id); //busco el id
            
            //VALIDACIÓN SI ES NULO
            if (!entityOptional.isPresent()) {
                throw new EntityNotFoundException("No existe registro con el id: " + id);
            }  
            
            return entityOptional.get();     //si sigue corriendo el programa devuelve EL REG        
        } 
        
        
        catch(EntityNotFoundException e){   //aca captura la excedption y la envia al controlador
            throw e;
        }
        
        catch(Exception e){                 //error de otro tipo
         throw new Exception (e.getMessage());
        }         
    }
    
    
    @Transactional
    @Override
    public E save(E entity) throws Exception {
        
        try {          
         entity=baseRepository.save(entity);
         return entity;       
       }
        
        catch(DataIntegrityViolationException e){
        throw new DataIntegrityViolationException(e.getMessage());
        }
        
         catch (Exception e){      
        throw new Exception (e.getMessage());
        }
        
        
    }
    
    @Transactional
    @Override
    public E update(ID id, E entity) throws Exception {
        
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            if (!entityOptional.isPresent()) {
                throw new EntityNotFoundException("No existe registro con el id: " + id); // ERROR 404
            }
            if(id!=entity.getId()){
                throw new BadRequestException("No coinciden los ids existentes."); // COMPARA ID DE LA BUSQ CON EL DEL BODY --> BAD REQUEST 400
            }           
            E appo = entityOptional.get();
            appo = baseRepository.save(entity);
            return appo;
            
        }catch(BadRequestException e){
            throw e;
        }catch(EntityNotFoundException e){
            throw e;
        }catch (Exception e){ // 
        throw new Exception (e.getMessage());
        }
          
    }
    
    @Transactional
    @Override
    public boolean delete(ID id) throws Exception {
        try {
          if (baseRepository.existsById(id)) 
           {
              baseRepository.deleteById(id);
              return true;
            }           
          else {
              throw new EntityNotFoundException("No existe registro con el id: " + id);
            }
          }catch(EntityNotFoundException e){
           throw e ;
           
          }catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
