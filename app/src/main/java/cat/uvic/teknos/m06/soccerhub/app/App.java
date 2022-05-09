package cat.uvic.teknos.m06.soccerhub.app;

import cat.uvic.teknos.m06.soccerhub.utilities.SchemaLoader;
import cat.uvic.teknos.m06.soccerhub.utilities.SingleLineCommandSchemaLoader;

public class App {
    public static void main(String[] args)  {

        loadSchema(new SingleLineCommandSchemaLoader("", null));
    }

    private static void loadSchema(SchemaLoader schemaloader) {
        schemaloader.load();
    }
}