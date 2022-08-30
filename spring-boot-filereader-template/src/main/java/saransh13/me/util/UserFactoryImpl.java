package saransh13.me.util;

import org.springframework.stereotype.Component;
import saransh13.me.model.User;
@Component
public class UserFactoryImpl implements  IFactory<User> {



    @Override
    public User newObject(String[] split) {
        return User.builder().id(Integer.parseInt(split[0].trim())).name(split[1].trim()).build();
    }

}