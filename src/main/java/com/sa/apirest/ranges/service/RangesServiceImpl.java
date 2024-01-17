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
           //List <Account> entities = accountRepository.findByBancoContainingOrNombreTitularContaining(filter, filter);
           //List <Account> entities= accountRepository.search(filter);
           List<Ranges > entities = rangesRepository.searchNative(filter);
           
            return entities;
        }
        catch (Exception e){
            throw new Exception (e.getMessage());
        }  
    }
    
    @Override
    public Page<Ranges> search(String filter, Pageable pageable) throws Exception {    
        try{
            Page<Ranges> entities = rangesRepository.searchNative(filter,pageable);
            return entities;
        }catch (Exception e){
            throw new Exception (e.getMessage());
        }       
    }
}
