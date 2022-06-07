package com.redhat.demos.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "PostTag")
@Table(name = "post_tag")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class PostTag extends PanacheEntityBase {
 
    @EmbeddedId
    @EqualsAndHashCode.Exclude
    private PostTagId id;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    private Post post;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tagId")
    private Tag tag;
 
    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();
 
    public PostTag() {}
 
    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
        this.id = new PostTagId(post.id, tag.id);
    }
 
}
