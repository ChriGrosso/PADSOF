package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@SelectClasses ({AvionTest.class,
				AvionMercanciasTest.class,
				AvionPasajerosTest.class,
				VueloMercanciasTest.class,
				FacturaTest.class})
@Suite
public class AllTest {
}
