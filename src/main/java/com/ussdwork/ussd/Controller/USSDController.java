package com.ussdwork.ussd.Controller;


import java.util.List;
import java.util.Map;


import com.ussdwork.ussd.model.Admin;
import com.ussdwork.ussd.model.Groups;
import com.ussdwork.ussd.model.Loan;
import com.ussdwork.ussd.model.Pending;
import com.ussdwork.ussd.model.Savings;
import com.ussdwork.ussd.model.Total;
import com.ussdwork.ussd.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ussdwork.ussd.Service.*;
import com.ussdwork.ussd.model.User;

import org.springframework.ui.Model;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/ussd")
public class USSDController {
@GetMapping("/havoc/{path:[^\\.]*}")
public String forward()
{
    return "forward:/";
}


    @Autowired
   private USSDService ussdService;



    @PostMapping ("/test")
    public String mainMenu(@RequestParam(value = "text", required = false) String userInput, @RequestParam(value = "phoneNumber", required = false) String userPhone) {
        String myNum = userPhone.substring(3);
        System.out.println("numerous ===== "+ myNum);
        System.out.println("SOMETHING   ===== "+userInput);
        return ussdService.verifier(myNum, userInput);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(ussdService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/allSavings")
    public ResponseEntity<List<Savings>> getSavings()
    {
        return new ResponseEntity<List<Savings>>(ussdService.getSavings(), HttpStatus.OK);
    }

        @GetMapping("/allLoans")
    public ResponseEntity<List<Loan>> getLoans()
    {
        return new ResponseEntity<List<Loan>>(ussdService.getLoans(), HttpStatus.OK);
    }

    @GetMapping("/allTransactions")
    public ResponseEntity<List<Transaction>> getTransactions()
    {
        return new ResponseEntity<List<Transaction>>(ussdService.getTransactions(), HttpStatus.OK);
    }

    @GetMapping("/allTotals")
    public ResponseEntity<List<Total>> getTotals()
    {
        return new ResponseEntity<List<Total>>(ussdService.getTotal(), HttpStatus.OK);
    }

        @GetMapping("/allGroups")
    public ResponseEntity<List<Groups>> getGroups()
    {
        return new ResponseEntity<List<Groups>>(ussdService.getGroups(), HttpStatus.OK);
    }

        @GetMapping("/allPending")
    public ResponseEntity<List<Pending>> getPending()
    {
        return new ResponseEntity<List<Pending>>(ussdService.getPending(), HttpStatus.OK);
    }



    @GetMapping("/admin")
    public ResponseEntity<List<Admin>> getAdmin()
    {
        return  new ResponseEntity<List<Admin>>(ussdService.getAdmin(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public String verifyAdmin(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        System.out.println("name ===== " + username);
        System.out.println("password ===== " + password);

        return ussdService.verifyAdmin(username, password);
    }

      @CrossOrigin(origins = "http://localhost:3000")
@GetMapping("/tester")
    public String home(Model model) {
        model.addAttribute("message", "Hello, World!");
        return "index.html";
    }

    

    @PostMapping("/post")
    public User addUser(@RequestBody User newUser)
    {
        return ussdService.addUser(newUser);
    }

    @PostMapping("/postSavings")
    public Savings addSavings(@RequestBody Savings newSavings)
    {
        return ussdService.addSavings(newSavings);
    }

        @PostMapping("/postLoans")
    public Loan addLoans(@RequestBody Loan newLoans)
    {
        return ussdService.addLoans(newLoans);
    }

        @PostMapping("/postTransactions")
    public Transaction addTransactions(@RequestBody Transaction newTransactions)
    {
        return ussdService.addTransactions(newTransactions);
    }


    @PutMapping("/update")
    public User update(@RequestBody User update)
    {
        return ussdService.update(update);
    }

    @PutMapping("/updateSavings")
    public Savings updateSavings(@RequestBody Savings updateSavings)
    {
        return ussdService.updateSavings(updateSavings);
    }

        @PutMapping("/updateLoans")
    public Loan updateLoans(@RequestBody Loan updateSavings)
    {
        return ussdService.updateSavings(updateSavings);
    }

            @PutMapping("/updateTransactions")
    public Transaction updateTransaction(@RequestBody Transaction updateTransaction)
    {
        return ussdService.updateTransaction(updateTransaction);
    }
    



    @DeleteMapping("/{phoneNumber}")
    public void delete(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.delete(phoneNumber);
    }

    @DeleteMapping("/savings/{phoneNumber}")
    public void deleteSavings(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.deleteSavings(phoneNumber);
    }

        @DeleteMapping("/loans/{phoneNumber}")
    public void deleteLoans(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.deleteLoans(phoneNumber);
    }

            @DeleteMapping("/transactions/{phoneNumber}")
    public void deleteTransactions(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.deleteTransactions(phoneNumber);
    }

                @DeleteMapping("/confirm/{phoneNumber}")
    public void ConfirmPending(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.ConfirmPending(phoneNumber);
    }

                @DeleteMapping("/decline/{phoneNumber}")
    public void declinePending(@PathVariable ("phoneNumber") String phoneNumber)
    {
         ussdService.declinePending(phoneNumber);
    }

                @DeleteMapping("/groups/{number}")
    public void deleteGroups(@PathVariable ("number") String number)
    {
         ussdService.deleteGroups(number);
    }

}
