/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sa.apirest.ranges.repository;

import com.sa.apirest.ranges.model.Ranges;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RangesRepository extends BaseRepository<Ranges, Integer>{
    List<Ranges> findByRangesContaining(String ranges);
     
    @Query (value= "SELECT a FROM Ranges a WHERE a.ranges LIKE %:filter%")
    List<Ranges> search (@Param ("filter") String filter);
    
    @Query(nativeQuery = true, value = "SELECT * FROM ranges a WHERE a.ranges LIKE %:filter% ")
    List<Ranges> searchNative(@Param ("filter") String filter);
    
    Page <Ranges> findByRangesContaining(String ranges, Pageable pageable);
    
    @Query( 
            value = "SELECT a FROM Ranges a WHERE a.ranges LIKE %:filter%" )           
    Page<Ranges> search(@Param ("filter") String filter, Pageable pageable);
    
    
    @Query(
            nativeQuery = true, 
            value = "SELECT * FROM ranges a WHERE a.ranges LIKE %:filter%")          
    Page<Ranges> searchNative(@Param ("filter") String filter, Pageable pageable);
}
