package transitplay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.cognitect.transit.TransitFactory;
import com.cognitect.transit.Reader;
import com.cognitect.transit.Writer;
import static spark.Spark.externalStaticFileLocation;
import static spark.Spark.get;
import static spark.Spark.post;

public class PlayServer {

    public static void main(String[] args) {

        externalStaticFileLocation("client/play");
        get("/hello",(request, response) -> {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("myTime", new Date());
            response.type("application/transit+json");
            return serialise(data);
	});

        get("/farewell",(request, response) -> {
            response.type("application/json");
            return "{\"myTime\": \"2014-07-31T13:15:23Z\"}";
	});

        get("/moTypes",(request, response) -> {
            response.type("application/transit+json");
            return serialise(new BigDecimal("1213.123456789"));
	});

        post("/receive",(request, response) -> {
            Object o = deserialise(request.body());
            System.out.println("The client said " + o);
	    BigDecimal b = (BigDecimal)((Map)o).get("number");
	    System.out.println("The client said " + b);
            return "Ok";
	});

    }

    public static ByteArrayOutputStream serialise(Object data) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Writer writer = TransitFactory.writer(TransitFactory.Format.JSON,
                                                  baos);
	    writer.write(data);
            return baos;
    }

    public static Object deserialise(String data) {
	ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
            Reader reader = TransitFactory.reader(TransitFactory.Format.JSON, 
                                                  bais);
	    return reader.read();
    }

}
