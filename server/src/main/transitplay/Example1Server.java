package transitplay;

import com.cognitect.transit.Reader;
import com.cognitect.transit.TransitFactory;
import com.cognitect.transit.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static spark.Spark.*;

public class Example1Server {

    public static void main(String[] args) {

        externalStaticFileLocation("/home/gcarlyle/code/hacks/transit-play/client/example1");

        get("/getIt",(request, response) -> {
            Date data = new Date(0);
            response.type("application/transit+json");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Writer writer = TransitFactory.writer(TransitFactory.Format.JSON, baos);
            writer.write(data);
            return baos.toString();
	    });

        post("/postIt",(request, response) -> {
            String payload = request.body();
            ByteArrayInputStream bais = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));
            Reader reader = TransitFactory.reader(TransitFactory.Format.JSON, bais);
            Date data = (Date)reader.read(); // pesky scala developers!
            System.out.println("The client said " + data);
            return "Ok";
	    });

    }

}
