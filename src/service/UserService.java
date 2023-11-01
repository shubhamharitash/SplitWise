package service;

import contants.Errors;
import dto.Expense;
import dto.User;
import repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {
    ExpenseService expenseService=new ExpenseService();
      void splitExact(String lender, int amountLend, int numberOfBorrowers, List<String> borrowers, List<Double> exactAmountList){
          if (!isExactListValid(exactAmountList,amountLend))
              throw new RuntimeException(Errors.Invalid_Exact_Amount_Sum);
        List<Expense> expenseList= expenseService.getExpenseList(lender,borrowers,exactAmountList);
         User user=expenseService.settleBalance(lender,expenseList);
        UserRepository.getUserList().add(user);
        UserRepository.getStringUserMap().put(lender,user);
    }
      void splitEqual(String lender, int amountLend,int numberOfBorrowers, List<String> borrowers){
        double equallySplitAmount= amountLend/numberOfBorrowers;
          List<Expense> expenseList= expenseService.getExpenseList(lender,borrowers,getEquallySplitList(numberOfBorrowers,equallySplitAmount));
          User user=expenseService.settleBalance(lender,expenseList);
        UserRepository.getUserList().add(user);
        UserRepository.getStringUserMap().put(lender,user);

    }
      void splitByPercent(String lender,double amountLend,int numberOfBorrowers, List<String> borrowers,List<Double> percentList){
          if (!isPercentListValid(percentList))
              throw new RuntimeException(Errors.Invalid_Percentage_Share);
        List<Double> spendAmount=new ArrayList<>();
        for (int i=0;i<percentList.size();i++) {
            double borrowedAmount=percentList.get(i)*amountLend/100.0;
            spendAmount.add(borrowedAmount);
        }
        List<Expense> expenseList= expenseService.getExpenseList(lender,borrowers,spendAmount);
         User user=expenseService.settleBalance(lender,expenseList);
        UserRepository.getUserList().add(user);
        UserRepository.getStringUserMap().put(lender,user);
    }
    List<Double> getEquallySplitList(int numberOfBorrowers,double equallySplitAmount){
        Double[] doubles = new Double[numberOfBorrowers];
        Arrays.fill(doubles, equallySplitAmount);
        List<Double> equallySplitList = Arrays.asList(doubles);
        return equallySplitList;
    }
    boolean isPercentListValid(List<Double> percentList){
    return   percentList.stream().reduce((double) 0,(a, b)->a+b)==100?true:false;
    }
    boolean isExactListValid(List<Double> exactList,int amountLend){
        return   exactList.stream().reduce((double) 0,(a, b)->a+b)==amountLend?true:false;
    }

}
