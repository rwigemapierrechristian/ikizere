package com.ussdwork.ussd.Service;

public class languageBackupfile {
    
                                /*IKINYARWANDA */
// /*USSD menu*/
//     public String processUSSDRequestKinyarwanda(String userInput, String num) {
//         if (Strings.isNullOrEmpty(userInput)) {
//             return "CON Ikaze Kubwizigame Ikizere:\n1. kwizigama \n2.Gusaba Inguzanyo  \n3.Kwishyura Inguzanyo \n4. Kureba Ayo Ufite \n5. Kureba Ibyakozwe \n6. Guhindura umubare w'Ibanga  \n7. Guhindura ururimi";
//         } else {
//             return ibikorwa(userInput, num);
//         }
//     }

//     public String ibikorwa(String input, String num){
//         String[] parts = input.split("\\*");

//         switch (parts[0]) {
//             case "1":
//                 return processKohereza(input,num);
//             case "2":
//                 return processGusaba(input,num);
//             case "3":
//                 return processKwishyura(input,num);
//             case "4":
//                 return processReba(num);
//             case "5":
//                 return porcessibyakozwe(input,num);
//             case "6":
//                 return processUmubare(input,num);
//             case "7":
//                 return processGuhinduraUrurimi(input,num);
//             default:
//                 return "END Ibyo wahisemo ntabwo aribyo";
//         }
//     }

// // /* (1) Transfer Funds*/   
//     private String processKohereza(String input, String num) {

//         String phoneNumber;
//         String[] parts = input.split("\\*");
//         if (parts.length > 1) {
//             //This means we have a phone number
//             phoneNumber = parts[1];

//         } else {
//             return "CON Injiza numero yawe ya MoMo:";
//         }
// return ohereza(input, num);
//    }
  
//    private String ohereza(String amount, String num) {

//        double Amount;
//        String number;
//        String[] amountPart= amount.split("\\*");
//        number = amountPart[1];
//        Momo sender = momoRepository.findByPhonenumber(number);

//        if(amountPart.length > 2)
//        {
//            Amount = Double.parseDouble(amountPart[2]);
//            if(sender.getBalance() < Amount )
//            {
//                return "END Ntabwo ufiteho amafaranga ahagije.";
//            }

//        }
//        else {

//         return "CON Injiza amafaranga avanwa kuri nmuero ya " + number + " " + "("+ sender.getName() + ")";
//        }

//         return umubare(amount,num);
//    }

//    private String umubare(String PIN, String num) {

//        String pin = null;
//        String[] amountPart= PIN.split("\\*");
//        double Amount = Double.parseDouble(amountPart[2]);
//        String number = amountPart[1];
//        Momo sender = momoRepository.findByPhonenumber(number);
//        User transfer = userRepository.findByPhoneNumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");
//        String name = transfer.getName();
//        if (amountPart.length >3)
//        {
//            pin = amountPart[3];
//            System.out.println(pin);
//            System.out.println(transfer.getPin());
//            if(pin.equals(transfer.getPin()))
//            {
//                double newSenderbalance = sender.getBalance() - Amount;
//                sender.setBalance(newSenderbalance);
//                momoRepository.save(sender);
//                double newTransferbalance = transfer.getDepositAmount() + Amount;
//                transfer.setDepositAmount(newTransferbalance);
//                userRepository.save(transfer);
//            } else {
//                return "END Umubare wibanga mwashyizemo ntabwo ariwo";
//            }

//        }
//        else{
//                return "CON Injiza umubare wibanga (PIN)";
//        }
       
//             String transferNumber = num;
//             String transferer = transfer.getName();
//             String description = "Transered funds";
//             double amount = Amount;
//             LocalDateTime date = LocalDateTime.now();

//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(transferNumber, transferer ,amount, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);

//             YearMonth currentYearMonth = YearMonth.now();
//             String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));
//             Savings verifyUser = savingsRepository.findByPhoneNumber(num);
//             if(verifyUser == null)
//             {
//             ArrayList<Savings> newSavings = new ArrayList<>();
//             Savings newestSavings = new Savings(transferNumber, transferer,month , amount);
//             newSavings.add(newestSavings);
//             savingsRepository.saveAll(newSavings);
//             } else
//             {
//                 double addon = verifyUser.getAmount() + amount;
//                 verifyUser.setAmount(addon);
//                 savingsRepository.save(verifyUser);
//             }
//             double newTotal = updateTotal.getTotal() + amount;
//             updateTotal.setTotal(newTotal);
//             totalRepository.save(updateTotal);



//        return "END Mukiliya mwiza " +name+", Ubwizigame bwanyu bwemejwe. Ubu mushobora gukanda 4 mukareba ayo mugejejemo.";
//    }

// // /* (2) Request Loan */
//    private String processGusaba(String input, String num)
//    {
//             double amount;
//         String[] parts = input.split("\\*");
//        Loan loaner = loanRepository.findByPhonenumber(num);
//         if (parts.length > 1) {
//             amount = Double.parseDouble(parts[1]);
//             if(loaner != null)
//             {
//                 return "END Mukiriya mwiza, mufite inguzanyo ya "+ loaner.getAmount()+" itarishyurwa, murasabwa kwishyura mbere yuko mufata indi.";
//             }
//         }
//          else {
//             return "CON Injiza amafaranga mushaka kugurizwa: ";
//         }
//        return shyiramoPin(input, num);
//    }

//    private String shyiramoPin(String input, String num)
//    {
//         double amount;
//        String pin;
//        String[] Input = input.split("\\*");
//        amount = Double.parseDouble(Input[1]);
//        User user = userRepository.findByPhoneNumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");
//        System.out.println("this is the total amount ==== " + updateTotal.getTotal());

//        if(Input.length > 2)
//        {

//             pin = Input[2];
//            if(pin.equals(user.getPin()))
//            {
//             String loanerNumber = num;
//             String loaner = user.getName();
//             String description = "Successfull loan Request";
//             LocalDateTime date = LocalDateTime.now();
//             double loan = (amount*0.1)+amount;
//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(loanerNumber, loaner ,loan, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);
//            }
//            else{
//             return "END INVALID PIN";
//            }

//        }
//        else {
//         double loan = (amount*0.1) + amount;
//         LocalDateTime date = LocalDateTime.now();
//         LocalDate repaymentDate = date.plus(1, ChronoUnit.MONTHS).toLocalDate();

//             return "CON Mugiye gusaba inguzanyo yamafaranga "+ amount+" rwf. Muzishyura inyungu y'icumi kwijana. Inguzanyo yanyu izishyurwa kwitariki: "+formatLocalDate(repaymentDate)+". Amafaranga muzishyura yose hamwe ni: "+loan+" rwf. Injizamo umubare wibanga kugirango wemeze.";
//        }
//        Loan userVerifier = loanRepository.findByPhonenumber(num);
//        if(userVerifier == null)
//        {
//             String loanerNumber = num;
//             String loaner = user.getName();
//             double loan = (amount*0.1)+amount;
//             String status = "pending";
//             ArrayList<Loan> newLoans = new ArrayList<>();
//             Loan newest = new Loan(loanerNumber, loaner ,loan, status);
//             newLoans.add(newest);
//             loanRepository.saveAll(newLoans);
//        }
//        else
//        {
//         double loan = (amount*0.1)+amount;
//         userVerifier.setAmount(loan);
//         userVerifier.setStatus("pending");
//         loanRepository.save(userVerifier);
//        }

//             double newTotal = updateTotal.getTotal() - amount;
//             updateTotal.setTotal(newTotal);
//             totalRepository.save(updateTotal);

//         return "END Inguzanyo yawe yemejwe.";
//    }

// // /* (3) Pay Loan */
//    private String processKwishyura(String input, String num)
//    {
//         double amount;
//         String[] parts = input.split("\\*");
//        Loan loaner = loanRepository.findByPhonenumber(num);
// if (loaner.getAmount() == 0)
// {
//     return "END Mukiriya mwiza, ntanguzanyo mufite yo kwishyura. Niba mwifuza gufata inguzanyo mukande 2.";
// }
//  else{
//         if (parts.length > 1) {
//             amount = Double.parseDouble(parts[1]);
//         }
//          else {
//             return "CON Injiza amafaranga mugiye kwishyura: ";
//         }
//  }

//        return ishyuraPin(input, num);
//    }

//    private String ishyuraPin(String input, String num)
//    {
//        double amount;
//        String pin;
//        String[] Input = input.split("\\*");
//        amount = Double.parseDouble(Input[1]);
//        User user = userRepository.findByPhoneNumber(num);
//        Momo payer = momoRepository.findByPhonenumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");

//        if(Input.length > 2)
//        {
//         if(payer != null && amount < payer.getBalance())
//         {
//            pin = Input[2];
//            if(pin.equals(user.getPin()))
//            {
//             String loanerNumber = num;
//             String loaner = user.getName();
//             String description = "Loan payment";
//             LocalDateTime date = LocalDateTime.now();
//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(loanerNumber, loaner ,amount, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);
//             double newBalance = payer.getBalance() - amount;
//             payer.setBalance(newBalance);
//             momoRepository.save(payer);
//            }
//            else{
//             return "END Umubara mwinjije ntabwo ariwo.";
//            }
//         } else
//         {
//             return "END Mukiliya Mwiza, ntamafaranga ahagije mufite kuri konti yanyu ya MoMo.";
//         }

//        }
//        else {

//             return "CON Injiza umubare w'ibanga.";
//        }
//        Loan loanMan = loanRepository.findByPhonenumber(num);

//        double remainingAmount = loanMan.getAmount() - amount;

//        if(remainingAmount < 1)
//        {
//         double newSavings = remainingAmount *(-1);
//         Savings addSavings = savingsRepository.findByPhoneNumber(num);
//         addSavings.setAmount(newSavings + addSavings.getAmount());
//         loanMan.setAmount(0);
//         loanMan.setStatus("paid");

//        } 
//        else{
//             loanMan.setAmount(remainingAmount);
//             loanRepository.save(loanMan); 

//             if (loanMan.getAmount() == 0)
//             {
//                 loanMan.setStatus("paid");
//                 loanRepository.save(loanMan);
//             }else{
//                 loanMan.setStatus("pending");
//                 loanRepository.save(loanMan);
//             }

//        }
//             double newTotal = updateTotal.getTotal() + amount;
//             updateTotal.setTotal(newTotal);
//             totalRepository.save(updateTotal);

//         return "END Mukiriya mwiza, mumaze kwishyura: "+amount+" kunguzanyo yanyu. Musigaje kwishyura "+loanMan.getAmount()+" rwf.";
//    }

//    /* (4) check balance*/
//     private String processReba(String num) {

//         User user = userRepository.findByPhoneNumber(num);
//         return "END Mufiteho amafaranga:"+ user.getDepositAmount()+" rwf.";
//     }

//     /* (5) Mini Statement */
//     public List<CustomTransaction> urutonde(String number) {
//         List<Transaction> allTransactions = transactionRepository.findAll();
//         ArrayList<CustomTransaction> filteredTransactions = new ArrayList<>();
//         int count = 1;
//         for (Transaction transaction : allTransactions) {

//             if (number.equals(transaction.getNumber())) {
//                 CustomTransaction customTransaction = new CustomTransaction(
//                     count,
//                     transaction.getDateTime(),
//                     transaction.getDescription(),
//                     transaction.getAmount()
//                 );
//                 filteredTransactions.add(customTransaction);
//                 count++;
//             }
//         }
//         return filteredTransactions;
//     }
    
//     private String porcessibyakozwe(String input, String num) {
//     List<CustomTransaction> customTransactions = urutonde(num);
//     int startingRow = 1;
//     for (CustomTransaction transaction : customTransactions) {
//         if (transaction.getCount() == 1) {
//             break;
//         }
//         startingRow++;
//     }

//     // Build the mini-statement string
//     StringBuilder miniStatementBuilder = new StringBuilder();
//     for (CustomTransaction transaction : customTransactions) {
//         if (transaction.getCount() >= startingRow) {
//             miniStatementBuilder.append("Itariki: ").append(transaction.getDateTime()).append("\n");
//             miniStatementBuilder.append("Ibyakozwe: ").append(transaction.getDescription()).append("\n");
//             miniStatementBuilder.append("Amafaranga: ").append(transaction.getAmount()).append("\n");
//             miniStatementBuilder.append("------------------------\n");
//         }
//     }
//     // Return the mini-statement string
//     return "CON Ibyakozwe :\n   \n " + miniStatementBuilder.toString();
// }


//   /* (6) change PIN*/   
//     private String processUmubare(String UI, String num) {
//         String oldPin = null;
//         String[] part = UI.split("\\*");
//         System.out.println("Here is the  number ==== "+num);

//         User changer = userRepository.findByPhoneNumber(num);
//         if(part.length > 1)
//         {
//             oldPin = part[1];
//             if (oldPin.equals(changer.getPin()))
//             {
//                 System.out.println("valid");
//             }else{
//                 return "END Umubara mwinjije ntabwo ariwo.";
//             }
//         }
//         else{
//             return "CON Injiza umubare w'ibanga uherutse gukoresha: ";
//         }
//         return mushya(UI, num);
//     }

//     private String mushya(String pin,String num) {
//         String newpin = null;
//         String[] pinPart = pin.split("\\*");
//         if (pinPart.length > 2)
//         {
//             newpin = pinPart[2];
//             if(newpin.length() == 4)
//             {
//                 System.out.println("valid");
//             } else {
//                 return "END Umubara mwinjije ntabwo ariwo.";
//             }
//         }
//         else {
//             return "CON Injiza umubare w'ibanga mushyashya. ugizwe n'imibare 4.";
//         }
//         return hindura(pin, num);
//     }

//     private String hindura(String Pin, String num) {
//         String conPin = null;
//         String[] pinPart = Pin.split("\\*");
//         String newPin = pinPart[2];
//         System.out.println("new pin === " + newPin);
//         User changer = userRepository.findByPhoneNumber(num);
//         String name = changer.getName();
//         if (pinPart.length > 3)
//         {
//             conPin = pinPart[3];
//             System.out.println(" confirm ==== " + conPin);
//             boolean verifier = conPin.equals(newPin);
//             System.out.println(verifier);
//             if (conPin.equals(newPin))
//             {
//                 System.out.println("match");
//             }
//             else {
//                 return "END Umubare w'ibanga mwinjije ntabwo uhwanye nuwo muherutse gushyiramo.";
//             }

//         } else {
//             return "CON Ongera wemeze umubare wawe w'ibanga.:";
//         }
//         changer.setNewPin(newPin);
//         userRepository.save(changer);
//         return "END Mukiriya mwiza "+name+ ", Umubare wawe w'ibanga wahinduwe.";
//     }

//   /* (7) Change Language */
//     private String processGuhinduraUrurimi(String input, String num)
//     {

//         String[] part = input.split("\\*");
//         if (part.length > 1)
//         {
//             switch (part[1])
//             {
//                 case "1":
//                     return processIkinyarwanda(num,input);
//                 case "2":
//                     return processGifaransa(num,input);
//                 case "3":
//                     return processCyongereza(num,input);
//                 default:
//                     return "END Invalid Input";
//             }
//         }
//         else{
//             return "CON Hitamo uririmi: \n1. Kinyarwanda \n2. Francais \n3. English";
//         }
//     }

//     private String processIkinyarwanda(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("kinyarwanda");
//         userRepository.save(changer);
//         return "END Wahisemo I Kinyarwanda";
//     }

//         private String processGifaransa(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("Francais");
//         userRepository.save(changer);
//         return "END Vous avais choisis Le Francais";
//     }

//         private String processCyongereza(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("English");
//         userRepository.save(changer);
//         return "END You have choosen English" ;
//     }



    


//                                /*POUR LE FRANCAIS*/
// /*USSD menu*/
//     public String processUSSDRequestFrancais(String userInput, String num) {
//         if (Strings.isNullOrEmpty(userInput)) {
//             return "CON Bienvenue sur la platform Ikizere Main Menu:\n1. Transfer de fond \n2.Demande un Pres  \n3.Payes votre Pres \n4. verifies votre monten \n5. Mini Statement \n6. Change votre numero PIN  \n7. Change la Langue";
//         } else {
//             return ProceduerManulle(userInput, num);
//         }
//     }

//     public String ProceduerManulle(String input, String num){
//         String[] parts = input.split("\\*");

//         switch (parts[0]) {
//             case "1":
//                 return processEnvoi(input,num);
//             case "2":
//                 return processPres(input,num);
//             case "3":
//                 return processPaye(input,num);
//             case "4":
//                 return processVerifie(num);
//             case "5":
//                 return porcessMini(input,num);
//             case "6":
//                 return processChanges(input,num);
//             case "7":
//                 return processLangue(input,num);
//             default:
//                 return "END Invalid option";
//         }
//     }


// /* (1) Transfer Funds*/   
//     private String processEnvoi(String input, String num) {

//         String phoneNumber;
//         String[] parts = input.split("\\*");
//         if (parts.length > 1) {
//             //This means we have a phone number
//             phoneNumber = parts[1];

//         } else {
//             return "CON Entre votre numero de telephone MoMo :";
//         }
// return envoi(input, num);
//    }
  
//    private String envoi(String amount, String num) {

//        double Amount;
//        String number;
//        String[] amountPart= amount.split("\\*");
//        number = amountPart[1];
//        Momo sender = momoRepository.findByPhonenumber(number);

//        if(amountPart.length > 2)
//        {
//            Amount = Double.parseDouble(amountPart[2]);
//            if(sender.getBalance() < Amount )
//            {
//                return "END Vous ne disposez pas du solde suffisant pour effectuer cette transaction.";
//            }

//        }
//        else {

//         return "CON Entrez le montant a transferer de " + number + " " + "("+ sender.getName() + ")";
//        }

//         return monPin(amount,num);
//    }

//    private String monPin(String PIN, String num) {

//        String pin = null;
//        String[] amountPart= PIN.split("\\*");
//        double Amount = Double.parseDouble(amountPart[2]);
//        String number = amountPart[1];
//        Momo sender = momoRepository.findByPhonenumber(number);
//        User transfer = userRepository.findByPhoneNumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");

//        String name = transfer.getName();
//        if (amountPart.length >3)
//        {

//            pin = amountPart[3];
//            System.out.println(pin);
//            System.out.println(transfer.getPin());
//            if(pin.equals(transfer.getPin()))
//            {
//                double newSenderbalance = sender.getBalance() - Amount;
//                sender.setBalance(newSenderbalance);
//                momoRepository.save(sender);
//                double newTransferbalance = transfer.getDepositAmount() + Amount;
//                transfer.setDepositAmount(newTransferbalance);
//                userRepository.save(transfer);

//            } else {
//                return "END PIN invalide";
//            }
        


//        }
//        else{
//                return "CON Entrez votre PIN: ";
//        }
       
//             String transferNumber = num;
//             String transferer = transfer.getName();
//             String description = "Transered funds";
//             double amount = Amount;
//             LocalDateTime date = LocalDateTime.now();

//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(transferNumber, transferer ,amount, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);

//             YearMonth currentYearMonth = YearMonth.now();
//             String month = currentYearMonth.format(DateTimeFormatter.ofPattern("MM/yyyy"));


//             ArrayList<Savings> newSavings = new ArrayList<>();
//             Savings newestSavings = new Savings(transferNumber, transferer,month , amount);
//             newSavings.add(newestSavings);
//             savingsRepository.saveAll(newSavings);
//                double newTotal = updateTotal.getTotal() + amount;
//                updateTotal.setTotal(newTotal);
//                totalRepository.save(updateTotal);

//        return "END Dear" +name+", your funds have been successfully transfered to your savings account. Dial 4 to check your new balance";
//    }

// /* (2) Request Loan */
//    private String processPres(String input, String num)
//    {
//             double amount;
//         String[] parts = input.split("\\*");
//        Loan loaner = loanRepository.findByPhonenumber(num);
//         if (parts.length > 1) {
//             amount = Double.parseDouble(parts[1]);
//             if(loaner != null)
//             {
//                 return "END Cher utilisateur, vous avez un solde impay\u00E9 de "+ loaner.getAmount()+", veuillez payer avant de pouvoir demander un autre pr\u00EAt.";
//             }
//         }
//          else {
//             return "CON Entrez le montant que vous souhaitez \u00EAtre pr\u00EAt\u00E9: ";
//         }


//        return entrePin(input, num);
//    }

//    private String entrePin(String input, String num)
//    {
//         double amount;
//        String pin;
//        String[] Input = input.split("\\*");
//        amount = Double.parseDouble(Input[1]);
//        User user = userRepository.findByPhoneNumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");
//        double savingsTotal = savingsRepository.findAll().stream().mapToDouble(Savings:: getAmount).sum();


//        if(Input.length > 2)
//        {
//         if(amount < savingsTotal)
//         {
//             if(amount < updateTotal.getTotal())
//             {
//           pin = Input[2];
//            if(pin.equals(user.getPin()))
//            {
//             String loanerNumber = num;
//             String loaner = user.getName();
//             String description = "Successfull loan Request";
//             LocalDateTime date = LocalDateTime.now();
//             double loan = (amount*0.1)+amount;
//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(loanerNumber, loaner ,loan, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);
//             double newTotal = updateTotal.getTotal() - amount;
//             updateTotal.setTotal(newTotal);
//             totalRepository.save(updateTotal);
//            }
//            else{
//             return "END PIN invalide.";
//            }
//             }
 
//         }
//         else
//         {
//             return "END Dear user, we do not have sufficient funds to provide you with the loan amount you requested.";
//         }

//        }
//        else {
//         double loan = (amount*0.1) + amount;
//         LocalDateTime date = LocalDateTime.now();
//         LocalDate repaymentDate = date.plus(1, ChronoUnit.MONTHS).toLocalDate();

//             return "CON vous \u00EAtes sur le point d'emprunter le montant de "+ amount+" rwf. Un intérêt de 10 % sera facturé sur le paiement final qui sera dû le "+formatLocalDate(repaymentDate)+". Votre paiement total s'élèvera à: "+loan+" rwf. Veuillez entrer votre code PIN pour confirmer la demande de pr\u00EAt.";
//        }
//             String loanerNumber = num;
//             String loaner = user.getName();
//             double loan = (amount*0.1)+amount;
//             String status = "pending";
//             ArrayList<Loan> newLoans = new ArrayList<>();
//             Loan newest = new Loan(loanerNumber, loaner ,loan, status);
//             newLoans.add(newest);
//             loanRepository.saveAll(newLoans);

//         return "END votre demande de pr\u00EAt a \u00E9t\u00E9 trait\u00E9e avec succ\u00E8s et accept\u00E9e.";
//    }

// /* (3) Pay Loan */
//    private String processPaye(String input, String num)
//    {
//         double amount;
//         String[] parts = input.split("\\*");
//        Loan loaner = loanRepository.findByPhonenumber(num);
// if (loaner.getAmount() == 0)
// {
//     return "END Cher utilisateur, vous n'avez aucune dette \u00E0 r\u00E9gler. Si vous souhaitez demander un pr\u00EAt, veuillez appuyer sur 2";
// }
//  else{
//         if (parts.length > 1) {
//             amount = Double.parseDouble(parts[1]);
//         }
//          else {
//             return "CON Entrez le montant que vous souhaitez payer: ";
//         }
//  }

//        return payePin(input, num);
//    }

//    private String payePin(String input, String num)
//    {
//        double amount;
//        String pin;
//        String[] Input = input.split("\\*");
//        amount = Double.parseDouble(Input[1]);
//        User user = userRepository.findByPhoneNumber(num);
//        Momo payer = momoRepository.findByPhonenumber(num);
//        Total updateTotal = totalRepository.findByName("Total-Savings");

//        if(Input.length > 2)
//        {
//         if(amount < updateTotal.getTotal())
//         {
//            pin = Input[2];
//            if(pin.equals(user.getPin()))
//            {
//             String loanerNumber = num;
//             String loaner = user.getName();
//             String description = "Loan payment";
//             LocalDateTime date = LocalDateTime.now();
//             ArrayList<Transaction> newTransactions = new ArrayList<>();
//             Transaction newest = new Transaction(loanerNumber, loaner ,amount, date , description);
//             newTransactions.add(newest);
//             transactionRepository.saveAll(newTransactions);
//             double newBalance = payer.getBalance() - amount;
//             payer.setBalance(newBalance);
//             momoRepository.save(payer);
//         }
//            else{
//             return "END PIN invalide.";
//            }
//         }
//         else
//         {
//             return "END Vous ne dispose pas de font suffisent sur votre conte MoMo pour effectue cet transaction. ";
//         }

//        }
//        else {

//             return "CON Veuillez entrer votre code PIN pour confirmer votre paiement.";
//        }
//        Loan loanMan = loanRepository.findByPhonenumber(num);

//        double remainingAmount = loanMan.getAmount() - amount;

//        if(remainingAmount < 1)
//        {
//         double newSavings = remainingAmount *(-1);
//         Savings addSavings = savingsRepository.findByPhoneNumber(num);
//         addSavings.setAmount(newSavings + addSavings.getAmount());
//         loanMan.setAmount(0);
//         loanMan.setStatus("paid");

//        } 
//        else{
//             loanMan.setAmount(remainingAmount);
//             loanRepository.save(loanMan); 

//             if (loanMan.getAmount() == 0)
//             {
//                 loanMan.setStatus("paid");
//                 loanRepository.save(loanMan);
//             }else{
//                 loanMan.setStatus("pending");
//                 loanRepository.save(loanMan);
//             }

//        }
//             double newTotal = updateTotal.getTotal() + amount;
//             updateTotal.setTotal(newTotal);
//             totalRepository.save(updateTotal);

//         return "END vous avez pay\u00E9 avec succ\u00E8s le montant de :"+amount+"pour couvrir votre pr\u00EAt. Votre solde est maintenant "+loanMan.getAmount()+" rwf.";
//    }

// /* (4) check balance*/
//     private String processVerifie(String num) {

//         User user = userRepository.findByPhoneNumber(num);
//         return "END votre solde est: "+ user.getDepositAmount();
//     }

// /* (5) Mini Statement */

//     public List<CustomTransaction> avoirTout(String number) {
//         List<Transaction> allTransactions = transactionRepository.findAll();
//         ArrayList<CustomTransaction> filteredTransactions = new ArrayList<>();
//         int count = 1;
//         for (Transaction transaction : allTransactions) {

//             if (number.equals(transaction.getNumber())) {
//                 CustomTransaction customTransaction = new CustomTransaction(
//                     count,
//                     transaction.getDateTime(),
//                     transaction.getDescription(),
//                     transaction.getAmount()
//                 );
//                 filteredTransactions.add(customTransaction);
//                 count++;
//             }
//         }
//         return filteredTransactions;
//     }
     
//    private String porcessMini(String input, String num)
//    {
//     List<CustomTransaction> customTransactions = avoirTout(num);
//     int startingRow = 1;
//     for (CustomTransaction transaction : customTransactions) {
//         if (transaction.getCount() == 1) {
//             break;
//         }
//         startingRow++;
//     }

//     // Build the mini-statement string
//     StringBuilder miniStatementBuilder = new StringBuilder();
//     for (CustomTransaction transaction : customTransactions) {
//         if (transaction.getCount() >= startingRow) {
//             miniStatementBuilder.append("Date: ").append(transaction.getDateTime()).append("\n");
//             miniStatementBuilder.append("Description: ").append(transaction.getDescription()).append("\n");
//             miniStatementBuilder.append("Montant: ").append(transaction.getAmount()).append("\n");
//             miniStatementBuilder.append("------------------------\n");
//         }
//     }
//     // Return the mini-statement string
//     return "CON Mini declaration:\n   \n " + miniStatementBuilder.toString();
//    }   

// /* (6) change PIN*/   
//     private String processChanges(String UI, String num) {
//         String oldPin = null;
//         String[] part = UI.split("\\*");
//         System.out.println("Here is the  number ==== "+num);

//         User changer = userRepository.findByPhoneNumber(num);
//         if(part.length > 1)
//         {
//             oldPin = part[1];
//             if (oldPin.equals(changer.getPin()))
//             {
//                 System.out.println("valid");
//             }else{
//                 return "END PIN invalide.";
//             }
//         }
//         else{
//             return "CON Veuillez entrer votre ancien code PIN \u00E0 4 chiffres: ";
//         }
//         return nouvauPin(UI, num);
//     }

//     private String nouvauPin(String pin,String num) {
//         String newpin = null;
//         String[] pinPart = pin.split("\\*");
//         if (pinPart.length > 2)
//         {
//             newpin = pinPart[2];
//             if(newpin.length() == 4)
//             {
//                 System.out.println("valid");
//             } else {
//                 return "END PIN invalide. Assurez-vous d'avoir entr\u00E9 un code PIN \u00E0 4 chiffres.";
//             }
//         }
//         else {
//             return "CON Entrez votre nouveau code PIN \u00E0 4 chiffres.";
//         }
//         return change(pin, num);
//     }

//     private String change(String Pin, String num) {
//         String conPin = null;
//         String[] pinPart = Pin.split("\\*");
//         String newPin = pinPart[2];
//         System.out.println("new pin === " + newPin);
//         User changer = userRepository.findByPhoneNumber(num);
//         String name = changer.getName();
//         if (pinPart.length > 3)
//         {
//             conPin = pinPart[3];
//             System.out.println(" confirm ==== " + conPin);
//             boolean verifier = conPin.equals(newPin);
//             System.out.println(verifier);
//             if (conPin.equals(newPin))
//             {
//                 System.out.println("match");
//             }
//             else {
//                 return "END Le code PIN que vous venez de saisir ne correspond pas \u00E0 votre saisie pr\u00E9c\u00E9dente.";
//             }

//         } else {
//             return "CON confirmez votre nouveau code PIN s'il vous pla\u00EEt:";
//         }
//         changer.setNewPin(newPin);
//         userRepository.save(changer);
//         return "END Chers "+name+ ", votre code PIN a \u00E9t\u00E9 r\u00E9initialis\u00E9 avec succ\u00E8s.";
//     }

// /* (7) Change Language */
//     private String processLangue(String input, String num)
//     {

//         String[] part = input.split("\\*");
//         if (part.length > 1)
//         {
//             switch (part[1])
//             {
//                 case "1":
//                     return processPourKinyarwanda(num,input);
//                 case "2":
//                     return processPourFrancais(num,input);
//                 case "3":
//                     return processPourEnglish(num,input);
//                 default:
//                     return "END entr\u00E9e invalide";
//             }
//         }
//         else{
//             return "CON Choisissez une langue\u00A0:\n1. Kinyarwanda \n2. Francais \n3. English";
//         }
//     }

//     private String processPourKinyarwanda(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("kinyarwanda");
//         userRepository.save(changer);
//         return "END Wahisemo I Kinyarwanda";
//     }

//     private String processPourFrancais(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("Francais");
//         userRepository.save(changer);
//         return "END Vous avais choisis Le Francais";
//     }

//     private String processPourEnglish(String num, String input)
//     {
//         User changer = userRepository.findByPhoneNumber(num);
//         changer.setLanguage("English");
//         userRepository.save(changer);
//         return "END You have choosen English" ;
//     }

}
