package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;
import ru.itmo.web.lesson4.model.UsersColor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", UsersColor.BLUE),
            new User(6, "pashka", "Pavel Mavrin", UsersColor.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", UsersColor.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", UsersColor.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
        new Post(1, "Hello, world", "Well done! The post is working", 1),
        new Post(2, "It's the second Post!", "Yeah, It's true. This is the second one", 6),
        new Post(3, "It's the third one", "sed lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt ornare massa eget egestas purus viverra accumsan in nisl nisi scelerisque eu ultrices vitae auctor eu augue ut lectus arcu bibendum at varius vel pharetra vel turpis nunc eget lorem dolor sed viverra ipsum nunc aliquet bibendum enim facilisis gravida neque convallis a cras semper auctor neque vitae tempus quam pellentesque nec nam aliquam sem et tortor consequat id porta nibh venenatis cras sed felis eget velit aliquet sagittis id consectetur purus ut faucibus pulvinar elementum integer enim neque volutpat ac tincidunt vitae semper quis lectus nulla at volutpat diam ut venenatis tellus in metus vulputate eu scelerisque felis imperdiet proin fermentum leo vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur libero id faucibus nisl tincidunt eget nullam non nisi est sit amet facilisis magna etiam tempor orci eu lobortis elementum nibh tellus molestie nunc non blandit massa enim nec dui nunc mattis enim ut tellus elementum sagittis vitae et leo duis ut diam quam nulla porttitor massa id neque aliquam vestibulum morbi blandit cursus risus at ultrices mi tempus imperdiet nulla malesuada pellentesque elit eget gravida cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus mauris vitae ultricies leo integer malesuada nunc vel risus commodo viverra maecenas accumsan lacus vel facilisis volutpat est velit egestas dui id ornare arcu odio ut sem nulla pharetra diam sit amet nisl suscipit adipiscing bibendum est ultricies integer quis auctor elit sed vulputate mi sit amet mauris commodo quis imperdiet massa tincidunt nunc pulvinar sapien et ligula ullamcorper", 1)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }

        data.put("posts", POSTS);
    }
}
