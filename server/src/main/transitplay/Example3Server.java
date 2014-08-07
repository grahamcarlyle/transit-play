package transitplay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

public class Example3Server {

    public static void main(String[] args) {

        externalStaticFileLocation("client/example3");

        get("/getIt",(request, response) -> {
            response.type("application/transit+json");
            return TransitHelper.serialise(new BigDecimal("123456789087654321.123456789"));
	    });

        post("/postIt",(request, response) -> {
            String payload = request.body();
            Object data = TransitHelper.deserialise(payload);
            System.out.println("The client said " + data + ", " + data.getClass());
            return "Ok";
	    });

    }

}
