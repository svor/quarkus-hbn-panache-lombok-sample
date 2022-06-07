package com.redhat.demos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
public class PostTagId implements Serializable {
 
    @Column(name = "post_id")
    private Long postId;
 
    @Column(name = "tag_id")
    private Long tagId;

}