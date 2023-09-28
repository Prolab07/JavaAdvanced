package com.advanced.spring.unit;

import com.advanced.spring.dto.UserDTO;
import com.advanced.spring.entity.User;
import com.advanced.spring.utils.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void testToDTO() {
        // Підготовка
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");

        // Дія
        UserDTO dto = userMapper.toDTO(user);

        // Перевірка
        assertEquals(1L, dto.getId());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals("Test User", dto.getName());
    }

    @Test
    public void testToEntity() {
        // Підготовка
        UserDTO dto = new UserDTO();
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setName("Test User");

        // Дія
        User user = userMapper.toEntity(dto);

        // Перевірка
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getName());
    }
}
