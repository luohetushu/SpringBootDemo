package com.spring.boot;

import com.spring.boot.dao.UserDao;
import com.spring.boot.entity.User;
import com.spring.boot.web.AdminController;
import com.spring.boot.web.UserController;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.spring.boot.web.HelloController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)  // 用SpringRunner来运行
@SpringBootTest
public class SpringBootDemoApplicationTests {

    public SpringBootDemoApplicationTests(){}

    /**
     * 加载一个使用 Spring Boot 字典配置功能的 Spring 应用程序上下文
     */
    @Test
    public void contextLoads() {
    }


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    //todo 使用 new 的方式创建控制器，那么控制器类中的 @Autowired 将会失效，注入为空
    private Object[] controllers = new Object[]{new HelloController(), new UserController(), new AdminController()};

    //创建目标控制类并传递到MockMvcBuilders.standaloneSetup()函数中
    @Before
    public void setMvc(){
        //mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        //mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
//        mvc = MockMvcBuilders.standaloneSetup(controllers)
//                .addFilter((request, response, filterChain) -> {
//                    //设置响应编码
//                    response.setCharacterEncoding("UTF-8");
//                    filterChain.doFilter(request, response);
//                })
//                .build();
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Hello Spring Boot")));
    }

    @Test
    public void testUser() throws Exception{
        // 测试UserController
        RequestBuilder request;

        // get 获取 users 列表
        request = get("/users/");
        //mvc.perform(request).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
        //MockHttpServletResponse response = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse();
        //response.getWriter().print(response.getContentAsString());
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

        // post 提交一个 user
        request = post("/users/add")
                //.contentType(MediaType.APPLICATION_JSON)
                .contentType("application/json;charset=UTF-8")
                .content("{\"uid\":10,\"name\":\"门矢士\",\"age\":25}");
        // todo 测试响应的编码可以修改，但显示的中文乱码无法修改
        ResultActions actions = mvc.perform(request).andExpect(status().isOk());
        MockHttpServletResponse response = actions.andReturn().getResponse();
        //response.setContentType("text/text;charset=UTF-8");  // 无效
        //response.setHeader("Content-Type", "text/text;charset=UTF-8");  // 无效
        //System.out.println("content = " + new String(response.getContentAsString().getBytes(StandardCharsets.ISO_8859_1),
                //StandardCharsets.UTF_8));  // 无效
        //actions.andDo(MockMvcResultHandlers.print());
        actions.andExpect(content().string(equalTo("success")));

        // get 获取 users 列表
        request = get("/users/");
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"uid\":10,\"name\":\"门矢士\",\"age\":25}]")));

        // put 修改 user 信息
        request = put("/users/{id}", 10L)
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"终极门矢士\",\"age\":30}");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

        // get 获取一个 user
        request = get("/users/{id}", 10L);
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"uid\":10,\"name\":\"终极门矢士\",\"age\":30}")));

        // post 添加多个用户
        String requestJson = "[{\"uid\":16,\"name\":\"泊进之介\",\"age\":27}," +
                "{\"uid\":19,\"name\":\"桐生战兔\",\"age\":26}]";
        request = post("/users/addAll")
                .contentType("application/json;charset=UTF-8")
                .content(requestJson);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

        // get 获取 users 列表
        request = get("/users/");
        mvc.perform(request).andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
                //.andExpect(content().string(equalTo("[{\"uid\":10,\"name\":\"门矢士\",\"age\":25}]")));

        // delete 删除一个 user
        //request = delete("/users/{id}", 10L);
        //mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

    }

    /**
     * CRUD: 创建(Create)、更新(Update)、读取(Retrieve)和删除(Delete)
     */
    @Test
    //@Transactional  //开启事务
    //@Commit  // 测试中需要指定 @Commit，不然默认回滚 @Rollback
    public void testUserCrud() throws Exception {
        // 测试 UserController
        RequestBuilder request;

        //处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        request = get("/users/{id}", 2L)
                .header("Content-Type", "application/json,charset=UTF-8");
        mvc.perform(request).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testAdmin() throws Exception {
        // 测试 AdminController
        RequestBuilder request;

        //处理"/admins/{aid}"的GET请求，用来获取url中id值的Admin信息
        request = get("/admins/{aid}", "a1")
                .header("Content-Type", "application/json,charset=UTF-8");
        mvc.perform(request).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void generateAsciiDocs() throws Exception {

        URL remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");
        Path outputDirectory = Paths.get("src/docs/asciidoc/generated");

        // 输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .build();

        Swagger2MarkupConverter.from(remoteSwaggerFile)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
    }

    @Test
    public void generateMarkdownDocs() throws Exception {

        URL remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");
        Path outputDirectory = Paths.get("src/docs/markdown/generated");

        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .build();

        Swagger2MarkupConverter.from(remoteSwaggerFile)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
    }

    @Test
    public void generateConfluenceDocs() throws Exception {

        URL remoteSwaggerFile = new URL("http://localhost:8080/v2/api-docs");
        Path outputDirectory = Paths.get("src/docs/confluence/generated");

        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .build();

        Swagger2MarkupConverter.from(remoteSwaggerFile)
                .withConfig(config)
                .build()
                .toFolder(outputDirectory);
    }

}
