package de.fhws.international.fhwsh.dao;

import java.util.List;

import de.fhws.international.fhwsh.models.Post;

public interface PostDao {
    long addNewPost(Post post);

    void deletePost(long id);

    List<Post> getAll();

    Post getById(long id);

    void update(long id, Post post);

    int size();
}
