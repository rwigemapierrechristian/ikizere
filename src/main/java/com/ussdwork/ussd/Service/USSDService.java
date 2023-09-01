package com.ussdwork.ussd.Service;

import com.google.common.base.Strings;
import com.ussdwork.ussd.repository.*;
import com.ussdwork.ussd.model.*;

import org.checkerframework.checker.units.qual.min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class USSDService {
    @Autowired
   private UserRepository userRepository;
  @Autowired
    private AdminRepository adminRepository;
  @Autowired
  private MomoRepository momoRepository;
  @Autowired 
  private TransactionRepository transactionRepository;
    @Autowired 
  private LoanRepository loanRepository;
    @Autowired 
  private SavingsRepository savingsRepository;
  @Autowired
  private TotalRepository totalRepository;
    @Autowired
  private GroupsRepository groupsRepository;
    @Autowired
  private PendingRepository pendingRepository;
    @Autowired
  private WithdrawRepository withdrawRepository;
      @Autowired
  private LeaveRepository leaveRepository;


    public USSDService(UserRepository userRepository, AdminRepository adminRepository,MomoRepository momoRepository ,TransactionRepository transactionRepository, LoanRepository loanRepository, SavingsRepository savingsRepository, TotalRepository totalRepository,GroupsRepository groupsRepository, PendingRepository pendingRepository, WithdrawRepository withdrawRepository,LeaveRepository leaveRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.transactionRepository = transactionRepository;
        this.loanRepository = loanRepository;
        this.momoRepository = momoRepository;
        this.totalRepository = totalRepository;
        this.groupsRepository = groupsRepository;
        this.pendingRepository = pendingRepository;
        this.withdrawRepository = withdrawRepository;
        this.leaveRepository = leaveRepository;
    }

/*verify whether the number is registered or not.*/
    public String verifier(String num, String input) {
        User userFinder= userRepository.findByPhoneNumber(num);
        if(userFinder != null)
        {
        System.out.println("Verified");
        return processUSSDRequestEnglish(input, num);

        } else
        {
            return subProcess(input, num);
        }
    }

/*registration process.*/
    
    public String subProcess (String UI, String num) {
        if(Strings.isNullOrEmpty(UI))
        {
            return "CON Hi There, you are about to subscribe to Ikizere Savings Services.\n Press 1 to proceed : ";
        }
        else{
            return Continue(num ,UI);
        }
    }
    
    public String Continue(String num, String name) {
        String[] part = name.split("\\*");
                switch (part[0])
                {
                    case "1":
                        return processName(num,name);
                    default:
                        return "END Invalid Input";
                }

    }
    
    public String processName(String num, String name) {
        String Name = null;
        String[] namePart = name.split("\\*");
        if(namePart.length > 1){
            Name = namePart[1];
            System.out.println(Name);
        }
        else {
            return "CON Please Enter Your name:";
        }
        return THEpin(num,name);
    }
    
    public String THEpin(String num, String pin) {
        String PIN = null;
        String[] namePart = pin.split("\\*");
        String name = namePart[1];
        if(namePart.length > 2)
        {
            PIN = namePart[2];
            System.out.println(PIN);
            if(PIN.length() == 4)
            {
                System.out.println("verified");
            }
            else{
                return "END INVALID PIN. Try again please.";
            }
        }
        else {
            return "CON hello "+ name + ", please Enter a 4 digit PIN:";
        }
        return ConfirmPin(num,pin);
    }

    public String ConfirmPin(String num, String conpin) {
        String confirm = null;
        String conPin[] = conpin.split("\\*");
        String pin = conPin[2];
        if (conPin.length > 3)
        {
            confirm = conPin[3];
            if(confirm.equals(pin))
            {
                System.out.println("match");
            }
            else
            {
                return "END Pin does not match.";
            }
        } else {
            return "CON Please confirm your PIN.";
        }
        return CreateUser(num, conpin);
    }

    public String CreateUser(String num, String aUser) {
        String name = null;
        String[] userPart = aUser.split("\\*");
        name = userPart[1];
        String Pin = userPart[2];
        if (Pin.length()  == 4)
        {
            String language = "English";
            double depositAmount = 0;
            String group = "none";
            String groupId = "";
            String membershipId = "";
            String membershipStatus = "none";

            String number = num;
            ArrayList<User> newUser = new ArrayList<>();
            User newest = new User(name , number , Pin , depositAmount, group,groupId,membershipId,membershipStatus,  language);
            newUser.add(newest);
            userRepository.saveAll(newUser);
        } else {
            return "END Something went wrong";
        }

        return "END Hi " + name + ", your account has been created successfully. Welcome to Neon Mobile Services.";
    }


/*USSD menu*/
    public String processUSSDRequestEnglish(String userInput, String num) {
        String[] parts = userInput.split("\\*");
        String element = parts[parts.length -1];
        if (Strings.isNullOrEmpty(userInput) || element.equals("00") ) {
            User verifyStatus = userRepository.findByPhoneNumber(num);
            System.out.println("THIS ISSSSS THE NUMBER ===-------0==== " +num);
            if(verifyStatus.getMembershipStatus().equals("Team-Leader"))
            {
                return "CON Ikizere Savings Main Menu:\n1. Transfer Funds \n2.Check balance  \n3.Mini Statment \n4. Loan request \n5. Paymnet \n6. Reset PIN  \n7. Change Language \n8. savings Group. \n9.Group Details \n10. Pending-Approvals \n11. Manage group.";
            }
            else
            {
                return "CON Ikizere Savings Main Menu:\n1. Transfer Funds \n2.Check Balance  \n3.Mini Statement \n4. Loan Request \n5. Payment \n6. Reset PIN  \n7. Change Language \n8. savings Group.";
            }
           
        } else {
            return menuProcedures(userInput, num);
        }
    }

    public String menuProcedures(String input, String num){
        String[] parts = input.split("\\*");
        String choice = parts[0];
        System.out.println("The length of your array is == "+parts.length);
        for (int i = parts.length - 1; i >= 0; i--) {

            if(parts[i].equals("00"))
            {
                choice = parts[i+1];
                break;
            }
        }
        System.out.println("This is Part[0] ==== "+parts[0]);
        System.out.println("This is your choice === "+choice);
             switch (choice) {
            case "1":
                return processSendMoney(input,num,choice);
            case "2":
                return processCheckBalance(input,num,choice);
            case "3":
                return processMiniStatement(input,num,choice);
            case "4":
                return processLoanRequest(input,num,choice);
            case "5":
                 return processPayment(input,num,choice);
            case "6":
                return processChangePin(input,num,choice);
            case "7":
                return processChangeLanguage(input,num);
            case "8":
            return processRegister(input,num,choice);
            case "9":
            return processDetails(input,num,choice);
            case "10":
            return processReviewPending(input,num,choice);
            case "11":
            return processManageGroup(input,num,choice);
            default:
                return "END Invalid option";
        }
    }


    /* (1) Transfer Funds*/   
    private String processSendMoney(String input, String num, String choice) {
        User checker = userRepository.findByPhoneNumber(num);
        if(!checker.getSavingsGroup().equals("none"))
        {
       String phoneNumber = "";
       String[] amountPart= input.split("\\*");
       String targetElement = choice; 
       int index = -1;
       for (int i = amountPart.length - 1; i >= 0; i--) {
        if (amountPart[i].equals(targetElement)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = amountPart.length-1;
    int distance = Math.abs(lastIndex - index);
            String lastElement = amountPart[amountPart.length -1];
    if(distance == 1)
    {
        phoneNumber = lastElement;
    }
    else if(distance == 2)
    {
        phoneNumber = amountPart[amountPart.length -2];
    }
    else if(distance == 3)
    {
        phoneNumber = amountPart[amountPart.length -3];
    }
        if (!lastElement.equals(choice)) 
        {
            Momo momoCheck = momoRepository.findByPhonenumber(phoneNumber);
            if(Objects.isNull(momoCheck))
            {
                return"CON the number you passed is not  registered Momo number. \n \n \n00. Main Menu";
            }
            else
            {
                return send(input, phoneNumber, choice);
            }


        } else 
        {
            return "CON Enter your MoMo phone number :";
        }
        }
        else
        {
            return"CON Dear user please register to a group before you transfer funds. \n \n \n00. Main Menu";
        }

   }
  
   private String send(String input, String num,String choice) {

       double Amount;
       String phoneNumber = "";
       String[] amountPart= input.split("\\*");
       String targetElement = choice; 
       int index = -1;
       for (int i = amountPart.length - 1; i >= 0; i--) {
        if (amountPart[i].equals(targetElement)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = amountPart.length-1;
    int distance = Math.abs(lastIndex - index);
            String lastElement = amountPart[amountPart.length -1];
    if(distance == 1)
    {
        phoneNumber = lastElement;
    }
    else if(distance == 2)
    {
        phoneNumber = amountPart[amountPart.length -2];
    }
    else if(distance == 3)
    {
        phoneNumber = amountPart[amountPart.length -3];
    }
         Momo sender = momoRepository.findByPhonenumber(phoneNumber);
       if(!lastElement.equals(phoneNumber))
       {
           Amount = Double.parseDouble(amountPart[amountPart.length-1]);
           if(sender.getBalance() < Amount )
           {
               return "CON You do not have the sufficient balance to carry out this transaction  \n \n \n.00 Main Menu.";
           }

       }
       else {

        return "CON Enter the amount to transfer from " + phoneNumber + " " + "("+ sender.getName() + ")";
       }

        return Pin(input,num,choice);
   }

   private String Pin(String input, String num,String choice) {

       String pin = "";
      double Amount= 0;
       String phoneNumber = "";
       String[] part= input.split("\\*");
        String targetElement = choice; 
       int index = -1;
       for (int i = part.length - 1; i >= 0; i--) {
        if (part[i].equals(targetElement)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = part.length-1;
    int distance = Math.abs(lastIndex - index);
            String lastElement = part[part.length -1];
            System.out.println("This is the distance ==== "+distance);
           if(distance == 1)
    {
        phoneNumber = lastElement;
    }
    else if(distance == 2)
    {
        phoneNumber = part[part.length -2];
        Amount = Double.parseDouble(lastElement);
    }
    else if(distance == 3)
    {
        pin = lastElement;
        phoneNumber = part[part.length -3];
        Amount = Double.parseDouble(part[part.length-2]);
    }

        System.out.println("This is the number of the sender ==== "+phoneNumber);
         Momo sender = momoRepository.findByPhonenumber(phoneNumber);
       User transfer = userRepository.findByPhoneNumber(num);
       Total updateTotal = totalRepository.findByName("Total-Savings");
       String name = transfer.getName();
       String previous = part[part.length -2];
       System.out.println("This is previous ==== "+previous);
       int y =  (int) Amount;
       String x = Integer.toString(y);
       System.out.println("This is x ==== "+x);
       if (!lastElement.equals(x))
       {
           System.out.println(pin);
           System.out.println(transfer.getPin());
           if(pin.equals(transfer.getPin()))
           {
               double newSenderbalance = sender.getBalance() - Amount;
               sender.setBalance(newSenderbalance);
               momoRepository.save(sender);
               double newTransferbalance = transfer.getDepositAmount() + Amount;
               transfer.setDepositAmount(newTransferbalance);
               userRepository.save(transfer);
           } else {
               return "CON Invalid PIN \n  \n  \n.00 Main Menu";
           }

       }
       else{
               return "CON Enter Your PIN";
       }
       
            String transferNumber = num;
            String transferer = transfer.getName();
            String description = "Transered funds";
            double amount = Amount;
            LocalDateTime date = LocalDateTime.now();

            ArrayList<Transaction> newTransactions = new ArrayList<>();
            Transaction newest = new Transaction(transferNumber, transferer ,amount, date , description);
            newTransactions.add(newest);
            transactionRepository.saveAll(newTransactions);

            YearMonth currentYearMonth = YearMonth.now();
            String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));
            Savings verifyUser = savingsRepository.findByPhoneNumber(num);
            Groups group = groupsRepository.findByNumber(transfer.getGroupId());
            if(verifyUser == null)
            {
            ArrayList<Savings> newSavings = new ArrayList<>();
            Savings newestSavings = new Savings(transferNumber, transferer,month , amount);
            group.setTotalSavings(group.getTotalSavings() + amount);
            newSavings.add(newestSavings);
            savingsRepository.saveAll(newSavings);
            } else
            {
                Groups grp = groupsRepository.findByNumber(transfer.getGroupId());
                double addon = verifyUser.getAmount() + amount;
                grp.setTotalSavings(grp.getTotalSavings() + amount);
                System.out.println("this is the amount == "+grp.getTotalSavings());
                groupsRepository.save(grp);
                verifyUser.setAmount(addon);
                savingsRepository.save(verifyUser);
            }
            double newTotal = updateTotal.getTotal() + amount;
            updateTotal.setTotal(newTotal);
            totalRepository.save(updateTotal);



       return "CON Dear " +name+", your funds have been successfully transfered to your savings account. Dial 2 to check your new balance \n \n \n.00 Main Menu";
   }

/* (2) Check Balance */
    private String processCheckBalance(String input,String num, String choice) {
    String[] parts = input.split("\\*");
    User user = userRepository.findByPhoneNumber(num);
    if(!user.getSavingsGroup().equals("none"))
    {
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String pin ="";
    if(distance == 1)
    {
        pin =lastElement;
    }
    if(!lastElement.equals(choice))
    {
        if(pin.equals(user.getPin()))
        {
            System.out.println("This is the name of the user = "+user.getName());
            return "CON your balance is:"+ user.getDepositAmount()+"\n \n \n00. Main Menu";
        }
        else
        {
            return "CON Invalid PIN. \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON Enter your PIN:";
    }
    }
    else
    {
        return "CON Dear user,You need to be registered to a savings group before you gain access to this option. \n \n \n00. Main Menu";
    }



    }

    /* (3) Mini Statement */
    int i= -1;
private String processMiniStatement(String input, String num,String choice)

{
    String[]  part= input.split("\\*");
    User finder = userRepository.findByPhoneNumber(num);
    if(!finder.getSavingsGroup().equals("none"))
    {
        String lastElement = part[part.length -1];
       String pin="";
       String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--)
        {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }

    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    if(distance == 1)
    {
        pin = lastElement;
    }
    else if(distance == 2)
    {
        pin = part[part.length - 2];
    }
    else if(distance == 3)
    {
        pin = part[part.length - 3];
    }
        else if(distance == 4)
    {
        pin = part[part.length - 4];
    }
        else if(distance == 5)
    {
        pin = part[part.length - 5];
    }
    if(!lastElement.equals(choice))
    {
    
        if(pin.equals(finder.getPin()))
        {
            return processMin(input,num,choice);
        }
        else
        {
            return"CON Invalid PIN \n \n \n 00.Main menu";
        }
    }
    else
    {
        return"CON Enter your PIN: ";  
    }
    }
    else
    {
        return"CON Dear user, Please register to any savings group before accessing this option. \n \n \n00. Main Menu"; 
    }
}
   
private String processMin(String input, String num,String choice)
   {
    System.out.println("We are inside process Mini");
    String[] part = input.split("\\*");
    User finder = userRepository.findByPhoneNumber(num);
    String lastElement = part[part.length - 1];
           String pin="";
       String[] parts = input.split("\\*");
        int index = -1;

       for (int i = parts.length - 1; i >= 0; i--)
        {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
System.out.println("this is the index of choice"+index);
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    if(distance == 1)
    {
        pin = lastElement;
    }
    else if(distance == 2)
    {
        pin = part[part.length - 2];
    }
    else if(distance == 3)
    {
        pin = part[part.length - 3];
    }
        else if(distance == 4)
    {
        pin = part[part.length - 4];
    }
        else if(distance == 5)
    {
        pin = part[part.length - 5];
    }
    System.out.print("this is the value of PIN == "+pin+" and this is the value of the distance =" +distance+" bb ");
    List<Transaction> allTransactions = transactionRepository.findAll();
    ArrayList<CustomTransaction> filteredTransactions = new ArrayList<>();
    int count = 1;
    for (int i = allTransactions.size() - 1; i >= 0 && filteredTransactions.size() < 5; i--) 
    {
        Transaction transaction = allTransactions.get(i);
        if (num.equals(transaction.getNumber())) {
            CustomTransaction customTransaction = new CustomTransaction(
                count,
                transaction.getDateTime(),
                transaction.getDescription(),
                transaction.getAmount()
            );
            filteredTransactions.add(customTransaction);
            count++;
        }
    }
    System.out.print("this is the filtered array"+filteredTransactions.toString());
        if(lastElement.equals(finder.getPin()) || lastElement.equalsIgnoreCase("n") && filteredTransactions.size() != 0)
         {
        i=i+1;
            System.out.print("We are inside the final If !!!!!!!!!");
        CustomTransaction element = filteredTransactions.get(i);
        int n = filteredTransactions.size();
        LocalDateTime date = element.getDateTime();
        String description = element.getDescription();
        double amount = element.getAmount();
        System.out.println("this is the new I ==== "+i);
        if(element.getCount() == n)
        {
            
        return "CON Transactions ("+(i+1)+"/"+n+"):\n \n Date: "+ date+"\n Description:"+ description +" \n Amount:"+ amount+ "\n \n  \n 00.Main Menu";
        } else
        {
        return "CON Transactions ("+(i+1)+"/"+n+"):\n \n Date: "+ date+"\n Description:"+ description +" \n Amount:"+ amount+ "\n \n n. next \n \n 00.Main Menu";
        }
    } else
    {
        return "CON You have no Transactions. \n \n \n00. Main Menu";
    }

   }   

/* (4) Request loan*/
   private String processLoanRequest(String input, String num,String choice)
   {
        double amount;
        String[] parts = input.split("\\*");
       Loan loaner = loanRepository.findByPhonenumber(num);
       User verify = userRepository.findByPhoneNumber(num);
       if(!verify.getSavingsGroup().equals("none"))
       {
        Total updateTotal = totalRepository.findByName("Total-Savings");
       String groupId = verify.getGroupId();
       Groups finder = groupsRepository.findByNumber(groupId);
       String lastElement = parts[parts.length - 1] ;
        if (!lastElement.equals(choice)) {
            amount = Double.parseDouble(lastElement);
            if(loaner == null)
            {
                System.out.println("Your good to go.");
            }
            else if(loaner.getAmount() > 0)
            {
                return "CON Dear user, you have an unpaid balance of "+ loaner.getAmount()+", please pay up before you can request another loan. \n \n \n 00.Main menu";
            } else if(amount >= finder.getTotalSavings())
            {
                return "CON Dear user, Your group does not have sufficient funds to provide you with the amount you requested. \n \n \n 00.Main menu";
            }
        }
         else {
            return "CON Enter the amount you wish to be loaned: ";
        }
       return period(input, num,choice);
       }
       else
       {
        return "CON Dear user, Please register to any Savings group to access this option. \n \n \n00. Main Menu";
       }

   }

  
   private String period(String input, String num, String choice)
   {
    String[] parts = input.split("\\*");
    double amount = 0;
           int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    if(distance == 1)
    {
        amount = Double.parseDouble(lastElement);
    }
    int x= (int) amount;
    String back = Integer.toString(x);
    if(!lastElement.equals(back))
    {
        String period = lastElement;
        return duration(input,num,choice);

    }
    else
    {
        return "CON Enter the period of your Loan:\n1. Days \n2. Weeks \n3. Months";
    }
   }

   private String duration(String input,String num,String choice)
   {
    String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String period ="";
    if(distance == 2)
    {
        period = lastElement;
    }
    else if(distance == 3)
    {
        period = parts[parts.length - 2];
    }

    if(!lastElement.equals(period))
    {
        String duration = lastElement;
        return enterPin(input,num,choice);
    }
    
    else
    {
        if(period.equals("1"))
        {
            return"CON Enter the number of days:";
        }
        else if(period.equals("2"))
        {
            return "CON Enter the number of weeks:";
        }
        else if(period.equals("3"))
        {
            return"CON Enter the number of Months:";
        }
        else
        {
            return"CON Invalid input \n \n \n 00.Main Menu";
        }
    }
   }

   private String enterPin(String input, String num,String choice)
   {
        double amount=0;
       String pin;
       String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--)
        {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }

    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String period ="";
    int duration = 0;
    if(distance == 2)
    {
        amount = Double.parseDouble(parts[parts.length -2]);
        System.out.println("this is the amount xxxx "+amount);
        period = lastElement;
    }
    else if(distance == 3)
    {
        amount = Double.parseDouble(parts[parts.length -3]);
        System.out.println("this is the amount xxxx "+amount);
        period = parts[parts.length - 2];
        duration = Integer.parseInt(lastElement);
    }
    else if(distance == 4)
    {
        amount = Double.parseDouble(parts[parts.length -4]);
        System.out.println("this is the amount xxxx "+amount);
        period = parts[parts.length - 3];
        duration = Integer.parseInt(parts[parts.length - 2]);
    }
    
       User user = userRepository.findByPhoneNumber(num);
       Total updateTotal = totalRepository.findByName("Total-Savings");
       String groupId = user.getGroupId();
       Groups finder = groupsRepository.findByNumber(groupId);
       String back = Integer.toString(duration);
       if(!lastElement.equals(back))
       {
        pin = lastElement;
           if(pin.equals(user.getPin()))
     {
            String loanerNumber = num;
            String loaner = user.getName();
            String description = "Successfull loan Request";
            LocalDateTime date = LocalDateTime.now();
            double loan = (amount*0.1)+amount;
            ArrayList<Transaction> newTransactions = new ArrayList<>();
            Transaction newest = new Transaction(loanerNumber, loaner ,loan, date , description);
            newTransactions.add(newest);
            transactionRepository.saveAll(newTransactions);

            
       Loan userVerifier = loanRepository.findByPhonenumber(num);
               LocalDate now = LocalDate.now();
               LocalDate dueDate = calculateDueDate(now, period, duration);
       if(userVerifier == null)
       {
        if(distance == 4)
        {
            amount = Double.parseDouble(parts[parts.length -4]);
        }
            double interest = 0;
            
            System.out.println("this is the amount === "+amount);
            if(period.equals("1"))
        {
            loan = (amount*0.15*duration) + amount;
            interest = (amount*0.15*duration);
        }
        else if(period.equals("2"))
        {
            loan = (amount*0.12*duration) + amount;
            interest = (amount*0.12*duration);
        }
            else if(period.equals("3"))
        {
            loan = (amount*0.1*duration) + amount;
            interest = (amount*0.1*duration);
        }
            String status = "pending";
            ArrayList<Loan> newLoans = new ArrayList<>();
            Loan newer = new Loan(loanerNumber, loaner ,loan, interest, status,dueDate);
            newLoans.add(newer);
            loanRepository.saveAll(newLoans);
       }
       else
       {
        if(distance == 4)
        {
            amount = Double.parseDouble(parts[parts.length -4]);
        }
             loan = 0;
            System.out.println("this is the amount ==== "+amount);

            if(period.equals("1"))
        {
            loan = (amount*0.15*duration) + amount;

        }
        else if(period.equals("2"))
        {
            loan = (amount*0.12*duration) + amount;
        }
            else if(period.equals("3"))
        {
            loan = (amount*0.1*duration) + amount;
        }
        userVerifier.setAmount(amount);
        userVerifier.setInterest(loan- amount);
        userVerifier.setStatus("pending");
        loanRepository.save(userVerifier);
       }

            double newTotal = updateTotal.getTotal() - amount;
            double newGroupTotal = finder.getTotalSavings() - amount;
            updateTotal.setTotal(newTotal);
            finder.setTotalSavings(newGroupTotal);
            totalRepository.save(updateTotal);
            groupsRepository.save(finder);

        return "CON your loan request has been successfully processed and accepted. \n \n \n00. Main Menu";
           }
           else{
            return "END INVALID PIN";
           }

       }
       else {
        double loan = 0;
        double interest = 0;
        LocalDate date = LocalDate.now();
        LocalDate dueDate = calculateDueDate(date, period, duration);
        String length ="";
        if(period.equals("1"))
        {
            if(duration == 1)
            {
                length ="day";
            }
            else
            {
             length = "days"; 
            }
            loan = (amount*0.15) + amount;
            interest = (amount*0.15);
        }
        else if(period.equals("2"))
        {
            if(duration == 1)
            {
                length ="week";
            }
            else
            {
             length = "weeks"; 
            }
            loan = (amount*0.12*duration) + amount;
            interest = (amount*0.12*duration);
        }
            else if(period.equals("3"))
        {
            if(duration == 1)
            {
                length ="month";
            }
            else
            {
             length = "months"; 
            }
            loan = (amount*0.1*duration) + amount;
            interest = (amount*0.1*duration);
        }
            return "CON your about to borrow the amount of "+ amount +" rwf. the duration of your loan is "+duration+" "+length+" An interest of "+interest+"percent will be charged on the final payment which will be due on "+dueDate+". Your total payment will amount to : "+loan+" rwf. Please Enter your PIN to confirm the loan request.";
       }
   }

   public static LocalDate calculateDueDate(LocalDate startDate, String period, int duration) {
    LocalDate dueDate = startDate;

    switch (period) {
        case "1":
            dueDate = dueDate.plusDays(duration);
            break;
        case "3":
            dueDate = dueDate.plusMonths(duration);
            break;
        case "2":
            dueDate = dueDate.plusWeeks(duration);
            break;
        default:
            System.out.println("Invalid period entered. Defaulting to days.");
            dueDate = dueDate.plusDays(duration);
    }

    return dueDate;
}

/* (5) pay Loan */ 
   private String processPayment(String input, String num,String choice)
   {
        double amount;
        String[] parts = input.split("\\*");
        User user = userRepository.findByPhoneNumber(num);
        if(!user.getSavingsGroup().equals("none"))
        {
            
       Loan loaner = loanRepository.findByPhonenumber(num);
       Savings addSavings = savingsRepository.findByPhoneNumber(num);
       String lastElement = parts[parts.length - 1] ;
if (loaner.getAmount() == 0)
{
    return "CON Dear user, you have no debt to clear. If you wish to request a Loan please press 2 \n \n \n 00.Main Menu";
}

 else
 {
        if (!lastElement.equals(choice)) {
            amount = Double.parseDouble(lastElement);
        }
         else {
            return "CON Enter the amount you wish to be pay: ";
        }
 }

       return payPin(input, num,choice);
        }
        else
        {
            return "CON Dear user, Please register to any Savings group to access this option. \n \n \n00. Main Menu";
        }
   }

   private String payPin(String input, String num,String choice)
   {
       double amount = 0;
       String pin ="";
       String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    if(distance == 1)
    {
        amount = Double.parseDouble(lastElement);
    }
    else if(distance == 2)
    {
        amount = Double.parseDouble(parts[parts.length - 2]);
    }
       User user = userRepository.findByPhoneNumber(num);
       Total updateTotal = totalRepository.findByName("Total-Savings");
       Momo payer = momoRepository.findByPhonenumber(num);
       int con = (int) amount;
       String back = Integer.toString(con);
       if(!lastElement.equals(back))
       {
        if(payer != null && amount < payer.getBalance())
        {
           pin = lastElement;
           if(pin.equals(user.getPin()))
           {
            String loanerNumber = num;
            String loaner = user.getName();
            String description = "Loan payment";
            LocalDateTime date = LocalDateTime.now();
            ArrayList<Transaction> newTransactions = new ArrayList<>();
            Transaction newest = new Transaction(loanerNumber, loaner ,amount, date , description);
            newTransactions.add(newest);
            transactionRepository.saveAll(newTransactions);
            Double newBalance = payer.getBalance() - amount;
            payer.setBalance(newBalance);
            momoRepository.save(payer);
           }
           else
           {
            return "END INVALID PIN";
           }
        } else
        {
            return "END Dear user, you do not have sufficient funds on your Momo account to perform this transacation.";
        }

       }
       else 
       {

            return "CON Please Enter your PIN to confirm your payment:";
       }
       Loan loanMan = loanRepository.findByPhonenumber(num);

       double remaining = loanMan.getInterest() - amount;
       double remainingAmount = loanMan.getAmount();

       System.out.println("this is the amount after interest is taken out xxxx "+remaining);
       if(remaining< 1)
       {
               Double convert= remaining*(-1);
              System.out.println("this is the remaing amount after conversion xxxx "+remaining);
              remainingAmount = loanMan.getAmount() - convert;
              System.out.println("this is the total loan left xxxx "+remaining);
             
       if(remainingAmount < 1)
         {
        double newSavings = remainingAmount *(-1);
        System.out.println("i am in the loop ==== " + newSavings);
        Savings addSavings = savingsRepository.findByPhoneNumber(num);
        User adddeposit = userRepository.findByPhoneNumber(num);
        double addon = addSavings.getAmount() + newSavings;
        System.out.println("this is the additional savings === " + addon);
        adddeposit.setDepositAmount(addon);
        userRepository.save(adddeposit);
        addSavings.setAmount(addon);
        savingsRepository.save(addSavings);

        loanMan.setAmount(0);
        loanMan.setInterest(0);
        loanMan.setStatus("paid");
        loanRepository.save(loanMan);
        double newTotal = updateTotal.getTotal() + amount;
        updateTotal.setTotal(newTotal);
        totalRepository.save(updateTotal);
       }
       else
       {
        loanMan.setAmount(remainingAmount);
        loanMan.setInterest(0);
        loanMan.setStatus("pending");
        loanRepository.save(loanMan);
        double newTotal = updateTotal.getTotal() + amount;
        updateTotal.setTotal(newTotal);
        totalRepository.save(updateTotal);
       }
       }
       else{
            loanMan.setInterest(remaining);
            loanMan.setStatus("pending");
            loanRepository.save(loanMan);
            double newTotal = updateTotal.getTotal() + amount;
            updateTotal.setTotal(newTotal);
            totalRepository.save(updateTotal);
       }

        return "END you have successfully paid the amount of :"+amount+"to cover your loan. You loan balance is now"+loanMan.getAmount()+" rwf.";
   }

/* (6) change PIN*/   
    private String processChangePin(String UI, String num, String choice) {
        String oldPin = null;
        String newPin = null;
        String[] part = UI.split("\\*");
        System.out.println("Here is the  number ==== "+num);
        String lastElement = part[part.length-1];
        User changer = userRepository.findByPhoneNumber(num);
        
        if(!lastElement.equals(choice))
        {
                    int index = -1;
       for (int i = part.length - 1; i >= 0; i--) {
        if (part[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = part.length-1;
    int distance = Math.abs(lastIndex - index);
    if(distance == 1)
    {
        oldPin = lastElement;
    }
    else if(distance == 2)
    {
        oldPin = part[part.length - 2];
    }
        else if(distance == 3)
    {
        oldPin = part[part.length - 3];
        newPin = part[part.length - 2];

    }
            if (oldPin.equals(changer.getPin()))
            {
                System.out.println("valid");
            }else{
                System.out.println("This is the old Pin === "+oldPin);
                System.out.println("This is the distance === "+distance);
                return "CON Invalid PIN. \n \n \n00. Main Menu";
            }
        }
        else{
            return "CON Please Enter your old 4 digit pin:";
        }
        return newPin(UI, num,choice);
    }

    private String newPin(String pin,String num, String choice) {
        String newpin = null;
        String[] parts = pin.split("\\*");
        
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String oldPin ="";
    if(distance == 1)
    {
        oldPin = lastElement;
    }
    else if(distance == 2)
    {
        oldPin = parts[parts.length - 2];
    }
        else if(distance == 3)
    {
        oldPin = parts[parts.length - 3];
        newpin = parts[parts.length - 2];

    }
        if (!lastElement.equals(oldPin))
        {
            newpin = lastElement;
            if(newpin.length() == 4)
            {
                System.out.println("valid");
            } else {
                boolean test = newpin.length() == 4;
                System.out.println(test);
                return "CON Invalid PIN. Please make sure you entered a 4 digit PIN. \n \n \n00. Main Menu";
            }
        }
        else {
            return "CON Enter your new 4 digit PIN.";
        }
        return reset(pin, num,choice);
    }

    private String reset(String Pin, String num,String choice) {
        String conPin = null;
        String[] parts = Pin.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
        String newPin ="";
    if(distance == 2)
    {
        newPin = lastElement;
    }
    else if(distance == 3)
    {
        newPin = parts[parts.length - 2];
    }
        System.out.println("new pin === " + newPin);
        User changer = userRepository.findByPhoneNumber(num);
        String name = changer.getName();
        if (distance == 3)
        {
            conPin = lastElement;
            System.out.println(" confirm ==== " + conPin);
            boolean verifier = conPin.equals(newPin);
            System.out.println(verifier);
            if (conPin.equals(newPin))
            {
                System.out.println("match");
            }
            else {
                return "CON The PIN you just Entered those not match your previous input. \n \n \n00. Main Menu";
            }

        } else {
            return "CON confirm your new PIN please:";
        }
        changer.setNewPin(newPin);
        userRepository.save(changer);
        return "CON Hi "+name+ ", your PIN has been reset successfully. \n \n \n00. Main Menu";
    }

/* (7) Change Language */
    private String processChangeLanguage(String input, String num)
    {

        String[] part = input.split("\\*");
        if (part.length > 1)
        {
            switch (part[1])
            {
                case "1":
                    return processKinyarwanda(num,input);
                case "2":
                    return processFrancais(num,input);
                case "3":
                    return processEnglish(num,input);
                default:
                    return "END Invalid Input";
            }
        }
        else{
            return "CON Choose A language: \n1. Kinyarwanda \n2. Francais \n3. English \n \n \n00. Main Menu";
        }
    }

    private String processKinyarwanda(String num, String input)
    {
        User changer = userRepository.findByPhoneNumber(num);
        changer.setLanguage("kinyarwanda");
        userRepository.save(changer);
        return "CON Wahisemo I Kinyarwanda \n \n \n00. Main Menu";
    }

    private String processFrancais(String num, String input)
    {
        User changer = userRepository.findByPhoneNumber(num);
        changer.setLanguage("Francais");
        userRepository.save(changer);
        return "CON Vous avais choisis Le Francais \n \n \n00. Main Menu";
    }

    private String processEnglish(String num, String input)
    {
        User changer = userRepository.findByPhoneNumber(num);
        changer.setLanguage("English");
        userRepository.save(changer);
        return "CON You have choosen English \n \n \n00. Main Menu" ;
    }

/*(8) register for group */
private String processRegister(String input, String num,String choice)
{

        String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        selection = parts[parts.length - 2];
    }
        else if(distance == 3)
    {
        selection = parts[parts.length - 3];
    }
        else if(distance == 4)
    {
        selection = parts[parts.length - 4];
    }
        else if(distance == 5)
    {
         selection = parts[parts.length - 5];
    }
    User verifier = userRepository.findByPhoneNumber(num);
    String id = verifier.getGroupId();

if(!lastElement.equals(choice))
{
    switch(selection)
    {
        case "1":
        return processView(input,num,choice);
        case "2":
        return processRegisterToGroup(input,num,choice);
        case "3":
        return processWithdraw(input,num,choice);
        case "4":
        return processLeave(input,num,choice);
        case "0":
        return processCreateGroup(input,num,choice);
        default:
        return "CON invalid option \n \n \n00.Main Menu";
    }
}
else
{
    return"CON Group options:\n1. View group \n2.Register to Group\n3.Withdraw Amount\n4.Leave Group\n0. Create Group \n \n \n00.Main Menu.";
}

}

private String processCreateGroup(String input, String num, String choice) 
{
    
    String[] parts = input.split("\\*");
    User verifyMember = userRepository.findByPhoneNumber(num);
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    String name = "";
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        name = lastElement;
        selection = parts[parts.length - 2];
    }
        else if(distance == 3)
    {
        name = parts[parts.length-2];
        selection = parts[parts.length - 3];
    }

    if(!lastElement.equals(selection))
    {
        return processCompleteCreate(input,num,choice);
    }
    else
    {
        if(!verifyMember.getSavingsGroup().equals("none"))
        {
            return"CON Dear user you are already a member of "+verifyMember.getSavingsGroup()+" savings group. If you wish to create a new group, Please fill a leave request and create your own group. \n \n \n00. Main Menu";
        }
        else
        {
                    return "CON Enter the group name: ";
        }
    }
}

private String processView(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    String pin = "";
    User finder = userRepository.findByPhoneNumber(num);
    Groups total = groupsRepository.findByNumber(finder.getGroupId());
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        pin = lastElement;
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        pin = parts[parts.length -2];
        selection = parts[parts.length - 3];
    }
   if(!lastElement.equals(selection))
   {
    if(pin.equals(finder.getPin()))
    {
        if(!finder.getSavingsGroup().equals("none"))
        {
            String grpName = finder.getSavingsGroup();
            String grpId = finder.getGroupId();
            String MemberId = finder.getMembershipId();
            double savings = finder.getDepositAmount();
            return "CON Group Details:\n Group Name: "+grpName+"\n Group ID: "+grpId+"\n Membership ID: "+MemberId+"\n Total-Savings: "+savings+"\n \n \n00. Main Menu";
        }
        else
        {
            return "CON Dear User, you are currently not register in any Savings Group. \n \n \n00.Main Menu";
        }
    }
    else
    {
        return "CON Invalid PIN \n \n \n00. Main Menu";
    }
   }
   else
   {
        return" CON Enter your PIN: ";
   }
}

private String processRegisterToGroup(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    String groupId = "";
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        groupId = lastElement;
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        groupId = parts[parts.length -2];
        selection = parts[parts.length - 3];
    }

    if(!lastElement.equals(selection))
    {
        Groups verify = groupsRepository.findByNumber(groupId);
        if(!Objects.isNull(verify))
        {
        return confirmRegistration(input,num,choice);
        }
        else
        {
            return "CON Dear user, the group ID you just entered "+groupId+" does not match any registered group. Please verify the group ID of the group you wish to join and try Again \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON Enter the Group ID of the group you wish to join: ";
    }
}

private String confirmRegistration(String input, String num, String choice)
{

    String[] parts = input.split("\\*");
    String pin = "";
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    String groupId = "";
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        groupId = lastElement;
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        groupId = parts[parts.length -2];
        selection = parts[parts.length - 3];
    }
    Groups groupName = groupsRepository.findByNumber(groupId);
    String Name = groupName.getName();
    if(!lastElement.equals(groupId))
    {
        pin = lastElement;
        if(pin.equals(finder.getPin()))
        {
            Pending verifier = pendingRepository.findByPhonenumber(num);
            boolean look = Objects.isNull(verifier);
            System.out.println("xxxxxxxxxxxxxxxxx"+look);


            if(Objects.isNull(verifier))
            {
             String name = finder.getName();
            String number = finder.getPhoneNumber();
            ArrayList<Pending> newPendings = new ArrayList<>();
            Pending newest = new Pending(name, number, groupId);
            newPendings.add(newest);
            pendingRepository.saveAll(newPendings);

            return "CON Dear user, Your request to join "+Name+" group with the group ID "+groupId+" has been filled succesfully. \n \n \n00. Main Menu";
               
            }
            else
            {
                Groups  grpName = groupsRepository.findByNumber(verifier.getGroupId());
                 return "CON Dear user your request to join "+grpName.getGroupName()+" with the group ID "+grpName.getGroupNumber()+" is still pending. Please wait until you Request is no longer pending. \n \n \n00. Main Menu";
            }

        } 
        else
        {
            return "CON Invalid PIN \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON You are about to join "+Name +" group. To confirm your request please enter your PIN:";
    }
}

private String processWithdraw(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    Loan verifyLoan = loanRepository.findByPhonenumber(num);
    User verifySavingsAmount = userRepository.findByPhoneNumber(num);
    String selection = "";
    double amount = 0;
    
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        amount = Double.parseDouble(lastElement);
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        amount = Double.parseDouble(parts[parts.length -2]);
        selection = parts[parts.length - 3];
    }
            else if(distance == 4)
    {
        amount = Double.parseDouble(parts[parts.length -3]);
        selection = parts[parts.length - 4];
    }
            else if(distance == 5)
    {
        amount = Double.parseDouble(parts[parts.length -4]);
        selection = parts[parts.length - 5];
    }

    if(!lastElement.equals(selection))
    {
        if(amount <= finder.getDepositAmount())
        {
            if( Objects.isNull(verifyLoan) ||verifyLoan.getAmount() == 0 )
            {
                return processMomoNum(input,num,choice);
            } 
            else
            {

                return "CON Dear user, You have an unpaid loan of "+verifyLoan.getAmount()+". Please clear the loan before you request to withdraw. \n \n \n00. Main Menu";
            }
        }
        else{
                System.out.println("the amount === "+amount);
                System.out.println("this is the users total deposit"+ finder.getDepositAmount());
            return "CON Dear user you do not have sufficient funds in your personal savings to withdraw the amount you requested. Please return to the main Menu and press 4 to verify your current deposit total.\n \n \n00. Main Menu";
        }
    }
    else{
        if(!finder.getSavingsGroup().equals("none"))
        {
            return"CON Enter the amount you would like to withdraw: ";
        }
        else
        {
            return "CON Dear User, you are currently not register in any Savings Group. \n \n \n00.Main Menu";
        }

    }
}

private String processMomoNum(String input, String num, String choice)
{
     String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    Loan verifyLoan = loanRepository.findByPhonenumber(num);
    User verifySavingsAmount = userRepository.findByPhoneNumber(num);
    String selection = "";
    double amount = 0;
    String momoNum = "";
    
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        amount = Double.parseDouble(lastElement);
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        momoNum = lastElement;
        amount = Double.parseDouble(parts[parts.length -2]);
        selection = parts[parts.length - 3];
    }
            else if(distance == 4)
    {
        momoNum = parts[parts.length -2];
        amount = Double.parseDouble(parts[parts.length -3]);
        selection = parts[parts.length - 4];
    }
            else if(distance == 5)
    {
        momoNum = parts[parts.length -3];
        amount = Double.parseDouble(parts[parts.length -4]);
        selection = parts[parts.length - 5];
    }
    int x = (int) amount;
    String changer = Integer.toString(x);
    if(!lastElement.equals(changer))
    {
        System.out.println("this is the last element === " + lastElement);

        Momo verifyMomo = momoRepository.findByPhonenumber(momoNum);
        if(!Objects.isNull(verifyMomo))
        {
            return processCompleteWithdraw(input,num,choice);
        }
        else
        {
            System.out.println("this is the momo num ==== "+momoNum);
            return"CON The number you entered does not match any valid Momo account number. verify the number and try again. \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON Enter the Momo account number to transfer the funds to :";
    }

}

private String processCompleteWithdraw(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    Loan verifyLoan = loanRepository.findByPhonenumber(num);
    User verifySavingsAmount = userRepository.findByPhoneNumber(num);
    String selection = "";
    double amount = 0;
    String momoNum = "";
    String pin ="";
    
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        amount = Double.parseDouble(lastElement);
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        momoNum = lastElement;
        amount = Double.parseDouble(parts[parts.length -2]);
        selection = parts[parts.length - 3];
    }
            else if(distance == 4)
    {
        pin = lastElement;
        momoNum = parts[parts.length -2];
        amount = Double.parseDouble(parts[parts.length -3]);
        selection = parts[parts.length - 4];
    }
            else if(distance == 5)
    {
        pin = parts[parts.length -2];
        momoNum = parts[parts.length -3];
        amount = Double.parseDouble(parts[parts.length -4]);
        selection = parts[parts.length - 5];
    }
    if(!lastElement.equals(momoNum))
    {
        if(pin.equals(verifySavingsAmount.getPin()))
        {
            ArrayList<Withdraw> newWithdraw = new ArrayList<>();
            String name = verifySavingsAmount.getName();
            String groupId = verifySavingsAmount.getGroupId();
            Withdraw newest = new Withdraw(name, momoNum, groupId,amount);
            newWithdraw.add(newest);
            withdrawRepository.saveAll(newWithdraw);
            return "CON Your request to withdraw the amount of "+amount+" from your savings has been successfully filled. \n \n \n 00. Main Menu";
        }
        else
        {
            return "CON Invalid PIN. \n \n \n00. Main Menu.";
        }
    }
    else
    {
        return "CON You are about to withdraw the amount of "+amount+" from your savings account. Please Enter your PIN to confirm the request: ";
    }
}

private String processLeave(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    User verifyMember = userRepository.findByPhoneNumber(num);
    String selection = "";
    String memberId = "";
    
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        memberId = lastElement;
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        memberId = parts[parts.length -2];
        selection = parts[parts.length - 3];
    }
    if(!lastElement.equals(selection))
    {
        if(memberId.equals(verifyMember.getMembershipId()))
        {
            return processCompleteLeave(input,num,choice);
        }
        else
        {
            return "CON Invalid membership ID \n \n \n00. Main Menu";
        }
    }
    else
    {
            Loan verifyLoan = loanRepository.findByPhonenumber(num);
        if( Objects.isNull(verifyLoan) || verifyLoan.getAmount() == 0)
        {
                     if(!verifyMember.getSavingsGroup().equals("none"))
        {
             return "CON Enter your membership ID:";
        }
                else
        {
            return "CON Dear User, you are currently not register in any Savings Group. \n \n \n00.Main Menu";
        }
     
        }
        else
        {
                   return"CON Dear user, you have an unpaid loan of "+verifyLoan.getAmount()+". If you wish to leave your savings group, please pay up your loan. \n \n \n00.Main Menu";
        }
    }

}

private String processCompleteLeave(String input, String num, String choice)
{
     String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    User verifyMember = userRepository.findByPhoneNumber(num);
    String selection = "";
    String memberId = "";
    String pin ="";
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    User finder = userRepository.findByPhoneNumber(num);
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        memberId = lastElement;
        selection = parts[parts.length - 2];

    }
        else if(distance == 3)
    {
        memberId = parts[parts.length -2];
        selection = parts[parts.length - 3];
    }
    
    if(!lastElement.equals(memberId))
    {
        if(pin.equals(verifyMember.getPin()))
        {
            ArrayList<Leave> newLeave = new ArrayList<>();
            String name = verifyMember.getName();
            String groupId = verifyMember.getGroupId();
            Leave newest = new Leave(name, num, groupId);
            newLeave.add(newest);
            leaveRepository.saveAll(newLeave);
            return "CON Dear user, your request to leave "+verifyMember.getSavingsGroup()+" group with your member ID "+memberId+" has been filled succesfully. \n \n \n00. Main Menu";
        }
        else
        {
            return "CON Invalid PIN \n \n \n00. Main Menu";
        }
    }
    else
    {
        return"CON";
    }
}

private String processCompleteCreate(String input, String num, String choice)
{
    String[] parts = input.split("\\*");
    User verifyMember = userRepository.findByPhoneNumber(num);
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String selection ="";
    String name = "";
    String pin ="";
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if(distance == 2)
    {
        name = lastElement;
        selection = parts[parts.length - 2];
    }
        else if(distance == 3)
    {
        pin = lastElement;
        name = parts[parts.length-2];
        selection = parts[parts.length - 3];
    }
     if(!lastElement.equals(name))
     {
        if(pin.equals(verifyMember.getPin()))
        {
            ArrayList<Groups> newGroup = new ArrayList<>();
            String groupCode = "";
            String memberCode = "";
            Random group = new Random();
            for (int x = 0; x < 5; x++) {
                int digit = group.nextInt(10); 
                groupCode += digit; 
            }

            System.out.println("group code is === "+ groupCode);
            String number = groupCode;
            int members = 1;
            double savings = verifyMember.getDepositAmount();
            Groups newest = new Groups(name, number,members,savings);
            Random member = new Random();
            for (int y = 0; y < 5; y++) {
                int digit = member.nextInt(10); 
                memberCode += digit; 
            }
            
            System.out.println("memberCode is === "+ memberCode);
            newGroup.add(newest);
            groupsRepository.saveAll(newGroup);
            verifyMember.setSavingsGroup(name);
            verifyMember.setMembershipStatus("Team-Leader");
            verifyMember.setMembershipId(memberCode);
            verifyMember.setGroupId(number);
            userRepository.save(verifyMember);
            Groups addon = groupsRepository.findByNumber(number);

            System.out.println("Group "+ name +" created successfully.");
            return "CON Dear user, You have successfully created "+name+" savings group. The group ID is "+groupCode+". Your membership ID is "+memberCode+". \n \n \n00. Main Menu.";
        }
        else
        {
            return "CON Invalid PIN. \n \n \n00. Main Menu";
        }

     }  

     return "CON You are about to create "+name+" savings group. To confirm your request please Enter your PIN:";


}




/* (9) Details */
private String processDetails(String input, String num,String choice)
{
     User user = userRepository.findByPhoneNumber(num);
    if(user.getMembershipStatus().equals("Team-Leader"))
    {   
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String pin ="";
    if(distance == 1)
    {
        pin =lastElement;
    }
    if(!lastElement.equals(choice))
    {
        if(pin.equals(user.getPin()))
        {
        String id = user.getGroupId();
       Groups identify = groupsRepository.findByNumber(id);
    return "CON group details: \n Group Name: "+identify.getGroupName()+"\n Group ID: "+identify.getGroupNumber()+"\n Group Members: "+identify.getMembers()+"\n Total Group Savings: "+identify.getTotalSavings()+"\n \n \n00. Main Menu";
        }
        else
        {
            return"CON Invalid PIN. \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON Enter your PIN";
    }
    }
    else{
        return"END Invalid input";
    }
  
}

/* (10) Pending */
 private String processReviewPending(String input, String num, String choice) {
    User user = userRepository.findByPhoneNumber(num);
    if(user.getMembershipStatus().equals("Team-Leader"))
    {
    String[] parts = input.split("\\*");
    String lastElement = parts[parts.length -1];
    int n = 0;
    if(parts.length >1)
    {
         n= parts.length-1;
    }
    else
    {
     n= parts.length; 
    }
    String selection = "";
        int index = -1;
       for (int i = parts.length - 1; i >= 0; i--) {
        if (parts[i].equals(choice)) {
            index = i; 
            break;     
        }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    if(distance == 1)
    {
        selection = parts[parts.length - 1];
    }
    else if (distance == n)
    {
        selection = parts[index + 1];
        System.out.println("This is the selection === "+selection);
    }
    if(!lastElement.equals(choice))
    {
        System.out.println("this is your selection === "+selection +" and the distance is "+distance +" and this is the index of choice == "+index);
        switch(selection)
        {
            case"3":
            return processPending(input, num, choice);
            case "4":
            return processWithdrawing(input,num,choice);
            case"5":
            return processLeaving(input,num,choice);
            default:
            return"CON Invalid input.\n \n \n00. Main Menu";
        }
    }
    else
    {
        return"CON All pending requests:\n3.Pending Join \n4.Pending withdraw \n.5.Pending Leave \n \n \n00. Main Menu";
    }
    }
    else
    {
        return"CON Invalid input. \n \n \n00.Main Menu";
    }
    }

private String processWithdrawing(String input, String num, String choice)
{
    String[] part = input.split("\\*");
    String lastElement = part[part.length -1];
    User verifier = userRepository.findByPhoneNumber(num);
        List<Withdraw> allPending = withdrawRepository.findAll();
        ArrayList<CustomWithdraw> filteredPendings = new ArrayList<>();
        for(Withdraw withdraw : allPending)
        {
            int count = 1;
                if (verifier.getGroupId().equals(withdraw.getGroupId())) {
                CustomWithdraw customWithdraw = new CustomWithdraw(
                    count,
                    withdraw.getName(),
                    withdraw.getPhoneNumber(),
                    withdraw.getGroupId(),
                    withdraw.getAmount()
                );
                filteredPendings.add(customWithdraw);
                count++;
            }
        }
        if(lastElement.equals("4") || lastElement.equalsIgnoreCase("n"))
         {
        int count = filteredPendings.size();
        if(count == 0)
        {
            return "CON Dear user, you have no new requests to withdraw. \n  \n  \n 00. main Menu";
        } else
        {
        CustomWithdraw element = filteredPendings.get(0);
        String name = element.getName();
        String number = element.getPhoneNumber();
        double amount = element.getAmount();
        return "CON You have "+count+" pending approvals left : \n Name: "+ name+"\n Number:"+ number +"\n Amount: "+amount+" \n \n1. Confirm \n2. Decline. \n \n 00. Main Menu";
        }
    }
    else
    {
         String select = lastElement;
        switch (select) {
            case "1":
            CustomWithdraw element = filteredPendings.get(0);
            String number = element.getPhoneNumber();
            User confirm = userRepository.findByPhoneNumber(number);
            Withdraw user = withdrawRepository.findByPhonenumber(number);
            Momo addon = momoRepository.findByPhonenumber(number);
            Total minus = totalRepository.findByName("Total-Savings");
            confirm.setDepositAmount(confirm.getDepositAmount() - element.getAmount());
            addon.setBalance(addon.getBalance() + element.getAmount());
            minus.setTotal(minus.getTotal() - element.getAmount());

            String transferNumber = element.getPhoneNumber();
            String transferer = element.getName();
            String description = "Successful withdraw funds";
            double amount = element.getAmount();
            LocalDateTime date = LocalDateTime.now();

            ArrayList<Transaction> newTransactions = new ArrayList<>();
            Transaction newest = new Transaction(transferNumber, transferer ,amount, date , description);
            newTransactions.add(newest);
            transactionRepository.saveAll(newTransactions);

            userRepository.save(confirm);
            momoRepository.save(addon);
            totalRepository.save(minus);
            withdrawRepository.deleteByPhonenumber(number);
            filteredPendings.remove(0);

            return "CON Dear leader, "+element.getName()+"'s request to withdraw the amount of"+element.getAmount()+" has been completed successfully.\n Press n to view the reamining requests. \n \n 00. Main Menu";
            case "2":

            CustomWithdraw delete = filteredPendings.get(0);
            String numbers = delete.getPhoneNumber();
            withdrawRepository.deleteByPhonenumber(numbers);
            filteredPendings.remove(0);
            return"CON Request denied successfully. \n \n Press n to view the reamining requests. \n \n \n00. Main Menu";  
        
            default:
                return"CON Invalid Input. \n \n \n00. Main Menu";
        }
    }
}

private String processLeaving(String input, String num, String choice)

{
    String[] part = input.split("\\*");
    String lastElement = part[part.length -1];
    User verifier = userRepository.findByPhoneNumber(num);
        List<Leave> allLeave = leaveRepository.findAll();
        ArrayList<CustomLeave> filteredPendings = new ArrayList<>();
        for(Leave leave : allLeave)
        {
            int count = 1;
                if (verifier.getGroupId().equals(leave.getGroupId())) {
                CustomLeave customLeave = new CustomLeave(
                    count,
                    leave.getName(),
                    leave.getPhoneNumber(),
                    leave.getGroupId()
                );
                filteredPendings.add(customLeave);
                count++;
            }
        }
        if(lastElement.equals("5") || lastElement.equalsIgnoreCase("n"))
         {
        int count = filteredPendings.size();
        if(count == 0)
        {
            return "CON Dear user, you have no new requests to leave your group \n  \n  \n 00. main Menu";
        } else
        {
        CustomLeave element = filteredPendings.get(0);
        String name = element.getName();
        String number = element.getPhoneNumber();
        return "CON You have "+count+" pending approvals left : \n Name: "+ name+"\n Number:"+ number +" \n \n1. Confirm \n2. Decline.";
        }
    }
    else
    {
         String select = lastElement;
        switch (select) {
            case "1":
            CustomLeave element = filteredPendings.get(0);
            String number = element.getPhoneNumber();
            User confirm = userRepository.findByPhoneNumber(number);
            Leave user = leaveRepository.findByPhonenumber(number);
            String groupId = user.getGroupId();
            Groups theGroup = groupsRepository.findByNumber(groupId);
            confirm.setSavingsGroup("none");
            confirm.setGroupId("");
            confirm.setMembershipStatus("none");

            confirm.setMembershipId("");
            userRepository.save(confirm);

            theGroup.setTotalSavings(theGroup.getTotalSavings() - confirm.getDepositAmount());
            theGroup.setMembers(theGroup.getMembers() - 1);
            groupsRepository.save(theGroup);

            pendingRepository.deleteByPhonenumber(number);
            filteredPendings.remove(0);

            return "CON Dear leader, "+confirm.getName()+"'s request to leave your group has been granted successfully. The group now has "+theGroup.getMembers()+" members. \n \n Press n to view the reamining requests. \n \n00. Main Menu";
            case "2":

            CustomLeave delete = filteredPendings.get(0);
            String numbers = delete.getPhoneNumber();
            pendingRepository.deleteByPhonenumber(numbers);
            filteredPendings.remove(0);
            return"CON Request denied successfully. \n \n Press n to view the reamining requests. ";  
        
            default:
                return"CON invalid Input. \n \n \n00. Main Menu.";
        }
    }

}

private String processPending(String input, String num, String choice)
{
    String[] part = input.split("\\*");
    String lastElement = part[part.length -1];
    User verifier = userRepository.findByPhoneNumber(num);
        List<Pending> allPending = pendingRepository.findAll();
        ArrayList<CustomPending> filteredPendings = new ArrayList<>();
        for(Pending pending : allPending)
        {
            int count = 1;
                if (verifier.getGroupId().equals(pending.getGroupId())) {
                CustomPending customPending = new CustomPending(
                    count,
                    pending.getName(),
                    pending.getPhoneNumber(),
                    pending.getGroupId()
                );
                filteredPendings.add(customPending);
                count++;
            }
        }
        if(lastElement.equals("3") || lastElement.equalsIgnoreCase("n"))
         {
        int count = filteredPendings.size();
        if(count == 0)
        {
            return "CON Dear user, you have no new requests to join your group \n  \n  \n 00. main Menu";
        } else
        {
        CustomPending element = filteredPendings.get(0);
        String name = element.getName();
        String number = element.getPhoneNumber();
        return "CON You have "+count+" pending approvals left : \n Name: "+ name+"\n Number:"+ number +" \n \n1. Confirm \n2. Decline.";
        }
    }
    else
    {
         String select = lastElement;
        switch (select) {
            case "1":
            CustomPending element = filteredPendings.get(0);
            String number = element.getPhoneNumber();
            User confirm = userRepository.findByPhoneNumber(number);
            Pending user = pendingRepository.findByPhonenumber(number);
            String groupId = user.getGroupId();
            Groups theGroup = groupsRepository.findByNumber(groupId);
            String groupName = theGroup.getGroupName();
            String memberCode ="";
            confirm.setSavingsGroup(groupName);
            confirm.setGroupId(groupId);
            confirm.setMembershipStatus("Member of "+groupName);

            
            Random member = new Random();
                for (int y = 0; y < 5; y++) {
                int digit = member.nextInt(10); 
                memberCode += digit; 
            }

            confirm.setMembershipId(memberCode);
            userRepository.save(confirm);

            theGroup.setTotalSavings(theGroup.getTotalSavings() + confirm.getDepositAmount());
            theGroup.setMembers(theGroup.getMembers() + 1);
            groupsRepository.save(theGroup);

            pendingRepository.deleteByPhonenumber(number);
            filteredPendings.remove(0);

            return "CON Dear leader, "+confirm.getName()+" has joined your group successfully. The group now has "+theGroup.getMembers()+" members. \n \n Press n to view the reamining requests.";
            case "2":

            CustomPending delete = filteredPendings.get(0);
            String numbers = delete.getPhoneNumber();
            pendingRepository.deleteByPhonenumber(numbers);
            filteredPendings.remove(0);
            return"CON Request denied successfully. \n \n Press n to view the reamining requests. \n \n00. Main Menu";  
        
            default:
                return"CON xxinvalid Input. \n \n \n00. Main Menu.";
        }
    }

}

/*(11) manage Group*/ 
private String processManageGroup(String input, String num, String choice)
{
    User verify = userRepository.findByPhoneNumber(num);
    if(verify.getMembershipStatus().equals("Team-Leader"))
    {
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    int n = 0;
    if(parts.length >1)
    {
         n= parts.length-1;
    }
    else
    {
     n= parts.length; 
    }
    String lastElement = parts[parts.length - 1];
    String selection ="";
    if(distance == 1)
    {
        selection =lastElement;
    }
    else if (distance == n)
    {
        selection = parts[parts.length - n];
    }
    if(!lastElement.equals(choice))
    {
        switch(selection)
        {
            case "1":
            return processSearchMember(input,num,choice);
            case "2":
            return processAddMember(input,num,choice);
            case "3":
            return processRemoveMember(input,num,choice);
            default:
            return "CON Invalid PIN \n \n \n00. Main Menu";
        }
    }
    else
    {
        return "CON 1.Search member \n2. Add a member \n3. Remove a member \n \n.00 main Menu.";
    }
    }
    else
    {
        return"CON Invalid Input \n \n \n00. Main Menu";
    }
}

private String processSearchMember(String input, String num, String choice)
{
    User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
            System.out.println("this is the distance: "+distance);
if(distance == 1)
{
    selection = lastElement;
}
    else if(distance == 2)
    {
        number =lastElement;
        selection = parts[parts.length -2];
    }
    else if (distance == 3)
    {
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }

     System.out.println("this is the selection: "+selection);

    if(!lastElement.equals(selection))
    {
        System.out.println("this is the number: "+number);
        User verifyMember = userRepository.findByPhoneNumber(number);
        if(!verifyMember.getSavingsGroup().equals("none") && verifyMember.getSavingsGroup().equals(leader.getSavingsGroup()))
        {
            return processCompleteSearch(input,num,choice);
        }
        else
        {
            return "CON Dear user, the person assigned to this number is not a member of your savings group. ("+leader.getSavingsGroup()+"). \n \n \n00. Main Menu";
        }
    }
    else
    {
        return"CON Enter the members phone Number:";
    }
}

private String processCompleteSearch(String input, String num, String choice)
{
    User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
    String pin = "";
    if(distance == 2)
    {
        number =lastElement;
    }
    else if (distance == 3)
    {
        pin = lastElement;
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }
        User member = userRepository.findByPhoneNumber(number);
        System.out.println("this is the lastElement === "+lastElement);
    if(!lastElement.equals(number))
    {
        if(pin.equals(leader.getPin()))
        {
            Loan verifyLoan = loanRepository.findByPhonenumber(number);
            String name = member.getName();
            String memberId = member.getMembershipId();
            double amount = member.getDepositAmount();
            if(Objects.isNull(verifyLoan) || verifyLoan.getAmount() == 0)
            {
                return"CON Member details: \nname: "+name+"\nmember ID:"+memberId+" \ntotal- Savings:"+amount+" \nloan-Status: None \nloan Amount: 0 \nInterest: 0  \nDue-Date: N/A \n \n \n00. Main Menu";
            }
            else
            {
                String status = verifyLoan.getStatus();
                double loan = verifyLoan.getAmount();
                double interest = verifyLoan.getInterest();
                LocalDate dueDate = verifyLoan.getDueDate();
                return"CON Member details: \nname: "+name+"\nmember ID:"+memberId+" \ntotal- Savings:"+amount+" \nloan-Status: "+status+" \nloan Amount: "+loan+"\nInterest: "+interest+"  \nDue-Date: "+dueDate+" \n \n \n00. Main Menu";
            }

        }
        else
        {
            return"CON Invalid PIN \n \n \n 00.Main Menu";
        }
    }
    else
    {
        return "CON Enter your PIN:";
    }
}
   
private String processAddMember(String input, String num, String choice)
{
    User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
    if(distance == 1)
    {
        selection = lastElement;
    }
    if(distance == 2)
    {
        number =lastElement;
    }
    else if (distance == 3)
    {
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }

    if(!lastElement.equals(selection))
    {
        User verifyMember = userRepository.findByPhoneNumber(number);
        if(!Objects.isNull(verifyMember))
        {
            if(verifyMember.getSavingsGroup().equals("none"))
            {
                return processCompleteAdd(input,num,choice);
            }
            else
            {
                return"CON Dear user, the person assigned to this number is already a member of another savings group. \n \n \n00. Main Menu";
            }
        }
        else
        {
            return "CON Dear user, the person assigned to this number is not a registered member of Ikizere savings group. \n \n \n00. Main Menu";
        }
    }
    else
    {
        return"CON Enter the phone Number:";
    }
}

private String processCompleteAdd(String input, String num, String choice)
{
    User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
    String pin = "";
    if(distance == 2)
    {
        number =lastElement;
    }
    else if (distance == 3)
    {
        pin = lastElement;
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }
    if(!lastElement.equals(number))
    {
        if(pin.equals(leader.getPin()))
        {
            User addMember = userRepository.findByPhoneNumber(number);
            addMember.setMembershipStatus("member of "+leader.getSavingsGroup());
            addMember.setGroupId(leader.getGroupId());
            addMember.setSavingsGroup(leader.getSavingsGroup());
            String memberCode ="";
                        
            Random member = new Random();
                for (int y = 0; y < 5; y++) {
                int digit = member.nextInt(10); 
                memberCode += digit; 
            }

            addMember.setMembershipId(memberCode);
            userRepository.save(addMember);

            Groups addon = groupsRepository.findByNumber(leader.getGroupId());
            addon.setMembers(addon.getMembers() + 1);
            addon.setTotalSavings(addon.getTotalSavings() + addMember.getDepositAmount());
            groupsRepository.save(addon);
            return "CON Dear user, "+addMember.getName()+" has been successfully added to your group. You group now has "+addon.getMembers()+" total members. \n \n \n00.Main Menu";
        }
        else
        {
            return"CON Invalid PIN \n \n \n 00.Main Menu";
        }
    }
    else
    {
        return "CON Enter your PIN:";
    }
}

private String processRemoveMember(String input, String num, String choice)
{
    
  User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
    if(distance == 2)
    {
        number =lastElement;
    }
    else if (distance == 3)
    {
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }

    if(!lastElement.equals(selection))
    {
        User verifyMember = userRepository.findByPhoneNumber(number);
        if(!verifyMember.getSavingsGroup().equals("none") && !verifyMember.getSavingsGroup().equals(leader.getSavingsGroup()))
        {
            return processCompleteDeleteMember(input,num,choice);
        }
        else
        {
            return "CON Dear user, the person assigned to this number is not a member of your savings group. ("+leader.getSavingsGroup()+"). \n \n \n00. Main Menu";
        }
    }
    else
    {
        return"CON Enter the members phone Number:";
    }
}

private String processCompleteDeleteMember(String input, String num, String choice)
{
    User leader = userRepository.findByPhoneNumber(num);
    String[] parts = input.split("\\*");
    int index = -1;
    for (int i = parts.length - 1; i >= 0; i--) {
    if (parts[i].equals(choice))
     {
    index = i; 
    break;     
     }
    }
    int lastIndex = parts.length-1;
    int distance = Math.abs(lastIndex - index);
    String lastElement = parts[parts.length - 1];
    String number ="";
    String selection = "";
    String pin = "";
    if(distance == 2)
    {
        number =lastElement;
    }
    else if (distance == 3)
    {
        pin = lastElement;
        selection = parts[parts.length -3];
        number = parts[parts.length -2];
    }
    if(!lastElement.equals(number))
    {
        if(pin.equals(leader.getPin()))
        {
            User removeMember = userRepository.findByPhoneNumber(number);
            removeMember.setMembershipStatus("");
            removeMember.setGroupId("");
            removeMember.setSavingsGroup("none");
            removeMember.setMembershipId("none");
            userRepository.save(removeMember);

            Groups addon = groupsRepository.findByNumber(leader.getGroupId());
            addon.setMembers(addon.getMembers() -1);
            addon.setTotalSavings(addon.getTotalSavings() - removeMember.getDepositAmount());
            groupsRepository.save(addon);

            
            return "CON Dear user, "+removeMember.getName()+" has been successfully removed from your group. You group now has "+addon.getMembers()+" total members. \n \n \n00.Main Menu";
        }
        else
        {
            return"CON Invalid PIN \n \n \n 00.Main Menu";
        }
    }
    else
    {
        return "CON Enter your PIN:";
    }
}
/*List of Users*/
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
        
    public List<Savings> getSavings() {
        return savingsRepository.findAll();
    }

        public List<Loan> getLoans() {
        return loanRepository.findAll();
    } 

        public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
        
     public List<Total> getTotal() {
        return totalRepository.findAll();
    }

        public List<Groups> getGroups() {
        return groupsRepository.findAll();
    }

    public List<Pending> getPending() {
        return pendingRepository.findAll();
    }


/*List of Admins*/

    public List<Admin> getAdmin() {
        return adminRepository.findAll();
    }

/*new User*/

    public User addUser(User user) {
       return userRepository.insert(user);
    }

    public Savings addSavings(Savings newSavings) 
    {
        return savingsRepository.insert(newSavings);
    }

        public Loan addLoans(Loan newLoans) {
        return loanRepository.insert(newLoans);
    }

        public Transaction addTransactions(Transaction newTransactions) {
        return transactionRepository.insert(newTransactions);
    }


/*Update User*/

    public User update(User update) {
        return userRepository.save(update);
    }

        public Savings updateSavings(Savings updateSavings) {
        return savingsRepository.save(updateSavings);
    }

        public Loan updateSavings(Loan updateSavings) {
        return loanRepository.save(updateSavings);
    }

        public Transaction updateTransaction(Transaction updateTransaction) {
        return transactionRepository.save(updateTransaction);
    }


/*Delete User*/

    public void delete(String phoneNumber) {
        userRepository.deleteByPhoneNumber(phoneNumber);
    }

        public void deleteSavings(String number) {
        savingsRepository.deleteByPhoneNumber(number);
    }

        public void deleteLoans(String number) {
            loanRepository.deleteByPhoneNumber(number);
    }

        public void deleteTransactions(String number) {
            transactionRepository.deleteByPhoneNumber(number);
    }

        public void declinePending(String phoneNumber) {
            pendingRepository.deleteByPhonenumber(phoneNumber);
    }

        public void ConfirmPending(String phoneNumber) {
            User confirm = userRepository.findByPhoneNumber(phoneNumber);
            Pending user = pendingRepository.findByPhonenumber(phoneNumber);
            String groupId = user.getGroupId();
            Groups theGroup = groupsRepository.findByNumber(groupId);
            String groupName = theGroup.getGroupName();
            String memberCode ="";
            String letter = groupName.substring(0, 3).toUpperCase();
            confirm.setSavingsGroup(groupName);
            confirm.setGroupId(groupId);
            confirm.setMembershipStatus("Member of "+groupName);

            
            Random member = new Random();
                for (int y = 0; y < 5; y++) {
                int digit = member.nextInt(10); 
                memberCode += digit; 
            }

            confirm.setMembershipId(letter+"-MB-"+memberCode);
            userRepository.save(confirm);

            theGroup.setTotalSavings(theGroup.getTotalSavings() + confirm.getDepositAmount());
            theGroup.setMembers(theGroup.getMembers() + 1);
            groupsRepository.save(theGroup);

            pendingRepository.deleteByPhonenumber(phoneNumber);

    }

        public void deleteGroups(String number) {
            Groups theGroup = groupsRepository.findByNumber(number);
            List<Pending> allPending = pendingRepository.findAll();
            List<User> allUsers = userRepository.findAll();
            int i = 0;
            for (User user : allUsers) {
            if (number.equals(user.getGroupId())) {
                user.setSavingsGroup("none");
                user.setGroupId("");
                user.setMembershipId("");
                user.setMembershipStatus("none");
                userRepository.save(user);
                i++;
            }
        }
        System.out.println(i+" Users have been updated successfully.");

            for (Pending pending : allPending) {
            if (number.equals(pending.getGroupId())) {
                pendingRepository.deleteByGroupId(number);
                i++;
            }
        }
        System.out.println(i+" Pending request have been deleted.");
        groupsRepository.deleteByNumber(number);
    }



/*Perform the verification against MongoDB*/
    public String verifyAdmin(String name , String pass) {

        Admin admin = adminRepository.findByUsername(name);

        if (admin != null) {
            return "success";
        } else {
            return "failure";
        }
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
        return dateTime.format(formatter);
    }

    private static String formatLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
