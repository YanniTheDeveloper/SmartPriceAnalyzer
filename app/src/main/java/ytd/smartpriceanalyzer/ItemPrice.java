package ytd.smartpriceanalyzer;

public class ItemPrice {
    double buyRN = 0.0, profitRN = 0.0, shippingRN = 0.0, shippingYCN = 0.0, otherRN = 0.0, otherYCN = 0.0, agentRN = 0.0;

    public double getSumR() {
        return buyRN + profitRN + shippingRN + otherRN + agentRN;
    }

    public double getSumYC() {
        return shippingYCN + otherYCN;
    }

    public double getPrice() {

        return (double) Math.round(((getSumR() * Currency.getRate()) + getSumYC())*100)/100;
    }

    public ItemPrice() {
    }


    public ItemPrice(double buyRN, double profitRN, double shippingRN, double shippingYCN, double otherRN, double otherYCN, double agentRN, double rateN) {
        this.buyRN = buyRN;
        this.profitRN = profitRN;
        this.shippingRN = shippingRN;
        this.shippingYCN = shippingYCN;
        this.otherRN = otherRN;
        this.otherYCN = otherYCN;
        this.agentRN = agentRN;
    }

    public double getBuyRN() {
        return buyRN;
    }

    public void setBuyRN(double buyRN) {
        this.buyRN = buyRN;
    }

    public double getProfitRN() {
        return profitRN;
    }

    public void setProfitRN(double profitRN) {
        this.profitRN = profitRN;
    }

    public double getShippingRN() {
        return shippingRN;
    }

    public void setShippingRN(double shippingRN) {
        this.shippingRN = shippingRN;
    }

    public double getShippingYCN() {
        return shippingYCN;
    }

    public void setShippingYCN(double shippingYCN) {
        this.shippingYCN = shippingYCN;
    }

    public double getOtherRN() {
        return otherRN;
    }

    public void setOtherRN(double otherRN) {
        this.otherRN = otherRN;
    }

    public double getOtherYCN() {
        return otherYCN;
    }

    public void setOtherYCN(double otherYCN) {
        this.otherYCN = otherYCN;
    }

    public double getAgentRN() {
        return agentRN;
    }

    public void setAgentRN(double agentRN) {
        this.agentRN = agentRN;
    }


}
