package transitplay;

import com.cognitect.transit.Reader;
import com.cognitect.transit.TransitFactory;
import com.cognitect.transit.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static spark.Spark.*;

public class Example2Server {

    public static void main(String[] args) {

        externalStaticFileLocation("/home/gcarlyle/code/hacks/transit-play/client/example2");

        get("/getIt",(request, response) -> {
            Map<Object, Object> data = new HashMap<Object, Object>();
            data.put("uuid", UUID.randomUUID());
            data.put("bigDecimal", new BigDecimal("123456789087654321.123456789"));
            data.put(new Date(0), "wat?");
            response.type("application/transit+json");
            return TransitHelper.serialise(data);
	    });

        post("/postIt",(request, response) -> {
            String payload = request.body();
            Object data = TransitHelper.deserialise(payload);
            System.out.println("The client said " + data);
            return "Ok";
	    });

    }

}
