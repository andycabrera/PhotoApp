package com.andycabrera.photoapp.api.users.data;

import java.util.List;

import com.andycabrera.photoapp.api.users.ui.models.AlbumResponseModel;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="albums-ws")
public interface AlbumsServiceCliente {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);
}
