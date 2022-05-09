package cat.uvic.teknos.m06.soccerhub.utilities;

import cat.uvic.teknos.m06.soccerhub.utilities.exceptions.SchemaLoaderException;
import cat.uvic.teknos.m06.soccerhub.utilities.xml.Schema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlSchemaLoades implements SchemaLoader{
    private final Schema schema;

    public XmlSchemaLoades(String path) {
        var xmlMapper = new XmlMapper();
        try {
            var xml = Files.readString(Path.of(path));
            schema = xmlMapper.readValue(xml, Schema.class);

        } catch (IOException e) {
            throw new SchemaLoaderException(e);
        }
    }

    @Override
    public void load() {
        if (schema.getVersion().equals("1.0.0"))  {
            var commands = schema.getCommands().length;
        }
    }
}
