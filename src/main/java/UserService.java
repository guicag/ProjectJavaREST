import com.google.gson.Gson;
import model.User;

import java.util.ArrayList;

import static spark.Spark.*;
public class UserService {
    public static void main(String[] args) {
        ArrayList<User> listUsers = new ArrayList<>();

        listUsers.add(new User("12", "toto", "titi", "pop"));

        get("/users", (request, response) -> {
            response.header("content-type", "application/json");
            return new Gson().toJson(new Response(StatusResponse.SUCCESS, new Gson().toJsonTree(listUsers)));
        });

        get("users/:id", (request, response) -> {
            response.header("content-type", "application/json");
            User result = null;
            for (int i = 0; i < listUsers.size(); i++) {
                if (listUsers.get(i).getId().equals(request.params(":id"))) {result = listUsers.get(i);}
            }
            if (result == null) {
                return new Gson().toJson(new Response(StatusResponse.ERROR));
            } else {
                return new Gson().toJson(new Response(StatusResponse.SUCCESS, new Gson().toJsonTree(result)));
            }
        });

        post("users/:id", (request, response) -> {
            response.header("content-type", "application/json");
            listUsers.add(new Gson().fromJson(request.body(), User.class));
            return new Gson().toJson(new Response(StatusResponse.SUCCESS));
        });
    }
}
