package info.bliki.api.creator;

import info.bliki.annotations.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@Category(IntegrationTest.class)
public class Dump2HTMLCreatorTest {
    private Dump2HTMLCreator creator;
    private File htmlDir;
    private File dbDir;

    @Before
    public void before() throws IOException {
        URL dump = getClass().getResource("/dump/enwiki-20150112-pages-articles1.xml");
        assertThat(dump).isNotNull();
        File testFile = new File(dump.getFile());
        File file = File.createTempFile("Dump2HTMLCreator", "test");
        file.delete();
        file.mkdirs();

        dbDir = new File(file, "db");
        htmlDir = new File(file, "html");
        creator = new Dump2HTMLCreator(testFile);
    }

    @Test
    public void importTemplatesAndModules() throws Exception {
        creator.dump(Dump2HTMLCreator.DumpMode.WRITE_TEMPLATES_AND_MODULES, dbDir, null, htmlDir);
        assertThat(dbDir).isDirectory();
        assertThat(htmlDir).doesNotExist();
    }

    @Test
    public void importAndRenderHTML() throws Exception {
        creator.dump(Dump2HTMLCreator.DumpMode.BOTH, dbDir, null, htmlDir);
        assertThat(dbDir).isDirectory();
        assertThat(htmlDir).isDirectory();
        assertThat(htmlDir.listFiles()).hasSize(3);
    }
}
