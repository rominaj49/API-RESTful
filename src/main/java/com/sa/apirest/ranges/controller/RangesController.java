package com.sa.apirest.ranges.controller;

import com.sa.apirest.ranges.exception.BusinessException;
import com.sa.apirest.ranges.model.Ranges;
import com.sa.apirest.ranges.service.RangesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
     @Operation(
        description = "Filtrar por palabra",
       responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFoundSearch"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")
        }
    )
    public ResponseEntity<?> search  (@RequestParam String filter){
        try{
         return ResponseEntity.status(HttpStatus.OK).body(rangesService.search(filter));
        }  
        
        catch(EntityNotFoundException e){
        throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage());
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
        catch (Exception e){
           throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }
    
      @GetMapping("/searchPaged")
       @Operation(
        description = "Filtrar por palabra con Paginado",
       responses = {
            @ApiResponse(responseCode = "200", ref = "okAPI"),
            @ApiResponse(responseCode = "404", ref = "notFoundSearch"),
            @ApiResponse(responseCode = "500", ref = "internalServerError")

        }
    )
    public ResponseEntity<?> search (@RequestParam String filter, Pageable pageable){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(rangesService.search(filter, pageable));
        }
        catch(EntityNotFoundException e){
           throw new BusinessException(HttpStatus.NOT_FOUND,e.getMessage()); //manda la excepcion y mensaje    
           //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
           throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());         
        }
    }
}