package chat;

import java.io.*;
import java.net.*;
import java.util.*;

// Client class
public class MultiThreadedClient {

    public static class Users{
        private String fullName;
        private String email;

        public Users(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class RequestBody{
        private String url;
        private String index;
        private Object object;

        public RequestBody(){}
        public RequestBody(String url, String index, Object object) {
            this.url = url;
            this.index = index;
            this.object = object;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }

    // driver code
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            ObjectOutputStream out = new ObjectOutputStream(
                    socket.getOutputStream());

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                Users users = new Users("gervais","gash@gmail.com");

                RequestBody myRequestBody = new RequestBody("/users","/post",users);
//                Users userSent = (Users) myRequestBody.getObject();
//                userSent.getEmail();
                /*
                        SAMPLE OF THE REQUEST AND RESPONSE
                        ----------------------------------
                        user: /users
                        response : table of users

                        user: /users/1
                        response : user with id that id
                 */

                // sending the user input to server
                out.writeObject(myRequestBody);
                out.flush();

                // displaying server reply
                System.out.println("Server replied "
                        + in.readLine());
            }

            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
