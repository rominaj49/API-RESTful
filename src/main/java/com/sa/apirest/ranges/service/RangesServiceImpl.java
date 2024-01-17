/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sa.apirest.ranges.service;

import com.sa.apirest.ranges.interfaces.RangesService;
import com.sa.apirest.ranges.model.Ranges;
import com.sa.apirest.ranges.repository.BaseRepository;
import com.sa.apirest.ranges.repository.RangesRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RangesServiceImpl extends BaseServiceImpl<Ranges, Integer> implements RangesService{

    @Autowired
    private RangesRepository rangesRepository;
    
    public RangesServiceImpl(BaseRepository<Ranges, Integer> baseRepository){
        super(baseRepository);
    }
    
    ///////////////////////////////////////////PAGINADO////////////////////////////////////////////
    @Override
    public List<Ranges> search (String filter) throws Exception{
        try{
           List<Ranges > entities = rangesRepository.searchNative(filter);
            if (entities.isEmpty()) {
             throw new EntityNotFoundException("No se encontraron resultados similares");
            }
            return entities;
        }
        catch(EntityNotFoundException e){   
            throw e;
        }
        catch (Exception e){
            throw new Exception (e.getMessage());
        }  
    }
    
    @Override
    public Page<Ranges> search(String filter, Pageable pageable) throws Exception {    
        try{
            Page<Ranges> entities = rangesRepository.searchNative(filter,pageable);
            if (entities.isEmpty()) {
             throw new EntityNotFoundException("No se encontraron resultados similares");
            }
            return entities;
        }
        catch(EntityNotFoundException e){   
            throw e;
        }
        catch (Exception e){
            throw new Exception (e.getMessage());
        }       
    }
}
