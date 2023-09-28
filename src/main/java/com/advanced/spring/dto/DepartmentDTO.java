package com.advanced.spring.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DepartmentDTO {

    private Long id;
    private String name;
    private List<UserDTO> users;
}
