package service;

import contants.Errors;
import dto.User;
import enums.ExpenseType;
import repository.UserRepository;
import util.OutputUtil;

import java.util.ArrayList;
import java.util.List;

public class SplitwiseService {

    UserService userService=new UserService();

    public void splitExpense(String[] inp){
        if (ExpenseType.valueOf(inp[4+Integer.parseInt(inp[3])]).equals(ExpenseType.EXACT)){
            List<String> borrowers=new ArrayList<>();
            List<Double> splitShare=new ArrayList<>();
            for (int i=4;i<Integer.valueOf(inp[3])+4;i++) {
                borrowers.add(inp[i]);
                splitShare.add(Double.valueOf(inp[i+Integer.valueOf(inp[3])+1]));
            }
            userService.splitExact(inp[1],Integer.valueOf(inp[2]),Integer.valueOf(inp[3]),borrowers,splitShare);
        }else
        if (ExpenseType.valueOf(inp[4+Integer.parseInt(inp[3])]).equals(ExpenseType.EQUAL)){
            List<String> borrowers=new ArrayList<>();
            int borrowerCount=Integer.valueOf(inp[3]);
            for (int i=4;i<borrowerCount+4;i++)
                borrowers.add(inp[i]);
            userService.splitEqual(inp[1],Integer.valueOf(inp[2]),Integer.valueOf(inp[3]),borrowers);
        }else
        if (ExpenseType.valueOf(inp[4+Integer.parseInt(inp[3])]).equals(ExpenseType.PERCENT)){
            List<String> borrowers=new ArrayList<>();
            List<Double> splitShare=new ArrayList<>();
            for (int i=4;i<Integer.valueOf(inp[3])+4;i++) {
                borrowers.add(inp[i]);
                splitShare.add(Double.valueOf(inp[i+Integer.valueOf(inp[3])+1]));
            }
            userService.splitByPercent(inp[1],Integer.valueOf(inp[2]),Integer.valueOf(inp[3]),borrowers,splitShare);
        }else{
            System.out.println(Errors.INVALID_EXPENSE_TYPE);
        }
    }

    public void show(String[] inp){
        if (inp.length==1){
            showAllBalance();
        }else {
            showBalance(inp[1]);
        }
    }
    private void showAllBalance(){
        OutputUtil outputUtil=new OutputUtil();
        List<User> userList=UserRepository.getUserList();
        if (userList.isEmpty()) {
            System.out.println(Errors.NO_BALANCE);
            return;
        }
        int BalanceFlag =0;
        for (User user:userList) {
            for (String owesTo: user.getOwesToMap().keySet()) {
                BalanceFlag =1;
              outputUtil.appendToOutputList(OutputUtil.generateOutput(owesTo, user.getName(), user.getOwesToMap().get(owesTo).getAmount()));
            }
            for (String lendTo: user.getLendToMap().keySet()) {
                BalanceFlag =1;
                outputUtil.appendToOutputList(OutputUtil.generateOutput(user.getName(),lendTo,user.getLendToMap().get(lendTo).getAmount()));
            }
        }
        outputUtil.printOutput();
        if (BalanceFlag ==0)
            System.out.println(Errors.NO_BALANCE);
    }
    private  void showBalance(String user_id){
        OutputUtil outputUtil=new OutputUtil();
        if (!UserRepository.getStringUserMap().containsKey(user_id)){
            System.out.println(Errors.NO_BALANCE);
            return;
        }
        User user=UserRepository.getStringUserMap().get(user_id);
        int BalanceFlag =0;

        for (String owesTo: user.getOwesToMap().keySet()) {
            BalanceFlag =1;
          outputUtil.appendToOutputList(OutputUtil.generateOutput(owesTo, user.getName(), user.getOwesToMap().get(owesTo).getAmount()));
        }
        for (String lendTo: user.getLendToMap().keySet()) {
            BalanceFlag =1;
            outputUtil.appendToOutputList(OutputUtil.generateOutput(user.getName(),lendTo,user.getLendToMap().get(lendTo).getAmount()));
        }
        outputUtil.printOutput();
        if (BalanceFlag ==0)
            System.out.println(Errors.NO_BALANCE);
    }
}
