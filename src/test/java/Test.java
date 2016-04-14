import com.jking31cs.RestClient;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jking31cs on 4/11/16.
 */
public class Test {

    private static String[] firstNames = new String[] {
        "Jimmy",
        "Betty",
        "Donny",
        "Alex",
        "Allie",
        "Simon",
        "Tony",
        "Patrick",
        "Eric",
        "Ray"
    };

    private static String[] lastNames = new String[] {
        "Smith",
        "Doe",
        "Patel",
        "Henderson",
        "Bush",
        "Reeves",
        "Waters",
        "Barrett",
        "Arnold",
        "Williamson"
    };

    public static void main(String[] args) throws IOException {
        RestClient client= new RestClient("http://localhost:5984");
        Random random = new SecureRandom();
        System.out.println("Adding 100 users to DB.");
        for (int i=0; i < 100; i++) {
            User u = new User();
            u.setFirstName(firstNames[random.nextInt(10)]);
            u.setLastName(lastNames[random.nextInt(10)]);
            client.put(u);
        }
        System.out.println("Done");
    }
}
