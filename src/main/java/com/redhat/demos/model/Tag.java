package com.redhat.demos.model;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Tag")
@Table(name = "tag")
@NaturalIdCache
@Cache(
    usage = CacheConcurrencyStrategy.READ_WRITE
)
@EqualsAndHashCode(callSuper = false)
@ToString
public class Tag extends PanacheEntity {
 
    @NaturalId
    @Getter
    @Setter
    private String name;
 
    @OneToMany(
        mappedBy = "tag",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Getter
    @Setter
    @JsonbTransient
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PostTag> posts = new ArrayList<>();
 
    public Tag() {
    }
 
    public Tag(String name) {
        this.name = name;
    }
 
}
