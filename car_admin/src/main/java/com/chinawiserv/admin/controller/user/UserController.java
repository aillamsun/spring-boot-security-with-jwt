package com.chinawiserv.admin.controller.user;

import com.chinawiserv.admin.config.security.JwtTokenUtil;
import com.chinawiserv.admin.config.security.JwtUser;
import com.chinawiserv.admin.controller.test.Person;
import com.chinawiserv.admin.model.User;
import com.chinawiserv.core.response.ResultBody;
import com.chinawiserv.core.response.ResultGenerator;
import com.chinawiserv.core.web.controller.BaseCRUDController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sungang on 2017/8/19.
 * <p>
 * 在 @PreAuthorize 中我们可以利用内建的 SPEL 表达式：比如 'hasRole()' 来决定哪些用户有权访问。
 * 需注意的一点是 hasRole 表达式认为每个角色名字前都有一个前缀 'ROLE_'。所以这里的 'ADMIN' 其实在
 * 数据库中存储的是 'ROLE_ADMIN' 。这个 @PreAuthorize 可以修饰Controller也可修饰Controller
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseCRUDController<User> {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }


    private List<Person> persons = new ArrayList<>();

    private void add() {
        persons.add(new Person("Hello", "World"));
        persons.add(new Person("Foo", "Bar"));
    }

    private void clear() {
        persons.clear();
    }

    @RequestMapping(path = "/persons", method = RequestMethod.GET)
    public List<Person> getPersons() {
        return persons;
    }

    @RequestMapping(path = "/persons", method = RequestMethod.POST)
    public ResultBody addP() {
        add();
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping(path = "/persons", method = RequestMethod.DELETE)
    public ResultBody delP() {
        clear();
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping(path = "/persons/{name}", method = RequestMethod.GET)
    public Person getPerson(@PathVariable("name") String name) {
        return persons.stream()
                .filter(person -> name.equalsIgnoreCase(person.getName()))
                .findAny().orElse(null);
    }


}
