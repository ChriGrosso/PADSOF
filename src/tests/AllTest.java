package tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;


@SelectClasses ({AvionTest.class,
				AvionMercanciasTest.class,
				AvionPasajerosTest.class,
				VueloMercanciasTest.class,
				FacturaTest.class, 
				PagoTest.class,
				AeropuertoTest.class,
				TemporadaTest.class,
				NotificacionTest.class,
				AerolineaTest.class,
				SkyManagerTest.class,
				ControladorTest.class,
				OperadorTest.class,
				GestorTest.class,
				UsuarioTest.class,
				ObservableTest.class})
@Suite
public class AllTest {
}
