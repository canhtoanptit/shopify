package com.quyenbeo.shopifym.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ShopInfo entity.
 */
public class ShopInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String apiKey;

    @NotNull
    private String password;

    @NotNull
    private String url;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShopInfoDTO shopInfoDTO = (ShopInfoDTO) o;
        if (shopInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShopInfoDTO{" +
            "id=" + getId() +
            ", apiKey='" + getApiKey() + "'" +
            ", password='" + getPassword() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
