package com.sa.apirest.ranges.interfaces;

import com.sa.apirest.ranges.model.Ranges;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RangesService extends BaseService<Ranges, Integer>{
    public List <Ranges> search (String filter) throws Exception;
    public Page <Ranges> search (String filter, Pageable pageable) throws Exception;
}
