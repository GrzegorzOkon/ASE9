package okon.ASE9;

import okon.ASE9.config.ProgramConfigReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramConfigReaderTest {
    @Test
    public void shouldSyaThatIPIsNotAbsent() {
        assertEquals(false, ProgramConfigReader.isIPAbsent("123.456.789.123:123[login,password]"));
        assertEquals(false, ProgramConfigReader.isIPAbsent("123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatIPIsAbsent() {
        assertEquals(true, ProgramConfigReader.isIPAbsent(":123[login,password]"));
    }

    @Test
    public void shouldSyaThatIPIsNotWrongFormat() {
        assertEquals(false, ProgramConfigReader.isIPWrongFormat("123.456.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatIPIsWrongFormat() {
        assertEquals(true, ProgramConfigReader.isIPWrongFormat("123..789.123:123[login,password]"));
        assertEquals(true, ProgramConfigReader.isIPWrongFormat("123.k.789.123:123[login,password]"));
        assertEquals(true, ProgramConfigReader.isIPWrongFormat(".123.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatPortIsNotAbsent() {
        assertEquals(false, ProgramConfigReader.isPortAbsent("123.456.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatPortIsAbsent() {
        assertEquals(true, ProgramConfigReader.isPortAbsent("123.456.789.123:[login,password]"));
        assertEquals(true, ProgramConfigReader.isPortAbsent("123.456.789.123:123login,password]"));
        assertEquals(true, ProgramConfigReader.isPortAbsent("123.456.789.123,123[login,password]"));
    }

    @Test
    public void shouldSyaThatPortIsNotWrongFormat() {
        assertEquals(false, ProgramConfigReader.isPortWrongFormat("123.456.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatPortIsWrongFormat() {
        assertEquals(true, ProgramConfigReader.isPortWrongFormat("123.456.789.123:kk[login,password]"));
        assertEquals(true, ProgramConfigReader.isPortWrongFormat("123.456.789.123:12,5[login,password]"));
        assertEquals(true, ProgramConfigReader.isPortWrongFormat("123.456.789.123:12.5[login,password]"));
    }

    @Test
    public void shouldSyaThatLoginIsNotAbsent() {
        assertEquals(false, ProgramConfigReader.isLoginAbsent("123.456.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatLoginIsAbsent() {
        assertEquals(true, ProgramConfigReader.isLoginAbsent("123.456.789.123:123[,password]"));
        assertEquals(true, ProgramConfigReader.isLoginAbsent("123.456.789.123:123login,password]"));
        assertEquals(true, ProgramConfigReader.isLoginAbsent("123.456.789.123:123[login password]"));
    }

    @Test
    public void shouldSyaThatLoginIsNotWrongFormat() {
        assertEquals(false, ProgramConfigReader.isLoginWrongFormat("123.456.789.123:123[login,password]"));
        assertEquals(false, ProgramConfigReader.isLoginWrongFormat("123.456.789.123:123[login5,password]"));
    }

    @Test
    public void shouldSyaThatLoginIsWrongFormat() {
        assertEquals(true, ProgramConfigReader.isLoginWrongFormat("123.456.789.123:123[4login,password]"));
        assertEquals(true, ProgramConfigReader.isLoginWrongFormat("123.456.789.123:123[log.in,password]"));
        assertEquals(true, ProgramConfigReader.isLoginWrongFormat("123.456.789.123:123[log)in,password]"));
    }

    @Test
    public void shouldSyaThatPasswordIsNotAbsent() {
        assertEquals(false, ProgramConfigReader.isPasswordAbsent("123.456.789.123:123[login,password]"));
    }

    @Test
    public void shouldSyaThatPasswordIsAbsent() {
        assertEquals(true, ProgramConfigReader.isPasswordAbsent("123.456.789.123:123[login,]"));
        assertEquals(true, ProgramConfigReader.isPasswordAbsent("123.456.789.123:123[login password]"));
        assertEquals(true, ProgramConfigReader.isPasswordAbsent("123.456.789.123:123[login,password"));
        assertEquals(true, ProgramConfigReader.isPasswordAbsent("123.456.789.123:123[login;password]"));
    }
}
