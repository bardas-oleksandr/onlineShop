package ua.levelup.rest;

import ua.levelup.converter.UserConverter;
import ua.levelup.dao.UserDao;
import ua.levelup.dao.impl.UserDaoImpl;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.rest.support.HeaderValidator;
import ua.levelup.validator.UserValidator;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.UserDto;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Type;
import java.util.*;

@Path("/users")
public class UserRestService {

    private static final String ID = "/{id}";
    private static final String EMAIL = "/email/{email}";

    private UserDao userDao = new UserDaoImpl();

    @HEAD
    public Response check(@HeaderParam("Accept") String accept,
                          @HeaderParam("Content-Type") String contentType) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            return Response.ok().build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        }
    }

    @GET
    @Path(ID)
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserById(@HeaderParam("Accept") String accept,
                                @HeaderParam("Content-Type") String contentType,
                                @PathParam("id") int id) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            User user = userDao.getById(id);
            UserDto userViewDto = UserConverter.asUserViewDto(user);
            return Response.ok(getJson(userViewDto)).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @GET
    @Path(EMAIL)
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserByEmail(@HeaderParam("Accept") String accept,
                                   @HeaderParam("Content-Type") String contentType,
                                   @PathParam("email") String email) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            User user = userDao.getByEmail(email);
            UserDto userViewDto = UserConverter.asUserViewDto(user);
            return Response.ok(getJson(userViewDto)).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addUser(@HeaderParam("Accept") String accept,
                            @HeaderParam("Content-Type") String contentType,
                            String userCreateDtoString) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            Type type = new TypeToken<UserCreateDto>() {
            }.getType();
            UserCreateDto userCreateDto = (UserCreateDto) extractFromJson(userCreateDtoString, type);
            UserValidator.validateNewUser(userCreateDto);
            User user = UserConverter.asUser(userCreateDto);
            userDao.add(user);
            return Response.status(201).build(); //Created
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @PUT
    @Path(ID)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@HeaderParam("Accept") String accept,
                               @HeaderParam("Content-Type") String contentType,
                               @PathParam("id") int id,
                               String userCreateDtoString) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            Type type = new TypeToken<UserCreateDto>() {
            }.getType();
            UserCreateDto userCreateDto = (UserCreateDto) extractFromJson(userCreateDtoString, type);
            UserValidator.validateNewUser(userCreateDto);
            User user = UserConverter.asUser(userCreateDto);
            int count = userDao.update(id, user);
            return Response.status(count == 0 ? 404 : 200).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @DELETE
    @Path(ID)
    public Response delete(@HeaderParam("Accept") String accept,
                           @HeaderParam("Content-Type") String contentType,
                           @PathParam("id") int id) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            int count = userDao.delete(id);
            return Response.status(count == 0 ? 404 : 200).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsers(@HeaderParam("Accept") String accept,
                                @HeaderParam("Content-Type") String contentType) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            List<User> users = userDao.getAllUsers();
            List<UserDto> userViewDtos = new ArrayList<>();
            for (User user : users) {
                UserDto userViewDto = UserConverter.asUserViewDto(user);
                userViewDtos.add(userViewDto);
            }
            return Response.ok(getJson(userViewDtos)).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUsersList(@HeaderParam("Accept") String accept,
                                    @HeaderParam("Content-Type") String contentType,
                                    String mapUserCreateDtoString) {
        try {
            HeaderValidator.validateHeaders(accept, contentType);
            Type type = new TypeToken<HashMap<String,UserCreateDto>>() {
            }.getType();
            Map<String,UserCreateDto> userCreateDtoMap = (Map<String,UserCreateDto>) extractFromJson(
                    mapUserCreateDtoString, type);
            Set<String> emails = userCreateDtoMap.keySet();
            Map<String, User> userMap = new HashMap<>();
            for (String email: emails) {
                UserCreateDto userCreateDto = userCreateDtoMap.get(email);
                UserValidator.validateNewUser(userCreateDto);
                User user = UserConverter.asUser(userCreateDto);
                userMap.put(email, user);
            }
            int count = userDao.updateByMap(userMap);
            return Response.status(200).build();
        } catch (RestException e) {
            return Response.status(e.getHttpStatus()).build();
        } catch (ApplicationException e) {
            return getResponseOnApplicationException(e);
        }
    }

    private Response getResponseOnApplicationException(Exception e) {
        String message = e.getMessage();
        Properties properties = MessageHolder.getMessageProperties();
        if (message.equals(properties.getProperty("EMPTY_RESULTSET") + User.class)) {
            return Response.status(404).build();
        }
        if (message.equals(properties.getProperty("FAILED_GET_USER"))
                || message.equals(properties.getProperty("FAILED_UPDATE_USER"))
                || message.equals(properties.getProperty("FAILED_ROLLBACK_USER_CHANGES"))
                || message.equals(properties.getProperty("FAILED_UPDATE_USER_LIST"))
                || message.equals(properties.getProperty("FAILED_GET_ALL_USERS"))) {
            return Response.status(500).build();
        }
        if (message.equals(properties.getProperty("EMPTY_EMAIL"))
                || message.equals(properties.getProperty("EMPTY_PASSWORD"))
                || message.equals(properties.getProperty("EMPTY_USER_NAME"))
                || message.contains(properties.getProperty("LONG_EMAIL"))
                || message.contains(properties.getProperty("LONG_PASSWORD"))
                || message.contains(properties.getProperty("LONG_USER_NAME"))
                || message.contains(properties.getProperty("INVALID_EMAIL_FORMAT"))
                || message.contains(properties.getProperty("AS_USER_CONVERTATION_ERROR"))
                || message.contains(properties.getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"))) {
            return Response.status(400).build();
        }
        return Response.status(400).build();
    }

    private Object extractFromJson(String json, Type type) {
        GsonBuilder builder = new GsonBuilder();
        return builder.create()
                .fromJson(json, type);
    }

    private String getJson(Object object){
        GsonBuilder builder = new GsonBuilder();
        return builder.create().toJson(object);
    }
}