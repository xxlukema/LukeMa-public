package com.learn.element.bsft;


public class PsRequest {

    private Distributor distributor;

    public Distributor getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributor distributor) {
        this.distributor = distributor;
    }

    @Override
    public String toString() {
        return "PsRequest [distributor=" + distributor + "]";
    }

}
