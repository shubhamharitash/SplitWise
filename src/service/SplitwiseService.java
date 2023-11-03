package service;

import contants.Errors;
import enums.ExpenseType;

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
        ShowService showService=new ShowService();
        if (inp.length==1){
            showService.showAllBalance();
        }else {
            showService.showUserBalance(inp[1]);
        }
    }

}
