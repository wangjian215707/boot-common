package com.github.edu.bsdt.jwt.server.domain;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.bsdt.jwt.server.entity.TSysJwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 22:28
 */
public interface ClientJWTTokenManagerDomain extends CustomRepository<TSysJwt,String> {

    @Query("select t from TSysJwt t where t.id=:clientId and t.pubKey=:key and t.state = 1 ")
    List<TSysJwt> getAllByIdAndAndPubKeyAndState(@Param("clientId") String clientId,
                                                 @Param("key") String key);
}
