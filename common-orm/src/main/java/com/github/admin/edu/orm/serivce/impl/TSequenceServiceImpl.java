package com.github.admin.edu.orm.serivce.impl;
import com.github.admin.edu.orm.repository.TSequenceDomain;
import com.github.admin.edu.orm.serivce.TSequenceService;
import com.github.admin.edu.orm.entity.TSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/4/18
 * Time: 20:38
 */
@Service
public class TSequenceServiceImpl implements TSequenceService {

    @Autowired
    private TSequenceDomain domain;

    @Transactional
    @Override
    public Long getUserId(String name) {
        if(!StringUtils.isEmpty(name)){
            Optional<TSequence> optional=domain.findById(name);
            if(!optional.isPresent()){
                TSequence tsequence=new TSequence();
                tsequence.setName(name);
                tsequence.setId(Long.parseLong("1"));
                domain.save(tsequence);
                return Long.parseLong("1");
            }else {
                TSequence tsequence=optional.get();
                tsequence.setId(tsequence.getId()+1);
                domain.save(tsequence);
                return tsequence.getId();
            }
        }
        return null;
    }

}
