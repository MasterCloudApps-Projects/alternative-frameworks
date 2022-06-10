package org.acme.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleToUserForm {

    private String username;

    private String rolename;
}