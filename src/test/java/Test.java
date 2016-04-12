import com.jking31cs.CouchObject;
import com.jking31cs.CouchResponse;
import com.jking31cs.RestClient;
import com.jking31cs.annotations.CouchDB;

import java.io.IOException;

/**
 * Created by jking31cs on 4/11/16.
 */
@CouchDB("studentdb")
public class Test extends CouchObject{
    public String email;
    public String name;

    public static void main(String[] args) throws IOException {
        RestClient client= new RestClient("http://localhost:5984");
        Test t = client.get("b0cb63346a3de919227d0566e6001bac", Test.class);
        System.out.println(t.name);
        System.out.println(t.email);

        t.name = "TestUserEdit";
        CouchResponse response = client.put(t);
        System.out.println(response);
    }
}
