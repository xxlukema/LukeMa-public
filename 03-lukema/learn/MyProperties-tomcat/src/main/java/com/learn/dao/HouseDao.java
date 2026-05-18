package com.learn.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.bean.House;


public class HouseDao {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger();

    private static List<House> HOUSE_LIST = null;

    private static final Lock LOCK = new ReentrantLock();
    
    public static final String DATE_UPDATED = "05/14/2018";

    public static List<House> getAllHouseList() {

        if (HOUSE_LIST == null) {

            try {
                LOCK.tryLock(2, TimeUnit.MINUTES);
                if (HOUSE_LIST == null) {

                    List<House> tempList = new ArrayList<>();

                    /**
                     * 1. Regal Oak Way
                     */
                    House house = new House();
                    tempList.add(house);

                    house.setId(1);
                    house.setDescription("Primary Residence");
                    house.setAddress("1307 Regal Oak Way");
                    house.setCity("Sugar Land");
                    house.setState("TX");
                    house.setZip("77479");
                    house.setPurchaseDate("02/01/2011");
                    house.setPurchasePrice(410_000);
                    house.setCurrentPrice(464_383);
                    house.setMortgageBank("FlagStar Bank");
                    house.setPrinciplaBalance(401_770);
                    house.setInterestRate(4.50f);
                    house.setMaturityDate("03/01/2047");
                    house.setMonthlyPrincipalPayment(545f);
                    house.setMonthlyInterestPayment(1_507f);
                    house.setMonthlyPropertyTax(1_246f);
                    house.setMonthlyMUDTax(177f);
                    house.setMonthlyLIDTax(243f);
                    house.setMonthlyInsurancePayment(143f);
                    house.setMonthlyHOAPayment(68f);
                    house.setMonthlyEscrow(1_389f);
                    house.setSquareFootage(3_950);
                    house.setLotSizeSquareFeet(8_776);
                    house.setTaxId("8707110010200907");
                    house.setLotDemesion("");

                    /**
                     * 2. Horseshoe Falls
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(2);
                    house.setDescription("Renting Property");
                    house.setAddress("5623 Horseshoe Falls");
                    house.setCity("Missouri City");
                    house.setState("TX");
                    house.setZip("77459");
                    house.setPurchaseDate("04/30/2007");
                    house.setPurchasePrice(256_000);
                    house.setCurrentPrice(300_786);
                    house.setMortgageBank("MDI/Cardinal Finance");
                    house.setPrinciplaBalance(222_838);
                    house.setInterestRate(4.615f);
                    house.setMaturityDate("05/01/2047");
                    house.setMonthlyPrincipalPayment(292f);
                    house.setMonthlyInterestPayment(858f);
                    house.setMonthlyPropertyTax(706f);
                    house.setMonthlyMUDTax(191f);
                    house.setMonthlyLIDTax(102f);
                    house.setMonthlyInsurancePayment(92f);
                    house.setMonthlyHOAPayment(87f);
                    house.setMonthlyEscrow(797f);
                    house.setMonthlyRentIncome(2_200f);
                    house.setSquareFootage(2_950);
                    house.setLotSizeSquareFeet(6_600);
                    house.setTaxId("8131010010150907");
                    house.setLotDemesion("");

                    /**
                     * 3. 22nd Ave
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(3);
                    house.setDescription("Renting Property");
                    house.setAddress("6421 22nd Ave");
                    house.setCity("Phoenix");
                    house.setState("AZ");
                    house.setZip("85041");
                    house.setPurchaseDate("06/22/2011");
                    house.setPurchasePrice(57_000);
                    house.setCurrentPrice(186_756);
                    house.setMortgageBank("MorningStar Mortgage LLC");
                    house.setPrinciplaBalance(55_896);
                    house.setInterestRate(4.37f);
                    house.setMaturityDate("01/01/2043");
                    house.setMonthlyPrincipalPayment(101f);
                    house.setMonthlyInterestPayment(204f);
                    house.setMonthlyPropertyTax(89f);
                    house.setMonthlyInsurancePayment(41f);
                    house.setMonthlyHOAPayment(37f);
                    house.setMonthlyEscrow(153f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_200f);
                    house.setSquareFootage(1_218);
                    house.setLotSizeSquareFeet(6_050);
                    house.setTaxId("105-86-028");
                    house.setLotDemesion("");

                    /**
                     * 4. Westchester
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(4);
                    house.setDescription("Renting Property");
                    house.setAddress("4530 E Westchester Dr");
                    house.setCity("Chandler");
                    house.setState("AZ");
                    house.setZip("85249");
                    house.setPurchaseDate("01/11/2013");
                    house.setPurchasePrice(196_000);
                    house.setCurrentPrice(297_507);
                    house.setMortgageBank("Cenlar MTG");
                    house.setPrinciplaBalance(124_821);
                    house.setInterestRate(3.75f);
                    house.setMaturityDate("02/01/2043");
                    house.setMonthlyPrincipalPayment(245f);
                    house.setMonthlyInterestPayment(391f);
                    house.setMonthlyPropertyTax(155f);
                    house.setMonthlyInsurancePayment(42f);
                    house.setMonthlyHOAPayment(40f);
                    house.setMonthlyEscrow(207f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_375f);
                    house.setSquareFootage(2_082);
                    house.setLotSizeSquareFeet(7_150);
                    house.setTaxId("313-09-574");
                    house.setLotDemesion("54 x 128");

                    /**
                     * 5. Chestnut
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(5);
                    house.setDescription("Renting Property");
                    house.setAddress("1369 E Chestnut Dr");
                    house.setCity("Gilbert");
                    house.setState("AZ");
                    house.setZip("85298");
                    house.setPurchaseDate("01/24/2013");
                    house.setPurchasePrice(220_000);
                    house.setCurrentPrice(313_647);
                    house.setMortgageBank("Cenlar MTG");
                    house.setPrinciplaBalance(139_836);
                    house.setInterestRate(3.625f);
                    house.setMaturityDate("02/01/2043");
                    house.setMonthlyPrincipalPayment(279f);
                    house.setMonthlyInterestPayment(423f);
                    house.setMonthlyPropertyTax(165f);
                    house.setMonthlyInsurancePayment(47f);
                    house.setMonthlyHOAPayment(68f);
                    house.setMonthlyEscrow(224f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_400f);
                    house.setSquareFootage(1_845);
                    house.setLotSizeSquareFeet(7_088);
                    house.setTaxId("304-72-138");
                    house.setLotDemesion("50 x 130");

                    /**
                     * 6. La Costa
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(6);
                    house.setDescription("Renting Property");
                    house.setAddress("937 E La Costa Pl");
                    house.setCity("Chandler");
                    house.setState("AZ");
                    house.setZip("85249");
                    house.setPurchaseDate("01/29/2013");
                    house.setPurchasePrice(241_000);
                    house.setCurrentPrice(337_335);
                    house.setMortgageBank("Cenlar MTG");
                    house.setPrinciplaBalance(153_847);
                    house.setInterestRate(3.75f);
                    house.setMaturityDate("02/01/2043");
                    house.setMonthlyPrincipalPayment(301f);
                    house.setMonthlyInterestPayment(481f);
                    house.setMonthlyPropertyTax(194f);
                    house.setMonthlyInsurancePayment(41f);
                    house.setMonthlyHOAPayment(159f);
                    house.setMonthlyEscrow(247f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_450f);
                    house.setSquareFootage(2_031);
                    house.setLotSizeSquareFeet(8_140);
                    house.setTaxId("303-58-521");
                    house.setLotDemesion("55 x 148");

                    /**
                     * 7. Cupertino
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(7);
                    house.setDescription("Renting Property");
                    house.setAddress("3750 S Cupertino Dr");
                    house.setCity("Gilbert");
                    house.setState("AZ");
                    house.setZip("85297");
                    house.setPurchaseDate("01/28/2013");
                    house.setPurchasePrice(225_000);
                    house.setCurrentPrice(343_010);
                    house.setMortgageBank("Cenlar MTG");
                    house.setPrinciplaBalance(143_289);
                    house.setInterestRate(3.75f);
                    house.setMaturityDate("02/01/2043");
                    house.setMonthlyPrincipalPayment(281f);
                    house.setMonthlyInterestPayment(449f);
                    house.setMonthlyPropertyTax(211f);
                    house.setMonthlyInsurancePayment(54f);
                    house.setMonthlyHOAPayment(38f);
                    house.setMonthlyEscrow(287f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_500f);
                    house.setSquareFootage(2_457);
                    house.setLotSizeSquareFeet(9_975);
                    house.setTaxId("309-24-795");
                    house.setLotDemesion("70 x 120");

                    /**
                     * 8. Indian Wells
                     */
                    house = new House();
                    tempList.add(house);

                    house.setId(8);
                    house.setDescription("Renting Property");
                    house.setAddress("720 E Indian Wells Pl");
                    house.setCity("Chandler");
                    house.setState("AZ");
                    house.setZip("85249");
                    house.setPurchaseDate("02/22/2013");
                    house.setPurchasePrice(250_590);
                    house.setCurrentPrice(389_743);
                    house.setMortgageBank("Cenlar MTG");
                    house.setPrinciplaBalance(237_178);
                    house.setInterestRate(4.625f);
                    house.setMaturityDate("04/01/2047");
                    house.setMonthlyPrincipalPayment(313f);
                    house.setMonthlyInterestPayment(914f);
                    house.setMonthlyPropertyTax(229f);
                    house.setMonthlyInsurancePayment(78f);
                    house.setMonthlyHOAPayment(159f);
                    house.setMonthlyEscrow(307f);
                    house.setMonthlyManagementChargeRate(0.08f);
                    house.setMonthlyRentIncome(1_550f);
                    house.setSquareFootage(2_458);
                    house.setLotSizeSquareFeet(8_775);
                    house.setTaxId("303-58-389");
                    house.setLotDemesion("65 x 135");

                    HOUSE_LIST = tempList;
                }
            } catch (InterruptedException e) {
            } finally {
                LOCK.unlock();
            }
        }

        return HOUSE_LIST;
    }

}
