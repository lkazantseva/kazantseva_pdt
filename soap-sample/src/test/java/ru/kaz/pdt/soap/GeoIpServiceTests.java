package ru.kaz.pdt.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

  @Test
  public void testMyIp() {
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("79.136.217.78");
    assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>75</State></GeoIP>");
  }
}
