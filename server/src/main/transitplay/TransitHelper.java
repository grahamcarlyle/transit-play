package transitplay;

import com.cognitect.transit.Reader;
import com.cognitect.transit.TransitFactory;
import com.cognitect.transit.Writer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TransitHelper {
    public static OutputStream serialise(Object data) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Writer writer = TransitFactory.writer(TransitFactory.Format.JSON, baos);
	    writer.write(data);
        return baos;
    }

    public static <T> T deserialise(String data) {
	    ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        Reader reader = TransitFactory.reader(TransitFactory.Format.JSON, bais);
	    return (T)reader.read();
    }
}
