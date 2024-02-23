package at.htl.leoenergy.influxdb;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "influxdb")
public class InfluxDBConfig {
    public String url;
    public String token;
    public String org;
    public String bucket;
}
