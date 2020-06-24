package com.spring.boot.web;

import com.spring.boot.entity.User;
import com.spring.boot.service.test.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Api(tags = "用户管理")
@RestController  // 直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式
@RequestMapping("/users")
public class UserController {

    // 创建线程安全的Map，模拟users信息的存储
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @Autowired
    private UserService userService;

    /**
     * 处理"/users/"的 GET 请求，用来获取用户列表
     * @return
     */
    @GetMapping("/")
    @ApiOperation(value = "获取用户列表", httpMethod = "GET")
    public List<User> getUserList() {
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> r = new ArrayList<User>(users.values());
        return r;
    }

    /**
     * 处理"/users/add"的 POST 请求，用来创建 User
     * @param user   @RequestBody 接受的是一个 json 格式的字符串是一个字符串
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "创建单个用户", notes = "根据单个 User 对象创建用户", httpMethod = "POST")
    public String postUser(@Valid @RequestBody User user) {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        users.put(user.getUid(), user);
        return "success";
    }

    /**
     * 处理"/users/add"的 POST 请求，用来创建 User
     * @param lists   @RequestBody 接受的是一个 json 格式的字符串是一个字符串
     * @return
     */
    @PostMapping("/addAll")
    @ApiOperation(value = "创建多个用户", notes = "根据多个 User 对象创建用户",  httpMethod = "POST")
    public String postUsers(@RequestBody List<User> lists) {
        for (User user : lists) {
            users.put(user.getUid(), user);
        }
        return "success";
    }

    /**
     * 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", dataType = "Long", required = true)
    public User getUser(@PathVariable Long id) {
        // url中的id可通过@PathVariable绑定到函数的参数中
        return userService.getUserByUid(id);
    }

    /**
     * 处理"/users/{id}"的PUT请求，用来更新User信息
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", dataType = "Long", required = true)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.put(id, u);
        return "success";
    }

    /**
     * 处理"/users/{id}"的DELETE请求，用来删除User
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "success";
    }


}
