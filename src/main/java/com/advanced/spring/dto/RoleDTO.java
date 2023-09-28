package com.advanced.spring.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class RoleDTO {
    private Long id;
    private String name;
}
