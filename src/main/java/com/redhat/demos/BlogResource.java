package com.redhat.demos;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.demos.model.NewPostRequest;
import com.redhat.demos.model.Post;
import com.redhat.demos.model.Tag;

@Path("/blog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class BlogResource {
    public static final Logger LOG = LoggerFactory.getLogger(BlogResource.class);
    @Inject
    EntityManager entityManager;

    @POST
    @Path("/tag")
    @Transactional
    public void createTag(Tag tag) {
        LOG.info("creating a new tag: " + tag);
        tag.persist();
    }

    @GET
    @Path("/tags")
    @Transactional
    public List<Tag> listAllTags() {
        return Tag.listAll();
    }

    @POST
    @Path("/post")
    @Transactional
    public void createPost(NewPostRequest newPostRequest) {
        LOG.info("new post request: " + newPostRequest);
        Post newPost = new Post(newPostRequest.getPostTitle());
        newPostRequest.getTagNames().forEach(tagName -> {
            LOG.info("\tloading tag from DB: " + tagName);
            // Tag loadedTag = Tag.find("name", tagName).firstResult();

            Session hibernateSession = entityManager.unwrap(Session.class);
            Tag loadedTagViaHbn = hibernateSession.bySimpleNaturalId(Tag.class).load(tagName);    

            LOG.info("\tadding tag: " + loadedTagViaHbn);

            // newPost.addTag(loadedTag);
            newPost.addTag(loadedTagViaHbn);
            // LOG.info("persisting Post: " + newPost);
            // newPost.persist();
        });
        LOG.info("persisting Post: " + newPost);
        // newPost.persist();
        entityManager.persist(newPost);

    }

    @GET
    @Path("/posts")
    @Transactional
    public List<Post> listAllPosts() {

        List<Post> posts = entityManager.createQuery(
            "select p " +
            "from Post p " +
            "join fetch p.tags pt " +
            "join fetch pt.tag ", Post.class)
            .getResultList();
        return posts;
    }
}
