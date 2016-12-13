package nl.unionsoft.sysstate.dao;

import java.util.List;

import nl.unionsoft.sysstate.dto.UserDto;

public interface UserDao {

    public UserDto getUser(String login);
    
    public UserDto getUserByToken(String token);

    public List<UserDto> getUsers();

    public void createOrUpdate(UserDto user);

    public UserDto getUser(Long userId);

    public void delete(Long userId);
    
    public boolean isValidPassword(final Long userId, String password) ;
}
