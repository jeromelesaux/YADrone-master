import de.yadrone.apps.controlcenter.YADroneControlCenter;
import org.junit.Test;

/**
 * Created by jlesaux on 21/09/15.
 * File ${FILE}
 */
public class IhmLoading {

    @Test
    public void testLoading() {
        YADroneControlCenter main = new YADroneControlCenter();
        String[] args={};
        //String[] args = {"--import-xls",CommandLinesTests.class.getResource("/92000064.xlsx").getPath()};
        main.main(args);
    }
}
