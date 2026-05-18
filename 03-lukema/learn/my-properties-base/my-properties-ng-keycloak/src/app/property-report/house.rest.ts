export interface House {
    id: number;
    description: string;
    address: string;
    city: string;
    state: string;
    zip: string;
    purchaseDate: string;
    purchasePrice: number;
    currentPrice: number;
    mortgageBank: string;
    principlaBalance: number;
    interestRate: string;
    maturityDate: string;
    monthlyPrincipalPayment: number;
    monthlyInterestPayment: number;
    monthlyCountyPropertyTax: number;
    monthlyMUDTax: number;
    monthlyLIDTax: number;
    monthlyInsurancePayment: number;
    monthlyHOAPayment: number;
    calculatedMonthlyEscrow: number;
    actualMonthlyEscrow: number;
    monthlyManagementChargeRate: number;

    monthlyRentIncome: number;

    squareFootage: number;
    lotSizeSquareFeet: number;
    taxId: string;
    lotDemesion: string;
}
