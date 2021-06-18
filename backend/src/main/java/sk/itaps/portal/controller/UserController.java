package sk.itaps.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sk.itaps.portal.domain.dto.*;
import sk.itaps.portal.domain.jpa.UserProfile;
import sk.itaps.portal.domain.mapper.Mapper;
import sk.itaps.portal.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Mapper mapper;

    @GetMapping
    @ResponseBody
    public List<UserDto> users() {
        return userService.getUsers().stream().map(mapper::userToUserDto).collect(Collectors.toList());
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserDefaultDto> usersList() {
        return userService.getUsers().stream().map(mapper::userToUserDefaultDto).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String registerUser(@Valid @RequestBody UserRegistrationDto userDto) {
//        User user = mapper.userRegistrationDtoToUser(userDto);
//        com.microsoft.graph.models.User adUser = mapper.userRegistrationDtoToAdUser(userDto);
        userService.register(userDto);
        //todo: vratit user objekt Dto
        return "OK";
//        return ResponseEntity.created();
    }
    /*
    @GetMapping("/profile")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getAllUserProfiles();
    }

    @GetMapping("/profile/{id}")
    public UserProfile getUserProfile(@PathVariable("id") int id) {
        return userProfileService.getUserProfile(id);
    }

    @DeleteMapping("/profile/{id}")
    public void deleteUserProfile(@PathVariable("id") int id) {
        userProfileService.delete(id);
    }

    @PostMapping("/profile")
    public int saveUserProfile(@RequestBody UserProfile userProfile) {
        userProfileService.saveOrUpdate(userProfile);
        return userProfile.getId();
    }*/

    @GetMapping("/{id}/profile")
    @ResponseBody
    public UserProfileDto getUserProfile(@PathVariable int id) {
        return mapper.userProfileToUserProfileDto(userService.getUserProfile(id));
    }

    @PutMapping("/{id}/profile")
    public UserProfile updateUserProfile(@PathVariable int id, @RequestBody UserProfileDto userProfileDto) {
        return userService.updateProfile(id, mapper.userProfileDtoToUserProfile(userProfileDto));
    }

    @GetMapping(value = "/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserPhoto(@PathVariable("id") String id) {
        return userService.getUserPhoto(id);
    }

    @PostMapping(value = "/{id}/photo")
    public void uploadUserPhoto(@RequestParam("photo") MultipartFile photo, Principal principal) {
        userService.uploadUserPhoto(photo, principal.getName());
    }

    @GetMapping("/group")
    public List<GroupDto> getGroups() {
        return userService.getAdGroup().stream().map(mapper::adGroupToGroupDto).collect(Collectors.toList());
    }
}
