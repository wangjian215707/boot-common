package com.github.admin.edu.orm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/4/18
 * Time: 20:33
 */
@Entity
@Table(name = "TSEQUENCE")
public class TSequence implements Serializable {
    private String name;
    private Long id;
    private Long rangeNo;
    private Long rangeSize;

    @Id
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "RANGE_NO")
    public Long getRangeNo() {
        return rangeNo;
    }

    public void setRangeNo(Long rangeNo) {
        this.rangeNo = rangeNo;
    }

    @Basic
    @Column(name = "RANGE_SIZE")
    public Long getRangeSize() {
        return rangeSize;
    }

    public void setRangeSize(Long rangeSize) {
        this.rangeSize = rangeSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSequence tSequence = (TSequence) o;
        return Objects.equals(name, tSequence.name) &&
                Objects.equals(id, tSequence.id) &&
                Objects.equals(rangeNo, tSequence.rangeNo) &&
                Objects.equals(rangeSize, tSequence.rangeSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, rangeNo, rangeSize);
    }
}
