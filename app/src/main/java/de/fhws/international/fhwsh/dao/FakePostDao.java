package de.fhws.international.fhwsh.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhws.international.fhwsh.models.Post;

public class FakePostDao implements PostDao{
    // static variable single_instance of type Singleton
    private static FakePostDao single_instance = null;

    // variable of type String
    public Map<Long, Post> db;

    // private constructor restricted to this class itself
    @RequiresApi(api = Build.VERSION_CODES.O)
    private FakePostDao() {
        db = new HashMap<>();
        db.put(1l, new Post(1, "FHWS International office", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum hendrerit elit at suscipit imperdiet. Integer aliquet dui et ultrices consectetur. Phasellus nisl augue, tristique sed faucibus quis, laoreet ut orci. Praesent laoreet ligula est, nec dictum eros vehicula vel. Duis quis fringilla erat, eget rhoncus velit. Fusce at posuere eros. Vivamus ut ligula fringilla, auctor libero nec, accumsan justo. Phasellus ipsum ante, gravida eu feugiat maximus, rutrum nec dolor.", LocalDateTime.now()));
        db.put(2l, new Post(2, "New Photos from Uni", "Aliquam erat volutpat. Etiam a elit arcu. Mauris euismod, orci vel imperdiet tincidunt, lorem leo ultricies dui, quis fringilla orci sem ac nisi. Donec ac congue leo, quis mattis leo. Cras egestas, purus nec maximus faucibus, velit sapien maximus neque, eget venenatis tellus orci quis ex. Aliquam vel ex vitae leo tincidunt malesuada. Fusce sit amet erat laoreet, semper orci sed, vestibulum leo. Proin vitae eleifend nulla, in eleifend tellus. Aenean eleifend magna vel felis auctor suscipit. Pellentesque placerat fermentum felis nec luctus. Suspendisse consequat consequat justo, et semper nisi cursus eu. Pellentesque sollicitudin non nisi sed tempor. Duis a massa tincidunt magna ultrices venenatis. Nulla sit amet dui sed ex gravida eleifend.", LocalDateTime.now()));
        db.put(3l, new Post(3, "Necessary information about the Exam!", "Phasellus faucibus risus ullamcorper est accumsan elementum. Quisque id tempus metus. Nam velit mauris, vestibulum dignissim eleifend vitae, volutpat sed nibh. Nulla cursus tincidunt metus, sed sollicitudin nisl bibendum vitae. In vitae egestas elit. Etiam elit lacus, accumsan ut purus id, iaculis sagittis nibh. Nunc et vehicula odio. Donec vitae elementum massa. Aenean efficitur turpis id rutrum aliquam. Praesent in vestibulum justo. Curabitur tincidunt, orci quis euismod pellentesque, massa est dapibus felis, finibus gravida magna dui sit amet lacus. Nulla eget accumsan erat, sit amet sollicitudin metus.", LocalDateTime.now()));
    }

    // static method to create instance of Singleton class
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static FakePostDao getInstance() {
        if (single_instance == null)
            single_instance = new FakePostDao();

        return single_instance;
    }

    public long addNewPost(Post post) {
        db.put(post.getId(), post);
        return post.getId();
    }

    public void deletePost(long id) {
        db.remove(id);
    }

    @Override
    public List<Post> getAll() {
        ArrayList<Post> posts = new ArrayList<>(db.values());
        sortByDate(posts);
        return posts;
    }

    @Override
    public Post getById(long id) {
        return db.get(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void update(long id, Post post) {
        db.replace(id, post);
    }

    @Override
    public int size() {
        return db.size();
    }
    
    private void sortByDate(List<Post> posts) {
        Collections.sort(posts, new Comparator<Post>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public int compare(Post o1, Post o2) {
               return o2.getDate().compareTo(o1.getDate());
            }
        });
    }
}
