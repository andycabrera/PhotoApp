package com.andycabrera.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.andycabrera.photoapp.api.users.data.AlbumsServiceCliente;
import com.andycabrera.photoapp.api.users.data.UserEntity;
import com.andycabrera.photoapp.api.users.data.UsersRepository;
import com.andycabrera.photoapp.api.users.shared.UserDto;
import com.andycabrera.photoapp.api.users.ui.models.AlbumResponseModel;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    // RestTemplate restTemplate;
    Environment environment;
    AlbumsServiceCliente albumServiceCliente;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, 
    AlbumsServiceCliente albumServiceCliente,Environment environment) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.albumServiceCliente = albumServiceCliente;
        this.environment = environment;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

        usersRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);
        
        if(userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email){
        UserEntity userEntity = usersRepository.findByEmail(email);

        if(userEntity==null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByUserId(String userId) {
        
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if(userEntity==null){
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        // String albumsUrl = String.format("http://desktop-tde9pq3.fibertel.com.ar:51994/users/%s/albums", userId);
        // // String albumsUrl = "http://desktop-tde9pq3.fibertel.com.ar:51994/users/df492e08-a507-4bfb-aef0-56b2f1a0be74/albums";

        // ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET,null, 
        //         new ParameterizedTypeReference<List<AlbumResponseModel>>(){
        // });
        // List<AlbumResponseModel> albumList = albumsListResponse.getBody();

        List<AlbumResponseModel> albumList = albumServiceCliente.getAlbums(userId);

        userDto.setAlbums(albumList);

        return userDto;
    }
    
}
