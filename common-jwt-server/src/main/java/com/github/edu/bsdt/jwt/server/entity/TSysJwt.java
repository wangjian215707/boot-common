package com.github.edu.bsdt.jwt.server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 22:27
 */
@Entity
@Table(name = "T_SYS_JWT")
public class TSysJwt implements Serializable {
    private String id;
    private String name;
    private String pubKey;
    private Integer age;
    private Long state;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PUB_KEY")
    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    @Basic
    @Column(name = "STATE")
    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }
    @Basic
    @Column(name = "AGE")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSysJwt sysJwt = (TSysJwt) o;
        return Objects.equals(id, sysJwt.id) &&
                Objects.equals(name, sysJwt.name) &&
                Objects.equals(pubKey, sysJwt.pubKey) &&
                Objects.equals(age, sysJwt.age) &&
                Objects.equals(state, sysJwt.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pubKey, age, state);
    }
}
