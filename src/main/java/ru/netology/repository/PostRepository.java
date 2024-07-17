package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private final AtomicLong counter = new AtomicLong();

  public List<Post> all() {
    return new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = counter.incrementAndGet();
      post.setId(newId);
      posts.put(newId, post);
    } else {
      if (posts.containsKey(post.getId())) {
        posts.put(post.getId(), post);
      } else {
        throw new NotFoundException("Post not found");
      }
    }
    return post;
  }

  public void removeById(long id) {
    if (posts.remove(id) == null) {
      throw new NotFoundException("Post not found");
    }
  }
}