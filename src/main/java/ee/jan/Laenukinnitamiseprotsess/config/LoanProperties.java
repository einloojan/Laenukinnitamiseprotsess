package ee.jan.Laenukinnitamiseprotsess.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "loan")
public class LoanProperties {

    private int maxCustomerAge;

    public int getMaxCustomerAge() {
        return maxCustomerAge;
    }

    public void setMaxCustomerAge(int maxCustomerAge) {
        this.maxCustomerAge = maxCustomerAge;
    }
}
