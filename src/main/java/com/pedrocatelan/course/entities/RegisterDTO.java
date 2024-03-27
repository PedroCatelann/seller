package com.pedrocatelan.course.entities;

import com.pedrocatelan.course.entities.enums.UserRoles;

public record RegisterDTO(String login, String name, String password, String phone) {
}
