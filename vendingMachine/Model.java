//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
//
// Class          : vendingMachine.Model
//
// Author         : Richard E. Pattis
//                  Computer Science Department
//                  Carnegie Mellon University
//                  5000 Forbes Avenue
//                  Pittsburgh, PA 15213-3891
//                  e-mail: pattis@cs.cmu.edu
//
// Maintainer     : Author
//
//
// Description:
//
//   The Model for the VendingMachine package implements the guts of the
// vending machine: it responds to presses of buttons created by the
// Conroller (deposit, cancel, buy), and tells the View when it needs
// to update its display (calling the update in view, which calls the
// accessor methods in this classes)
// 
//   Note that "no access modifier" means that the method is package
// friendly: this means the member is public to all other classes in
// the calculator package, but private elsewhere.
//
// Future Plans   : More Comments
//                  Increase price as stock goes down
//                  Decrease price if being outsold by competition
//                  Allow option to purchase even if full change cannot 
//                    be returned (purchaser pays a premium to quench thirst)
//                  Allow user to enter 2 x money and gamble: 1/2 time
//                    all money returned with product; 1/2 time no money and
//                    no product returned
//
// Program History:
//   9/20/01: R. Pattis - Operational for 15-100
//   2/10/02: R. Pattis - Fixed Bug in change-making method
//
//
//////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////


package vendingMachine;


import java.util.Scanner;


public class Model {
	//Define fields (all instance variables)
	private int cokeLeft;
	private int pepsiLeft;
	private int cokeCost;
	private int pepsiCost;
	View view;
	private int deposit;
	private int dimesLeft;
	private int quartersLeft;
	private int nickelsLeft;
    private int quartersDeposited = 0;
    private int nickelsDeposited = 0;
    private int dimesDeposited = 0;
	String message = "";
	public Model(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter quarters. \t to start(10)");
        try{
            quartersLeft = scanner.nextInt();
        }
        catch (Exception e){
            quartersLeft = 10;
        }
		System.out.println("Enter dimes. \t to start(10)");
        try{
            dimesLeft = scanner.nextInt();
        }
        catch (Exception e){
            dimesLeft = 10;
        }
        System.out.println("Enter nickels. \t to start(10)");
        try{
            nickelsLeft = scanner.nextInt();
        }
        catch (Exception e){
            nickelsLeft = 10;
        }
		System.out.println("Enter pepsi cost in cents(85)");
        try{
            pepsiCost = scanner.nextInt();
        }
        catch (Exception e){
            pepsiCost = 85;
        }
		System.out.println("Enter coke cost in cents (95)");
        try{
            cokeCost = scanner.nextInt();
        }
        catch (Exception e){
            cokeCost = 85;
        }
		System.out.println("Enter coke \t to start(5)");
        try{
            cokeLeft = scanner.nextInt();
        }
        catch (Exception e){
            cokeLeft = 5;
        }
		System.out.println("Enter pepsi \t to start(5)");
        try{
            pepsiLeft = scanner.nextInt();
        }
        catch (Exception e){
            pepsiLeft = 5;
        }
	}
	public void deposit(int amount){
        deposit+=amount;
        if (amount == 5){
            nickelsDeposited++;
            nickelsLeft++;
        }
        if (amount == 10){
            dimesDeposited++;
            dimesLeft++;
        }
        if (amount == 25){
            quartersDeposited++;
            quartersLeft++;
        }
        message = amount + " cents added.";
        view.update();
	}

	public String getDeposited(){
		return String.valueOf(deposit);
	}

	public void buy(String product){
		if (product.equals("Coke")){
            if (deposit < cokeCost){
                message = "Deposit more money.";
            }
            else{
                message = "Coke bought. Change: " + getChange(deposit-cokeCost);
                cokeLeft--;
                deposit = 0;
            }
        }
        if (product.equals("Pepsi")){
            if (deposit < pepsiCost){
                message = "Deposit more money.";
            }
            else{
                message = "Pepsi bought. Change: " + getChange(deposit-pepsiCost);
                pepsiLeft--;
                deposit = 0;
            }
        }
        view.update();
	}

    public void cancel(){
        quartersLeft -= quartersDeposited;
        nickelsLeft -= nickelsDeposited;
        dimesLeft -= dimesDeposited;
        quartersDeposited = 0;
        nickelsDeposited = 0;
        dimesDeposited = 0;
        deposit = 0;
        message = "deposit cancelled.";
        view.update();
    }
	private View view(){
        return view;
	}        // Model must tell View when to update itself

	public int  getCokeLeft(){
		return cokeLeft;
	}
	public int  getPepsiLeft(){
		return pepsiLeft;
	}

	public String getMessage(){
		return message;
	}

	public String getCokePrice(){
		return "" + cokeCost;
	}

	public String getPepsiPrice(){
		return "" + pepsiCost;
	}


	//Refer to the view (used to call update after each button press)
	public void addView(View v)
	{view = v;}

	public String toString()
	{
		return "Vending Machine State: \n" +
			"  Coke     Left      = " + cokeLeft     + "\n" +
			"  Pepsi    Left      = " + pepsiLeft    + "\n" +
			"  Quarters Left      = " + quartersLeft + "\n" +
			"  Dimes    Left      = " + dimesLeft    + "\n" +
			"  Nickels  Left      = " + nickelsLeft  + "\n";
		//Display any other instance variables that you declare too
	}
	
	//Define helper methods
    private String getChange(int n){
        int quarterNum = 0;
        int dimeNum = 0;
        int nickelNum = 0;
        String toReturn = "";
        while (n > 0){
            if (n >= 25 && quartersLeft > 0){
                quarterNum++;
                n-=25;
            }
            else if (n >=10 && dimesLeft > 0){
                dimeNum++;
                n-=10;
            }
            else{
                nickelNum++;
                n-=5;
            }
        }
        if (quarterNum > 0){
            toReturn += quarterNum + " quarters ";
        }
        if (dimeNum > 0){
            toReturn += dimeNum + " dimes ";
        }
        if (nickelNum > 0){
            toReturn += nickelNum + " nickels ";
        }
        return toReturn;
    }

}
