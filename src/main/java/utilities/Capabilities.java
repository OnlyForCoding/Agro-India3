package utilities;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Capabilities {
    private DesiredCapabilities desiredCapabilities = null;

    public DesiredCapabilities getBrowserCapabilities(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
        this.desiredCapabilities.acceptInsecureCerts();
        this.desiredCapabilities.setJavascriptEnabled(true);
        this.handleBrowser();
        this.handlePlatform();
        return desiredCapabilities;
    }

    private void handleBrowser(){
        if (System.getProperty("browser") != null) {
            String var1 = System.getProperty("browser").toLowerCase();
            byte var2 = -1;
            switch(var1.hashCode()) {
                case -1361128838:
                    if (var1.equals("chrome")) {
                        var2 = 0;
                    }
                    break;
                case -849452327:
                    if (var1.equals("firefox")) {
                        var2 = 1;
                    }
                    break;
                case 3356:
                    if (var1.equals("ie")) {
                        var2 = 2;
                    }
            }

            switch(var2) {
                case 0:
                    this.desiredCapabilities.setBrowserName("Chrome");
                    break;
                case 1:
                    this.desiredCapabilities.setBrowserName("firefox");
                    break;
                case 2:
                    this.desiredCapabilities.setBrowserName("internetExplorer");
                    break;
                default:
                    this.desiredCapabilities.setBrowserName("chrome");
            }
        }
    }

    private void handlePlatform() {
        if (System.getProperty("platform") != null) {
            if (System.getProperty("platform").toUpperCase().contains("WINDOWS")) {
                String var1 = System.getProperty("version");
                byte var2 = -1;
                switch(var1.hashCode()) {
                    case 55:
                        if (var1.equals("7")) {
                            var2 = 2;
                        }
                        break;
                    case 56:
                        if (var1.equals("8")) {
                            var2 = 0;
                        }
                        break;
                    case 1567:
                        if (var1.equals("10")) {
                            var2 = 1;
                        }
                }

                switch(var2) {
                    case 0:
                        Platform var10001 = Platform.WIN8;
                        this.desiredCapabilities.setPlatform(Platform.WIN8_1);
                        break;
                    case 1:
                        this.desiredCapabilities.setPlatform(Platform.WIN10);
                        break;
                    case 2:
                        this.desiredCapabilities.setPlatform(Platform.VISTA);
                        break;
                    default:
                        this.desiredCapabilities.setPlatform(Platform.WIN10);
                }
            } else if (System.getProperty("platform").toUpperCase().contains("MAC")) {
                this.desiredCapabilities.setPlatform(Platform.MAC);
            }
        }

    }
}
