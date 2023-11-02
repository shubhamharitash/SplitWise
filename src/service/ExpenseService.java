package service;

import dto.Expense;
import dto.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService {
     User settleBalance(String user_id, List<Expense> expenseList){
        User user;
        if (!UserRepository.getStringUserMap().containsKey(user_id))
         user=new User(user_id,new HashMap<>());
        else
         user= UserRepository.getStringUserMap().get(user_id);

       Map<String,Expense> lendToMap=user.getLendToMap();
        for (Expense expense:expenseList) {
                if (!lendToMap.containsKey(expense.getUser_id()))
                {
                    lendToMap.put(expense.getUser_id(), expense);
                    updateOwersProfile(user_id,expense);
                }else
                if (lendToMap.containsKey(expense.getUser_id())){
                    Expense expense1=new Expense(expense.getUser_id(),lendToMap.get(expense.getUser_id()).getAmount()+expense.getAmount());
                    settleOwersPreviousExpensesOnCurrentUser(user_id,expense);
                    lendToMap.put(expense.getUser_id(),expense1);
                }
        }
        return user;
    }
    void settleOwersPreviousExpensesOnCurrentUser(String user_id,Expense expense){
        Expense owersPreviousExpense=UserRepository.getStringUserMap().get(expense.getUser_id()).getLendToMap().get(user_id);

        double diffAmount=owersPreviousExpense.getAmount()-expense.getAmount();
        if (diffAmount>0){
            expense.setAmount(diffAmount);//u4 expense on u1=230 rem
            owersPreviousExpense.setAmount(-diffAmount);
        }else {
            expense.setAmount(-diffAmount);
            owersPreviousExpense.setAmount(diffAmount);
        }
        UserRepository.getStringUserMap().get(user_id).getLendToMap().put(expense.getUser_id(),expense);
        UserRepository.getStringUserMap().get(expense.getUser_id()).getLendToMap().put(user_id,owersPreviousExpense);
    }

    void updateOwersProfile(String lender_id, Expense expense){
        if (expense.getUser_id().equals(lender_id))
            return;
        if (!UserRepository.getStringUserMap().containsKey(expense.getUser_id())){
            Expense owedExpense=new Expense(lender_id, -expense.getAmount());//karja
            Map<String,Expense> owedExpenseMap=new HashMap<>();
            owedExpenseMap.put(lender_id,owedExpense);
            User user=new User(expense.getUser_id(),owedExpenseMap);
            UserRepository.getStringUserMap().put(expense.getUser_id(),user);
            UserRepository.getUserList().add(user);
        }
    }

    List<Expense> getExpenseList(String lender,List<String> borrowers,List<Double> spendAmount){
        List<Expense> expenseList=new ArrayList<>();
        int i=0;
        for (String borrower:borrowers) {
            if (lender.equals(borrower))
                continue;
            expenseList.add(new Expense(borrower,spendAmount.get(i++)));
        }
        return expenseList;
    }

}
