package com.advanced.spring.it;

import com.advanced.spring.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
/*
запускає контекст додатка SpringBoot,але це не означає
що ви запускаєте повний сервер з відкритим портом для HTTP-запитів. Замість цього, Spring Boot створює "внутрішній" контекст для тестування.

Коли ви використовуєте MockMvc, ви маєте можливість симулювати HTTP-запити до ваших контролерів в межах цього внутрішнього контексту без реального створення мережевих з'єднань.
 */
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "USER")  // це симулює користувача з роллю USER. Змініть на ADMIN для симуляції адміністратора.
public class UserControllerIntegrationMockMvcTest {

   /*
   Spring автоматично забезпечує інстанцію MockMvc (вашого мок-контролера) для використання у тестах.
    MockMvc є частиною Spring MVC Test Framework і використовується для написання інтеграційних тестів для ваших контролерів
    без необхідності реального запуску сервера. Він дозволяє виконувати HTTP-запити до вашого контролера та перевіряти результати.

    Таким чином, використовуючи MockMvc, ви можете легко симулювати реальні HTTP-запити до вашого API та переконатися,
     що вони працюють так, як очікується, без необхідності запускати реальний сервер або надсилати справжні HTTP-запити.
    */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello user"));
    }
}
