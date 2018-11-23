//package ua.levelup.controller.rest;
//
//import ua.levelup.converter.todto.UserConverter;
//import ua.levelup.dao.UserDao;
//import ua.levelup.dao.impl.UserDaoImpl;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.RestException;
//import ua.levelup.model.User;
//import ua.levelup.controller.support.HeaderValidator;
//import ua.levelup.web.dto.view.UserViewDto;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.lang.reflect.Type;
//import java.util.*;
//
//@Path("/users")
//public class UserRestService {
//
//    private static final String ID = "/{id}";
//    private static final String EMAIL = "/email/{email}";
//
//    private UserDao userDao = new UserDaoImpl();
//
//    @HEAD
//    public Response check(@HeaderParam("Accept") String accept,
//                          @HeaderParam("Content-Type") String contentType) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            return Response.ok().build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        }
//    }
//
//    @GET
//    @Path(ID)
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getUserById(@HeaderParam("Accept") String accept,
//                                @HeaderParam("Content-Type") String contentType,
//                                @PathParam("id") int id) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            User user = userDao.getById(id);
//            UserViewDto userViewDto = UserConverter.asUserViewDto(user);
//            return Response.ok(getJson(userViewDto)).build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    @GET
//    @Path(EMAIL)
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getUserByEmail(@HeaderParam("Accept") String accept,
//                                   @HeaderParam("Content-Type") String contentType,
//                                   @PathParam("email") String email) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            User user = userDao.getByEmail(email);
//            UserViewDto userViewDto = UserConverter.asUserViewDto(user);
//            return Response.ok(getJson(userViewDto)).build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    @POST
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response addUser(@HeaderParam("Accept") String accept,
//                            @HeaderParam("Content-Type") String contentType,
//                            String userCreateDtoString) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            Type type = new TypeToken<User>() {
//            }.getType();
//            User user = (User) extractFromJson(userCreateDtoString, type);
//            UserValidator.validateNewUser(user);
//            userDao.add(user);
//            return Response.status(201).build(); //Created
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    @PUT
//    @Path(ID)
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response updateUser(@HeaderParam("Accept") String accept,
//                               @HeaderParam("Content-Type") String contentType,
//                               @PathParam("id") int id,
//                               String userCreateDtoString) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            Type type = new TypeToken<User>() {
//            }.getType();
//            User user = (User) extractFromJson(userCreateDtoString, type);
//            UserValidator.validateNewUser(user);
//            int count = userDao.updateWithPassword(id, user);
//            return Response.status(count == 0 ? 404 : 200).build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    @DELETE
//    @Path(ID)
//    public Response delete(@HeaderParam("Accept") String accept,
//                           @HeaderParam("Content-Type") String contentType,
//                           @PathParam("id") int id) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            int count = userDao.delete(id);
//            return Response.status(count == 0 ? 404 : 200).build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response getAllUsers(@HeaderParam("Accept") String accept,
//                                @HeaderParam("Content-Type") String contentType) {
//        try {
//            HeaderValidator.validateHeaders(accept, contentType);
//            List<User> users = userDao.getAllUsers();
//            List<UserViewDto> userViewDtos = new ArrayList<>();
//            for (User user : users) {
//                UserViewDto userViewDto = UserConverter.asUserViewDto(user);
//                userViewDtos.add(userViewDto);
//            }
//            return Response.ok(getJson(userViewDtos)).build();
//        } catch (RestException e) {
//            return Response.status(e.getHttpStatus()).build();
//        } catch (ApplicationException e) {
//            return getResponseOnApplicationException(e);
//        }
//    }
//
//    private Response getResponseOnApplicationException(Exception e) {
//        String message = e.getMessage();
//        Properties properties = MessageContainer.getMessageProperties();
//        if (message.equals(properties.getProperty("EMPTY_RESULTSET") + User.class)) {
//            return Response.status(404).build();
//        }
//        if (message.equals(properties.getProperty("FAILED_GET_USER"))
//                || message.equals(properties.getProperty("FAILED_UPDATE_USER"))
//                || message.equals(properties.getProperty("FAILED_ROLLBACK_USER_CHANGES"))
//                || message.equals(properties.getProperty("FAILED_UPDATE_USER_LIST"))
//                || message.equals(properties.getProperty("FAILED_GET_ALL_USERS"))) {
//            return Response.status(500).build();
//        }
//        if (message.equals(properties.getProperty("EMPTY_EMAIL"))
//                || message.equals(properties.getProperty("EMPTY_PASSWORD"))
//                || message.equals(properties.getProperty("EMPTY_USER_NAME"))
//                || message.contains(properties.getProperty("LONG_EMAIL"))
//                || message.contains(properties.getProperty("LONG_PASSWORD"))
//                || message.contains(properties.getProperty("LONG_USER_NAME"))
//                || message.contains(properties.getProperty("INVALID_EMAIL_FORMAT"))
//                || message.contains(properties.getProperty("AS_USER_CONVERTATION_ERROR"))
//                || message.contains(properties.getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"))) {
//            return Response.status(400).build();
//        }
//        return Response.status(400).build();
//    }
//
//    private Object extractFromJson(String json, Type type) {
//        GsonBuilder builder = new GsonBuilder();
//        return builder.create()
//                .fromJson(json, type);
//    }
//
//    private String getJson(Object object){
//        GsonBuilder builder = new GsonBuilder();
//        return builder.create().toJson(object);
//    }
//}