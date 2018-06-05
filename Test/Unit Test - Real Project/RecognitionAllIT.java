/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.javaanpr.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import net.sf.javaanpr.imageanalysis.CarSnapshot;
import net.sf.javaanpr.intelligence.Intelligence;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author kasper
 */
@RunWith(Parameterized.class)
public class RecognitionAllIT {

    private final File carPic;
    private final String expPlate;

    public RecognitionAllIT(File carPic, String expPlate) {
        this.carPic = carPic;
        this.expPlate = expPlate;
    }

    /**
     * Method to load all the snapshots from the folder.
     *
     * @return list of car pictures
     * @throws IOException IOException
     */
    @Parameters(name = "Testing: {0}")
    public static Collection<Object[]> testInputs() throws IOException {
        List<Object[]> snapList = new ArrayList<>();

        //Load snapshots from src folder.
        String snapshotDirPath = "src/test/resources/snapshots";
        String resultsPath = "src/test/resources/results.properties";
        Properties properties;
        try (InputStream resultsStream = new FileInputStream(new File(resultsPath))) {
            properties = new Properties();
            properties.load(resultsStream);
        }
        //Check that it succeded, and that size is bigger than 0.
        assertTrue(properties.size() > 0);

        //Load files into list.
        File snapshotDir = new File(snapshotDirPath);
        for (File snap : snapshotDir.listFiles()) {
            snapList.add(new Object[]{
                snap,
                properties.getProperty(snap.getName())
            });
        }
        return snapList;
    }

    /**
     * Method to test all car snapshots, giving more feedback for which pictures
     * that fails the test.
     *
     * @throws Exception Exception
     */
    @Test
    public void testAllSnapshots() throws Exception {
        Intelligence intel = new Intelligence();

        CarSnapshot carSnap = new CarSnapshot(new FileInputStream(carPic));

        String numberPlate = intel.recognize(carSnap);
        assertThat(numberPlate, is(expPlate));
    }
}
